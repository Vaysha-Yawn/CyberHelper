package com.example.test.settings.presentation.fragments.system

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.components.views.CompactViewString
import com.example.test.databinding.FragmentGroupSystemSettingsBinding
import com.example.test.viewModels.GameSystemDAO
import com.example.test.settings.presentation.view_model.SystemSettingsVM
import com.example.test.components.views.HeaderView
import com.example.test.settings.presentation.view_model.CreateSystemVM


class GroupSystemSettingsFragment : Fragment(), HeaderView.HeaderBack {

    private val createSystemVM: CreateSystemVM by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_group_system_settings, container, false)
        val binding = FragmentGroupSystemSettingsBinding.bind(view)
        with(binding){
            header.setBack(true, this@GroupSystemSettingsFragment, requireActivity(), viewLifecycleOwner)
            for ( (index, i) in createSystemVM.groups.withIndex()){
                val compactViewString = CompactViewString(requireContext())
                compactViewString.setAddText("Добавить группу")
                compactViewString.setHint("Название характеристики")
                when(index){
                    0->{
                        compactViewString.setTitle("1 Инфщ")
                    }
                    1->{
                        compactViewString.setTitle("2 Параметры")
                    }
                    2->{
                        compactViewString.setTitle("3 Броня и оружие")
                    }
                    3->{
                        compactViewString.setTitle("4 Рюкзак")
                    }
                    4->{
                        compactViewString.setTitle("5 Биография")
                    }
                }
                compactViewString.setListener( object: CompactViewString.OnClickEdit{
                    override fun onClickEdit(posEdit: Int) {

                    }
                })
                addRV.addView(compactViewString)
            }
            next.setOnClickListener {
                // добавить по умолчанию базовые параметры
                view.findNavController().navigate(R.id.action_groupSystemSettingsFragment_to_typeItemSystemSettingsFragment)
            }
        }
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

}