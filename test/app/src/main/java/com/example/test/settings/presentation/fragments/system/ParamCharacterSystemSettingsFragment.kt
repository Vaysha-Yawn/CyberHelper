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
import com.example.test.databinding.FragmentParamsCharacterSystemSettingsBinding
import com.example.test.viewModels.GameSystemDAO
import com.example.test.settings.presentation.view_model.SystemSettingsVM
import com.example.test.components.views.HeaderView
import com.example.test.settings.presentation.view_model.CreateSystemVM


class ParamCharacterSystemSettingsFragment : Fragment(), HeaderView.HeaderBack {

    private val createSystemVM: CreateSystemVM by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_params_character_system_settings, container, false)
        val binding = FragmentParamsCharacterSystemSettingsBinding.bind(view)
        with(binding){
            header.setBack(true, this@ParamCharacterSystemSettingsFragment, requireActivity(), viewLifecycleOwner)
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
            adapter.setStrListener(
                object: CVAdapterRV.OnStringAdd{
                    override fun onAdd(adapterPos: Int, title: String) {
                        //
                    }

                },
                object: CVAdapterRV.OnStringDel{
                    override fun onDel(adapterPos: Int, editPos: Int, title: String) {
                        //
                    }

                },
                object: CVAdapterRV.OnStringEdit{
                    override fun onEdit(adapterPos: Int, editPos: Int, title: String) {
                        //
                    }

                }
            )

            next.setOnClickListener {
                view.findNavController().navigate(R.id.action_paramCharacterSystemSettingsFragment_to_paramItemSystemSettingsFragment)
            }
        }
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

}