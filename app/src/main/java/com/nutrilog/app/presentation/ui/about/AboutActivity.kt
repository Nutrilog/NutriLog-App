package com.nutrilog.app.presentation.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import com.nutrilog.app.BuildConfig
import com.nutrilog.app.R
import com.nutrilog.app.databinding.ActivityAboutBinding
import com.nutrilog.app.domain.model.TeamMember
import com.nutrilog.app.presentation.ui.base.BaseActivity
import com.nutrilog.app.presentation.ui.base.component.bottomSheet.teamMember.TeamMemberBottomSheet

class AboutActivity : BaseActivity<ActivityAboutBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityAboutBinding =
        ActivityAboutBinding::inflate

    override fun onViewBindingCreated(savedInstanceState: Bundle?) {
        super.onViewBindingCreated(savedInstanceState)

        initToolbar()
        initAction()
        initUI()
    }

    private fun initUI() {
        binding.apply {
            versionLabel.text = getString(R.string.label_version, BuildConfig.VERSION_NAME)
        }
    }

    private fun initAction() {
        binding.apply {
            authorPhotoOne.setOnClickListener {
                showDetail(TeamMember.RAFI.data)
            }
            authorPhotoTwo.setOnClickListener {
                showDetail(TeamMember.ISRAK.data)
            }
            authorPhotoThree.setOnClickListener {
                showDetail(TeamMember.GISELLA.data)
            }
            authorPhotoFour.setOnClickListener {
                showDetail(TeamMember.FAUZAN.data)
            }
            authorPhotoFive.setOnClickListener {
                showDetail(TeamMember.BAGJA.data)
            }
            authorPhotoSix.setOnClickListener {
                showDetail(TeamMember.NIZAR.data)
            }
            authorPhotoSeven.setOnClickListener {
                showDetail(TeamMember.GUNTUR.data)
            }
        }
    }

    private fun initToolbar() {
        binding.apply {
            toolbar.apply {
                tvPage.text = getString(R.string.header_about_title)
                backButton.setOnClickListener { finish() }
            }
        }
    }

    private fun showDetail(data: List<String>) {
        val bottomSheet = TeamMemberBottomSheet.newInstance(data)
        bottomSheet.show(supportFragmentManager, TeamMemberBottomSheet.TAG)
    }
}
