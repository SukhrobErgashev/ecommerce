package dev.sukhrob.ecommerce

import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import dev.sukhrob.ecommerce.databinding.ItemEpoxyModelProductBinding
import dev.sukhrob.ecommerce.model.domain.Product
import dev.sukhrob.ecommerce.epoxy.ViewBindingKotlinModel


data class ProductEpoxyModel(
    val product: Product
) : ViewBindingKotlinModel<ItemEpoxyModelProductBinding>(R.layout.item_epoxy_model_product) {

    override fun ItemEpoxyModelProductBinding.bind() {
        // Setup or text
        productTitleTextView.text = product.title
        productDescriptionTextView.text = product.description
        productCategoryTextView.text = product.category

        // Load our image
        productImageViewLoadingProgressBar.isVisible = true
        productImageView.load(data = product.image) {
            listener { _, _ ->
                productImageViewLoadingProgressBar.isGone = true
            }
        }
    }

}