package dev.sukhrob.ecommerce.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sukhrob.ecommerce.ProductsRepository
import dev.sukhrob.ecommerce.remote.service.ProductsService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesProductsRepository(productsService: ProductsService): ProductsRepository {
        return ProductsRepository(productsService)
    }
}