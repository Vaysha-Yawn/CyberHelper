package com.example.test.activity_and_fragments.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.test.R
import com.example.test.databinding.FragmentSettingsMainBinding


class SettingsMainFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings_main, container, false)
        val binding = FragmentSettingsMainBinding.bind(view)
        with(binding){
            fightType
            templateItem
            templateCharacter
        }

        return view
    }

}