package com.nutrilog.app.presentation.ui.welcome

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowInsets
import android.view.WindowManager
import com.nutrilog.app.R
import com.nutrilog.app.databinding.ActivityWelcomeBinding
import com.nutrilog.app.presentation.ui.auth.AuthActivity
import com.nutrilog.app.presentation.ui.base.BaseActivity

class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityWelcomeBinding =
        ActivityWelcomeBinding::inflate

    override fun onViewBindingCreated(savedInstanceState: Bundle?) {
        super.onViewBindingCreated(savedInstanceState)

        initActions()
    }

    private fun initActions() {
        binding.apply {
            startNow.setOnClickListener {
                binding.main.apply {
                    setTransition(R.id.nextToEnd)
                    transitionToState(R.id.end)
                }
            }
            back2.setOnClickListener {
                binding.main.apply {
                    setTransition(R.id.backToStart)
                    transitionToState(R.id.start)
                }
            }
            next2.setOnClickListener {
                binding.main.apply {
                    setTransition(R.id.nextToFinish)
                    transitionToState(R.id.finish)
                }
            }
            back3.setOnClickListener {
                binding.main.apply {
                    setTransition(R.id.backToEnd)
                    transitionToState(R.id.end)
                }
            }
            next3.setOnClickListener {
                moveToLogin()
            }
        }
    }

    private fun moveToLogin() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    public override fun onResume() {
        super.onResume()
        setupView()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            )
        }
        supportActionBar?.hide()
    }
}
