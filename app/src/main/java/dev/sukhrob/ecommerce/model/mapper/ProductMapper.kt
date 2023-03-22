package dev.sukhrob.ecommerce.model.mapper

import dev.sukhrob.ecommerce.model.domain.Product
import dev.sukhrob.ecommerce.model.domain.Rating
import dev.sukhrob.ecommerce.model.network.NetworkProduct
import dev.sukhrob.ecommerce.model.network.NetworkRating

fun NetworkProduct.toDomain(): Product {
    return Product(
        this.id,
        this.title,
        this.price,
        this.description,
        this.category,
        this.image,
        this.rating.toDomain()
    )
}

fun NetworkRating.toDomain(): Rating {
    return Rating(
        this.rate,
        this.count
    )
}