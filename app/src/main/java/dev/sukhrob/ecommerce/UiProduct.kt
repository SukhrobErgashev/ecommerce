package dev.sukhrob.ecommerce

import dev.sukhrob.ecommerce.model.domain.Product

data class UiProduct(
    val product: Product,
    val isFavProduct: Boolean = false
)