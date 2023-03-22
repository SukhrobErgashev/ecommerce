package dev.sukhrob.ecommerce.remote.service

import dev.sukhrob.ecommerce.model.network.NetworkProduct
import retrofit2.Response
import retrofit2.http.GET

interface ProductsService {
        @GET("products")
        suspend fun getProducts(): Response<List<NetworkProduct>>
    }