package com.example.test.settings.presentation.fragments.system

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.components.views.CompactViewDD
import com.example.test.components.views.HeaderView
import com.example.test.databinding.FragmentTypeDamageSystemSettingsBinding
import com.example.test.settings.presentation.view_model.SystemSettingsVM
import com.example.test.viewModels.GameSystemDAO


class TypeDamageSystemSettingsFragment : Fragment(), HeaderView.HeaderBack {

    private val gameSystemDAO:GameSystemDAO by activityViewModels()
    private val systemSettingsVM: SystemSettingsVM by activityViewModels()
    private val list = mutableListOf<Int?>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_type_damage_system_settings, container, false)
        val binding = FragmentTypeDamageSystemSettingsBinding.bind(view)
        with(binding){
            header.setBack(
                true,
                this@TypeDamageSystemSettingsFragment,
                requireActivity(),
                viewLifecycleOwner
            )
            next.setOnClickListener {
                view.findNavController()
                    .navigate(R.id.action_typeDamageSystemSettingsFragment_to_groupSystemSettingsFragment)
            }
            CVDD.setData(list)
            CVDD.setListener(
                listOf("Физический", "Магический", "Некротический"),
                object : CompactViewDD.OnDDSelected {
                    override fun onDDSelected(posDD: Int, positionAnswer: Int, result: String) {
                        //
                    }
                })
        }
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

}