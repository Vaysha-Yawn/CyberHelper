package com.example.test.settings.presentation.fragments.system

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.character_list.presentation.adapters.CVAdapterRV
import com.example.test.components.views.HeaderView
import com.example.test.data_base.GroupParam
import com.example.test.data_base.ParamNum
import com.example.test.data_base.ParamOptions
import com.example.test.data_base.ParamStr
import com.example.test.databinding.ChooseTypeParamBinding
import com.example.test.databinding.FragmentParamsCharacterSystemSettingsBinding
import com.example.test.settings.presentation.fragments.system.subFragments.EditParamNum
import com.example.test.settings.presentation.fragments.system.subFragments.EditParamOption
import com.example.test.settings.presentation.fragments.system.subFragments.EditParamStr
import com.example.test.settings.presentation.view_model.CreateSystemVM


class ParamCharacterSystemSettingsFragment : Fragment(), HeaderView.HeaderBack {

    private val createSystemVM: CreateSystemVM by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // заполнить группами и списками , если не заполнено
        for (i in createSystemVM.groups){
            for (e in i){
                if (!createSystemVM.mapParamCharacter.keys.contains(e?.title)&& e!=null){
                    createSystemVM.mapParamCharacter[e.title] = mutableListOf()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(R.layout.fragment_params_character_system_settings, container, false)
        val binding = FragmentParamsCharacterSystemSettingsBinding.bind(view)
        with(binding) {
            header.setBack(
                true,
                this@ParamCharacterSystemSettingsFragment,
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
                createSystemVM.typesItems.values.toMutableList(),
                createSystemVM.typesItems.keys.toMutableList(),
            )

            adapter.setStrListener(
                object : CVAdapterRV.OnStringAdd {
                    override fun onAdd(adapterPos: Int, title: String) {
                        val group = createSystemVM.getGroup(title)
                        if (group != null && (group.prefStr || group.prefNum || group.prefDD)) {
                            val dialogFragment = ParamTypeDialogFragment(
                                object : ParamTypeDialogFragment.Add {
                                    override fun add(type: String) {
                                        when (type) {
                                            createSystemVM.STR -> {
                                                createSystemVM.characterParamsStr.add(ParamStr())
                                                val pos = createSystemVM.characterParamsStr.size - 1
                                                createSystemVM.mapParamCharacter[title]?.add(
                                                    Pair(
                                                        createSystemVM.STR,
                                                        pos
                                                    )
                                                )
                                                view.findNavController().navigate(
                                                    R.id.action_paramCharacterSystemSettingsFragment_to_editParamStr,
                                                    EditParamStr().getBundle( pos, title)
                                                )
                                            }
                                            createSystemVM.NUM -> {
                                                createSystemVM.characterParamsNum.add(ParamNum())
                                                val pos = createSystemVM.characterParamsNum.size - 1
                                                createSystemVM.mapParamCharacter[title]?.add(
                                                    Pair(
                                                        createSystemVM.NUM,
                                                        pos
                                                    )
                                                )
                                                view.findNavController().navigate(
                                                    R.id.action_paramCharacterSystemSettingsFragment_to_editParamNum,
                                                    EditParamNum().getBundle(pos, title)
                                                )
                                            }
                                            createSystemVM.OPTIONS -> {
                                                createSystemVM.characterParamsOptions.add(
                                                    ParamOptions()
                                                )
                                                val pos =
                                                    createSystemVM.characterParamsOptions.size - 1
                                                createSystemVM.mapParamCharacter[title]?.add(
                                                    Pair(
                                                        createSystemVM.OPTIONS,
                                                        pos
                                                    )
                                                )
                                                view.findNavController().navigate(
                                                    R.id.action_paramCharacterSystemSettingsFragment_to_editParamOption,
                                                    EditParamOption().getBundle( pos, title)
                                                )
                                            }
                                        }
                                    }
                                },
                                object : ParamTypeDialogFragment.Help {
                                    override fun help() {
                                        view.findNavController()
                                            .navigate(R.id.action_paramCharacterSystemSettingsFragment_to_tipTypeParam)
                                    }
                                },
                                group.prefStr,
                                group.prefNum,
                                group.prefDD
                            )
                            dialogFragment.show(childFragmentManager, "chooseMod")
                        } else {
                            //Toast с ошибкой
                        }
                    }

                },
                object : CVAdapterRV.OnStringDel {
                    override fun onDel(adapterPos: Int, editPos: Int, title: String) {
                        // нам нужно удалить в группе и в листе
                        val i = createSystemVM.mapParamCharacter[title]?.get(editPos)
                        when(i?.first){
                            createSystemVM.STR->{
                                createSystemVM.characterParamsStr.removeAt(i.second)
                            }
                            createSystemVM.NUM->{
                                createSystemVM.characterParamsNum.removeAt(i.second)
                            }
                            createSystemVM.OPTIONS->{
                                createSystemVM.characterParamsOptions.removeAt(i.second)
                            }
                        }
                        createSystemVM.mapParamCharacter[title]?.removeAt(editPos)
                    }

                },
                object : CVAdapterRV.OnStringEdit {
                    override fun onEdit(adapterPos: Int, editPos: Int, title: String) {
                        val pair = createSystemVM.mapParamCharacter[title]?.get(editPos)
                        when (pair?.first) {
                            createSystemVM.STR -> {
                                view.findNavController().navigate(
                                    R.id.action_paramCharacterSystemSettingsFragment_to_editParamStr,
                                    EditParamStr().getBundle( pair.second, title)
                                )
                            }
                            createSystemVM.NUM -> {
                                view.findNavController().navigate(
                                    R.id.action_paramCharacterSystemSettingsFragment_to_editParamNum,
                                    EditParamNum().getBundle( pair.second, title)
                                )
                            }
                            createSystemVM.OPTIONS -> {
                                view.findNavController().navigate(
                                    R.id.action_paramCharacterSystemSettingsFragment_to_editParamOption,
                                    EditParamOption().getBundle(  pair.second, title)
                                )
                            }
                        }

                    }
                }
            )

            next.setOnClickListener {
                view.findNavController()
                    .navigate(R.id.action_paramCharacterSystemSettingsFragment_to_paramItemSystemSettingsFragment)
            }
        }
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

}

class ParamTypeDialogFragment(
    private val add: Add, private val objHelp: Help,
    private val str: Boolean, private val num: Boolean, private val options: Boolean
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.choose_type_param, container, false)

        val binding = ChooseTypeParamBinding.bind(view)
        fun bind() = with(binding) {

            fun setVisible(bool: Boolean, linLay: LinearLayout) {
                if (bool) {
                    linLay.visibility = View.VISIBLE
                } else {
                    linLay.visibility = View.GONE
                }
            }
            setVisible(str, linStr)
            setVisible(num, linNum)
            setVisible(options, linDD)

            string.setOnClickListener {
                add.add("str")
                dismiss()
            }

            number.setOnClickListener {
                add.add("num")
                dismiss()
            }

            dd.setOnClickListener {
                add.add("options")
                dismiss()
            }

            help.setOnClickListener {
                objHelp.help()
            }
        }
        bind()

        return view
    }

    interface Add {
        fun add(type: String)
    }

    interface Help {
        fun help()
    }
}