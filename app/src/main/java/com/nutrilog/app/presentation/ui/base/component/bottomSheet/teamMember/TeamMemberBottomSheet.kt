package com.nutrilog.app.presentation.ui.base.component.bottomSheet.teamMember

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nutrilog.app.databinding.LayoutProfileBottomSheetBinding

class TeamMemberBottomSheet: BottomSheetDialogFragment() {
    private var _binding: LayoutProfileBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var memberData: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            memberData = it.getStringArray(ARG_TEAM_MEMBER_DATA)?.toList()!!
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        behavior()
        initViews()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = LayoutProfileBottomSheetBinding.inflate(inflater, container, false)

        return _binding?.root
    }

    private fun behavior() {
        val behavior = BottomSheetBehavior.from(binding.profileBottomSheet)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.saveFlags = BottomSheetBehavior.SAVE_FIT_TO_CONTENTS
        behavior.isDraggable = false
    }

    private fun initViews() {
        binding.apply {
            buttonClose.setOnClickListener { dismiss() }

            textFullName.text = memberData[0]
            textUniversity.text = memberData[1]

            igBtn.setOnClickListener{
                val instagramLink = memberData[2]
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramLink))
                startActivity(intent)
            }

            linkedinBtn.setOnClickListener{
                val linkedinLink = memberData[3]
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedinLink))
                startActivity(intent)
            }

            githubBtn.setOnClickListener {
                val githubLink = memberData[4]
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(githubLink))
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "TeamMemberBottomSheet"

        private const val ARG_TEAM_MEMBER_DATA = "member_data"

        fun newInstance(
            memberData: List<String>,
        ) = TeamMemberBottomSheet().apply {
            arguments =
                Bundle().apply {
                    putStringArray(ARG_TEAM_MEMBER_DATA, memberData.toTypedArray())
                }
        }
    }
}