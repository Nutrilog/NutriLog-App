package com.nutrilog.app.ui

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.nutrilog.app.BuildConfig
import com.nutrilog.app.R
import com.nutrilog.app.databinding.ActivityMainBinding
import com.nutrilog.app.ui.base.BaseActivity
import com.nutrilog.app.ui.screen.analysis.AnalysisActivity
import com.nutrilog.app.ui.screen.auth.AuthViewModel
import com.nutrilog.app.ui.screen.welcome.WelcomeActivity
import com.nutrilog.app.utils.helpers.observe
import com.nutrilog.app.utils.helpers.showSnackBar
import com.supersuman.apkupdater.ApkUpdater
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val authViewModel: AuthViewModel by viewModel()
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!BuildConfig.DEBUG && isInternetConnection()) checkForUpdates()
    }

    override fun onViewBindingCreated(savedInstanceState: Bundle?) {
        super.onViewBindingCreated(savedInstanceState)

        initBottomNavigation()

        if (!isInternetConnection()) {
            binding.root.showSnackBar(getString(R.string.message_connection_internet))
        }
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

        observe(authViewModel.user) {
            if (it.id === "" || it.id.isEmpty()) {
                moveToWelcome()
            }
        }
    }

    private fun moveToWelcome() {
        startActivity(Intent(this, WelcomeActivity::class.java))
        finish()
    }

    private fun checkForUpdates() =
        CoroutineScope(Dispatchers.IO).launch {
            val updater = ApkUpdater(this@MainActivity, BuildConfig.REPO_URL)
            updater.threeNumbers = true
            if (updater.isNewUpdateAvailable() == true) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.label_update_app),
                    Snackbar.LENGTH_INDEFINITE,
                ).setAction(getString(R.string.label_download)) {
                    CoroutineScope(Dispatchers.IO).launch { updater.requestDownload() }
                }.show()
            }
        }

    private fun isInternetConnection(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                return true
            }
        }

        return false
    }
}
