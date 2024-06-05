package com.nutrilog.app.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.nutrilog.app.R
import com.nutrilog.app.databinding.ActivityMainBinding
import com.nutrilog.app.presentation.ui.analysis.AnalysisActivity
import com.nutrilog.app.presentation.ui.auth.AuthViewModel
import com.nutrilog.app.presentation.ui.base.BaseActivity
import com.nutrilog.app.presentation.ui.welcome.WelcomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val authViewModel: AuthViewModel by viewModel()
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    override fun onViewBindingCreated(savedInstanceState: Bundle?) {
        super.onViewBindingCreated(savedInstanceState)

        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.apply {
            bottomNavigation.setOnItemSelectedListener { item ->
                when (item) {
                    R.id.homeFragment -> navController.navigate(R.id.homeFragment)
                    R.id.historyFragment -> navController.navigate(R.id.historyFragment)
                    R.id.profileFragment -> navController.navigate(R.id.profileFragment)
                    R.id.analysisActivity -> {
                        val intent = Intent(this@MainActivity, AnalysisActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    }
                }
            }
            navController.addOnDestinationChangedListener { _, destination, _ ->
                bottomNavigation.setItemSelected(destination.id)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        binding.apply {
            bottomNavigation.setItemSelected(
                navController.currentDestination?.id ?: R.id.homeFragment,
            )
        }
    }

    override fun onStart() {
        super.onStart()

        authViewModel.getSession().observe(this) {
            if (it.id === "" || it === null) {
                moveToWelcome()
            }
        }
    }

    private fun moveToWelcome() {
        startActivity(Intent(this, WelcomeActivity::class.java))
        finish()
    }
}
