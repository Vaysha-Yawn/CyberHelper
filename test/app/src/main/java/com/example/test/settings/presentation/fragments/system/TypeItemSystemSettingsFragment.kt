package com.example.test.settings.presentation.fragments.system

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.character_list.presentation.adapters.CVAdapterRV
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
        with(binding) {
            header.setBack(
                true,
                this@TypeItemSystemSettingsFragment,
                requireActivity(),
                viewLifecycleOwner
            )
            val groupItemsTitle = mutableListOf<String>()
            for (i in createSystemVM.groups) {
                for (e in i) {
                    if (e != null && e.prefItem) {
                        groupItemsTitle.add(e.title)
                    }
                }
            }

            // удостоверились, что соответствует реальнсти
            if (createSystemVM.typesItems.keys.toMutableList() != groupItemsTitle) {
                for (i in groupItemsTitle) {
                    if (!createSystemVM.typesItems.keys.toMutableList().contains(i)) {
                        createSystemVM.typesItems[i] = mutableListOf()
                    }
                }
            }

            val adapter = CVAdapterRV(
                "Добавить тип предметов",
                "Название типа предметов"
            )
            RV.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            RV.adapter = adapter
            adapter.setData(
                createSystemVM.typesItems.values.toMutableList(),
                createSystemVM.typesItems.keys.toMutableList(),
            )
            adapter.setEditListener(
                object : CVAdapterRV.OnEdit {
                    override fun onEdit(
                        adapterPos: Int,
                        editPos: Int,
                        title: String,
                        text: String
                    ) {
                        createSystemVM.typesItems[title]?.set(editPos, text)
                    }
                })
            next.setOnClickListener {
                view.findNavController()
                    .navigate(R.id.action_typeItemSystemSettingsFragment_to_paramCharacterSystemSettingsFragment)
            }
        }
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

}