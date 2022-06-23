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
import com.example.test.adapters.CVAdapterRV
import com.example.test.databinding.FragmentTypeItemsSystemSettingsBinding
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

            val adapter = CVAdapterRV(
                "Добавить тип предметов",
                "Название типа предметов"
            )
            RV.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            RV.adapter = adapter
            adapter.setData(
                createSystemVM.getMapTypeItem()
            )
            adapter.setEditListener(
                object : CVAdapterRV.OnEdit {
                    override fun onEdit(
                        adapterPos: Int,
                        editPos: Int,
                        title: String,
                        text: String
                    ) {
                        createSystemVM.editTypeItem(title, editPos, text)
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