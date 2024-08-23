package com.nutrilog.app.ui.screen.auth

import android.view.LayoutInflater
import com.nutrilog.app.ui.base.BaseActivity
import com.nutrilog.app.databinding.ActivityAuthBinding

class AuthActivity : BaseActivity<ActivityAuthBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityAuthBinding =
        ActivityAuthBinding::inflate
}