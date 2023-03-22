package dev.sukhrob.ecommerce

import com.airbnb.epoxy.TypedEpoxyController
import dev.sukhrob.ecommerce.model.domain.Product

class ProductEpoxyController: TypedEpoxyController<List<Product>>() {

    override fun buildModels(data: List<Product>?) {
        if (data == null || data.isEmpty()) {
            // loading
            return
        }

        data.forEach {product ->
            ProductEpoxyModel(product).id(product.id).addTo(this)
        }
    }
}