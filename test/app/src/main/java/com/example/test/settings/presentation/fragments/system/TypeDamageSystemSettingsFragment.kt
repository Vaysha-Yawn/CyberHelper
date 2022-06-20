package com.example.test.settings.presentation.fragments.system

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.components.views.CompactViewDD
import com.example.test.components.views.CompactViewEdit
import com.example.test.components.views.HeaderView
import com.example.test.databinding.FragmentTypeDamageSystemSettingsBinding
import com.example.test.settings.presentation.view_model.CreateSystemVM
import com.example.test.settings.presentation.view_model.SystemSettingsVM
import com.example.test.viewModels.GameSystemDAO


class TypeDamageSystemSettingsFragment : Fragment(), HeaderView.HeaderBack {

    private val createSystemVM: CreateSystemVM by activityViewModels()

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
            createSystemVM.typesDamage.observe(viewLifecycleOwner){
                CVDD.setData(it)
            }
            CVDD.setListener(
                object: CompactViewEdit.OnClickEdit{
                    override fun onClickEdit(
                        posEdit: Int,
                        text: String,
                    ) {
                        createSystemVM.typesDamage.value?.set(posEdit, text)
                    }
                }
            )

            next.setOnClickListener {
                createSystemVM.typesDamage.value?.removeAll(listOf(null))
                view.findNavController()
                    .navigate(R.id.action_typeDamageSystemSettingsFragment_to_groupSystemSettingsFragment)
            }
        }
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

}