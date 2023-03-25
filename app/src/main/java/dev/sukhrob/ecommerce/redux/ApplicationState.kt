package dev.sukhrob.ecommerce.redux

import dev.sukhrob.ecommerce.model.domain.Product

data class ApplicationState(
    val products: List<Product> = emptyList(),
    val favoriteProductIds: Set<Int> = emptySet()
)