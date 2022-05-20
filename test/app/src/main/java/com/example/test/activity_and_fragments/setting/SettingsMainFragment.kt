package com.example.test.activity_and_fragments.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.databinding.FragmentSettingsMainBinding
import com.example.test.viewModels.GameSystemDAO
import com.example.test.views.HeaderView


class SettingsMainFragment : Fragment(), HeaderView.HeaderBack {

    private val mGameSystemDAO: GameSystemDAO by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings_main, container, false)
        val binding = FragmentSettingsMainBinding.bind(view)
        with(binding){
            fightType.setOnClickListener {
                view.findNavController().navigate(R.id.action_settingsMainFragment_to_settingFightType2)
            }

            templateItem.setOnClickListener {
                view.findNavController().navigate(R.id.action_settingsMainFragment_to_editTemplateItemFragment)
            }
            templateCharacter
            header.setBack( true, this@SettingsMainFragment, requireActivity(), viewLifecycleOwner)
        }

        return view
    }

    override fun back() {
        (activity as SettingsHost).backToMain()
    }

}