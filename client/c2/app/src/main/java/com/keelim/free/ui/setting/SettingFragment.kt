package com.keelim.free.ui.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.keelim.free.R
import com.keelim.free.databinding.FragmentSettingBinding
import com.keelim.free.ui.inject.InjectActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private val settingViewModel: SettingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        click()
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }

    private fun click() = with(binding){
        aboutButton.setOnClickListener {
            dismiss()
            findNavController().navigate(R.id.aboutFragment)
        }

        homepage.setOnClickListener {
            dismiss()
            startActivity(Intent(requireActivity(), InjectActivity::class.java))
        }

        signOut.setOnClickListener{
            dismiss()
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                Uri.parse(getString(R.string.git_lab_address))
            })
        }

        openSourceLicensesButton.setOnClickListener {
            dismiss()
            requireActivity().startActivity(
                Intent(
                    requireActivity(),
                    OssLicensesMenuActivity::class.java
                )
            )
        }
    }
}