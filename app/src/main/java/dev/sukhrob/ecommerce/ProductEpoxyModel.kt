package dev.sukhrob.ecommerce

import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import coil.load
import dev.sukhrob.ecommerce.databinding.ItemEpoxyModelProductBinding
import dev.sukhrob.ecommerce.epoxy.ViewBindingKotlinModel
import java.text.NumberFormat


data class ProductEpoxyModel(
    val uiProduct: UiProduct?,
    val onFavoriteIconClicked: (Int) -> Unit
) : ViewBindingKotlinModel<ItemEpoxyModelProductBinding>(R.layout.item_epoxy_model_product) {

    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    override fun ItemEpoxyModelProductBinding.bind() {
        shimmerLayout.isVisible = uiProduct == null
        cardView.isInvisible = uiProduct == null

        uiProduct?.let { uiProduct ->
            shimmerLayout.stopShimmer()

            // Setup our text
            productTitleTextView.text = uiProduct.product.title
            productDescriptionTextView.text = uiProduct.product.description
            productCategoryTextView.text = uiProduct.product.category
            productPriceTextView.text = currencyFormatter.format(uiProduct.product.price)

            // Load our image
            productImageViewLoadingProgressBar.isVisible = true
            productImageView.load(data = uiProduct.product.image) {
                listener { request, result ->
                    productImageViewLoadingProgressBar.isGone = true
                }
            }

            // Rest of the views
            val imageRes = if (uiProduct.isFavProduct)
                R.drawable.ic_round_favorite
            else
                R.drawable.ic_round_favorite_border
            favoriteImageView.setIconResource(imageRes)
            favoriteImageView.setOnClickListener {
                onFavoriteIconClicked.invoke(uiProduct.product.id)
            }

        } ?: shimmerLayout.startShimmer()
    }

}