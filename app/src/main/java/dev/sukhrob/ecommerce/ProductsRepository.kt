package dev.sukhrob.ecommerce

import android.util.Log
import dev.sukhrob.ecommerce.app.App.Companion.TAG
import dev.sukhrob.ecommerce.model.domain.Product
import dev.sukhrob.ecommerce.model.mapper.toDomain
import dev.sukhrob.ecommerce.remote.service.ProductsService
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productsService: ProductsService
) {

    suspend fun fetchAllProducts(): List<Product> {
        try {
            val response = productsService.getProducts()
            response.body()?.let { products ->
                return products.map { it.toDomain() }
            }
        } catch (e: java.lang.Exception) {
            Log.d(TAG, "getProducts: ")
        }
        return emptyList()
    }
}