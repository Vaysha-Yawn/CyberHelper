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
import com.example.test.components.views.compact_view.CVAdapterRV
import com.example.test.components.views.HeaderView
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
                "Добавить характеристику",
                "Название характеристики"
            )
            RV.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            RV.adapter = adapter

            val pair = createSystemVM.getListsParamCharacter()
            adapter.setData(
                pair.first
            )

            fun getParamIdTypeByPos(title: String, pos:Int):Pair<String, Int>?{
                return pair.second[title]?.get(pos)
            }

            adapter.setStrListener(
                object : CVAdapterRV.OnStringAdd {
                    override fun onAdd(adapterPos: Int, title: String) {
                        val group = createSystemVM.getGroup(title)
                        if (group != null && (group.prefStr || group.prefNum || group.prefDD)) {
                            val dialogFragment = ParamTypeDialogFragment(
                                object : ParamTypeDialogFragment.Add {
                                    override fun add(type: String) {
                                        val id = createSystemVM.addParamCharacter(type, title)
                                        when (type) {
                                            createSystemVM.STR -> {
                                                view.findNavController().navigate(
                                                    R.id.action_paramCharacterSystemSettingsFragment_to_editParamStr,
                                                    EditParamStr().getBundle( id, title)
                                                )
                                            }
                                            createSystemVM.NUM -> {
                                                view.findNavController().navigate(
                                                    R.id.action_paramCharacterSystemSettingsFragment_to_editParamNum,
                                                    EditParamNum().getBundle(id, title)
                                                )
                                            }
                                            createSystemVM.OPTIONS -> {
                                                view.findNavController().navigate(
                                                    R.id.action_paramCharacterSystemSettingsFragment_to_editParamOption,
                                                    EditParamOption().getBundle(id, title)
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
                        val param = getParamIdTypeByPos(title, editPos)
                        if (param!=null){
                            createSystemVM.delParamCharacter(title, param.second, param.first)
                        }
                    }
                },
                object : CVAdapterRV.OnStringEdit {
                    override fun onEdit(adapterPos: Int, editPos: Int, title: String) {
                        val pairQ = getParamIdTypeByPos(title, editPos)
                        when (pairQ?.first) {
                            createSystemVM.STR -> {
                                view.findNavController().navigate(
                                    R.id.action_paramCharacterSystemSettingsFragment_to_editParamStr,
                                    EditParamStr().getBundle( pairQ.second, title)
                                )
                            }
                            createSystemVM.NUM -> {
                                view.findNavController().navigate(
                                    R.id.action_paramCharacterSystemSettingsFragment_to_editParamNum,
                                    EditParamNum().getBundle( pairQ.second, title)
                                )
                            }
                            createSystemVM.OPTIONS -> {
                                view.findNavController().navigate(
                                    R.id.action_paramCharacterSystemSettingsFragment_to_editParamOption,
                                    EditParamOption().getBundle(  pairQ.second, title)
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

    val OPTIONS:String = "OPT"
    val NUM:String = "NUMBER"
    val STR:String = "STRING"

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
                add.add(STR)
                dismiss()
            }

            number.setOnClickListener {
                add.add(NUM)
                dismiss()
            }

            dd.setOnClickListener {
                add.add(OPTIONS)
                dismiss()
            }

            help.setOnClickListener {
                objHelp.help()
                dismiss()
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