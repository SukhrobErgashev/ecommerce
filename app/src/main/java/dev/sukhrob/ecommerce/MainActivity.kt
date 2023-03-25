package dev.sukhrob.ecommerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.ecommerce.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var epoxyController: ProductEpoxyController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        epoxyController = ProductEpoxyController(::onFavoriteIconClicked)
        binding.epoxyRecyclerView.setController(epoxyController)
        epoxyController.setData(emptyList())

        refreshData()
        viewModel.refreshProducts()
    }

    private fun refreshData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                combine(
                    viewModel.store.stateFlow.map { it.products },
                    viewModel.store.stateFlow.map { it.favoriteProductIds }
                ) { listOfProducts, setOfFavoriteIds ->
                    listOfProducts.map { product ->
                        UiProduct(
                            product = product,
                            isFavProduct = setOfFavoriteIds.contains(product.id)
                        )
                    }
                }.distinctUntilChanged().collect { listOfUiProduct ->
                    epoxyController.setData(listOfUiProduct)
                }
            }
        }
    }

    private fun onFavoriteIconClicked(selectedProductId: Int) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                val currentFavoriteIds = currentState.favoriteProductIds
                val newFavoriteIds = if (currentFavoriteIds.contains(selectedProductId)) {
                    currentFavoriteIds.filter { it != selectedProductId }.toSet()
                } else {
                    currentFavoriteIds + setOf(selectedProductId)
                }
                return@update currentState.copy(favoriteProductIds = newFavoriteIds)
            }
        }
    }
}