package com.example.test.settings.presentation.fragments.system

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.components.views.CompactViewEdit
import com.example.test.components.views.CompactViewString
import com.example.test.databinding.FragmentTypeItemsSystemSettingsBinding
import com.example.test.viewModels.GameSystemDAO
import com.example.test.settings.presentation.view_model.SystemSettingsVM
import com.example.test.components.views.HeaderView
import com.example.test.settings.presentation.view_model.CreateSystemVM


class TypeItemSystemSettingsFragment : Fragment(), HeaderView.HeaderBack {

    private val createSystemVM: CreateSystemVM by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_type_items_system_settings, container, false)
        val binding = FragmentTypeItemsSystemSettingsBinding.bind(view)
        with(binding){
            header.setBack(true, this@TypeItemSystemSettingsFragment, requireActivity(), viewLifecycleOwner)
            val listItemGroup = mutableListOf<String>()
            for ( i in createSystemVM.groups){
                for (e in i){

                }
            }
            for ( i in createSystemVM.groups){
                val compactViewEdit = CompactViewEdit(requireContext())
                compactViewEdit.setAddText("Добавить тип предметов")
                compactViewEdit.setHint("Название типа предметов")
                compactViewEdit.setTitle("")
                compactViewEdit.setListener(  object : CompactViewEdit.OnStringEdited{
                    override fun onStringEdited(
                        posEdit: Int,
                        text: String,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {

                    }

                })
                addRV.addView(compactViewEdit)
            }
            next.setOnClickListener {
                view.findNavController().navigate(R.id.action_typeItemSystemSettingsFragment_to_paramCharacterSystemSettingsFragment)
            }
        }
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

}