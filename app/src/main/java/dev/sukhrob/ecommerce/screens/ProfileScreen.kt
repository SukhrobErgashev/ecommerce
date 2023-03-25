package dev.sukhrob.ecommerce.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import dev.sukhrob.ecommerce.MainActivityViewModel
import dev.sukhrob.ecommerce.ProductEpoxyController
import dev.sukhrob.ecommerce.R
import dev.sukhrob.ecommerce.UiProduct
import dev.sukhrob.ecommerce.databinding.ScreenProductsListBinding
import dev.sukhrob.ecommerce.databinding.ScreenProfileBinding
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ProfileScreen : Fragment(R.layout.screen_profile) {

    private var _binding: ScreenProfileBinding? = null
    private val binding by lazy { _binding!! }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ScreenProfileBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}