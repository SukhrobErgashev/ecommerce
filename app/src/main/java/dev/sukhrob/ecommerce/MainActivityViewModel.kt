package dev.sukhrob.ecommerce

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sukhrob.ecommerce.model.domain.Product
import dev.sukhrob.ecommerce.redux.ApplicationState
import dev.sukhrob.ecommerce.redux.Store
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    private val productsRepository: ProductsRepository
) : ViewModel() {

    fun refreshProducts() = viewModelScope.launch {
        val products: List<Product> = productsRepository.fetchAllProducts()
        store.update { applicationState ->
            return@update applicationState.copy(
                products = products
            )
        }

        delay(5000)
        store.update {
            return@update it.copy(favoriteProductIds = setOf(1, 3))
        }
    }
}