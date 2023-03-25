package dev.sukhrob.ecommerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.ecommerce.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.productsListScreen,
                R.id.profileScreen
            )
        )

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController, navBarConfiguration)

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }
}