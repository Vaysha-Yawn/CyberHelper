package com.example.test.settings.presentation.fragments.system

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.databinding.FragmentTemplateItemSystemSettingsBinding
import com.example.test.viewModels.GameSystemDAO
import com.example.test.settings.presentation.view_model.SystemSettingsVM
import com.example.test.components.views.HeaderView


class TemplateItemSystemSettingsFragment : Fragment(), HeaderView.HeaderBack {

    private val gameSystemDAO:GameSystemDAO by activityViewModels()
    private val systemSettingsVM: SystemSettingsVM by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_template_item_system_settings, container, false)
        val binding = FragmentTemplateItemSystemSettingsBinding.bind(view)
        with(binding){
            header.setBack(true, this@TemplateItemSystemSettingsFragment, requireActivity(), viewLifecycleOwner)
            next.setOnClickListener {
                view.findNavController().navigate(R.id.action_templateItemSystemSettingsFragment_to_templateCharacterSystemSettingsFragment)
            }
        }
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

}