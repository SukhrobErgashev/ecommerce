package dev.sukhrob.ecommerce.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.ecommerce.MainActivityViewModel
import dev.sukhrob.ecommerce.ProductEpoxyController
import dev.sukhrob.ecommerce.UiProduct
import dev.sukhrob.ecommerce.databinding.ScreenProductsListBinding
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsListScreen: Fragment() {

    private var _binding: ScreenProductsListBinding? = null
    private val binding by lazy { _binding!! }

    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var epoxyController: ProductEpoxyController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ScreenProductsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}