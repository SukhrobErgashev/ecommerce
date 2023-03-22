package dev.sukhrob.ecommerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.ecommerce.databinding.ActivityMainBinding
import dev.sukhrob.ecommerce.model.mapper.toDomain
import dev.sukhrob.ecommerce.remote.service.ProductsService
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var productsService: ProductsService

    private lateinit var binding: ActivityMainBinding
    private lateinit var epoxyController: ProductEpoxyController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        epoxyController = ProductEpoxyController()
        binding.epoxyRecyclerView.setController(epoxyController)

        refreshData()
        setupListeners()
    }

    private fun refreshData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val response = productsService.getProducts()
                val domainProducts = response.body()?.map { networkProduct ->
                    networkProduct.toDomain()
                } ?: emptyList()
                epoxyController.setData(domainProducts)
            }
        }
    }

    private fun setupListeners() {
//        with(binding) {
//            cardView.setOnClickListener {
//                productDescriptionTextView.apply {
//                    isVisible = !isVisible
//                }
//            }
//
//            addToCartButton.setOnClickListener {
//                inCartView.apply {
//                    isVisible = !isVisible
//                }
//            }
//
//            var isFavorite = false
//            favoriteImageView.setOnClickListener {
//                val imageRes = if (isFavorite) {
//                    R.drawable.ic_round_favorite_border
//                } else {
//                    R.drawable.ic_round_favorite
//                }
//                favoriteImageView.setIconResource(imageRes)
//                isFavorite = !isFavorite
//            }
//        }
    }
}