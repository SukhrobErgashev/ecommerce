package dev.sukhrob.ecommerce

import com.airbnb.epoxy.TypedEpoxyController

class ProductEpoxyController(
    private val onFavoriteIconClicked: (Int) -> Unit
) : TypedEpoxyController<List<UiProduct>>() {

    override fun buildModels(data: List<UiProduct>?) {
        if (data.isNullOrEmpty()) {
            repeat(7) {
                val epoxyId = it + 1
                ProductEpoxyModel(uiProduct = null, onFavoriteIconClicked).id(epoxyId).addTo(this)
            }
            return
        }

        data.forEach { uiProduct ->
            ProductEpoxyModel(uiProduct, onFavoriteIconClicked).id(uiProduct.product.id)
                .addTo(this)
        }
    }
}