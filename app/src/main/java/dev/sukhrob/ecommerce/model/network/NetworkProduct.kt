package dev.sukhrob.ecommerce.model.network

data class NetworkProduct(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: NetworkRating
)