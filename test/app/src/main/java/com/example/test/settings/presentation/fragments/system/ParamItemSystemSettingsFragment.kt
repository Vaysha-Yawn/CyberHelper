package com.example.test.settings.presentation.fragments.system

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.components.views.CompactViewString
import com.example.test.components.views.HeaderView
import com.example.test.data_base.ParamNum
import com.example.test.data_base.ParamOptions
import com.example.test.data_base.ParamStr
import com.example.test.databinding.FragmentParamsItemSystemSettingsBinding
import com.example.test.settings.presentation.fragments.system.subFragments.EditParamNum
import com.example.test.settings.presentation.fragments.system.subFragments.EditParamOption
import com.example.test.settings.presentation.fragments.system.subFragments.EditParamStr
import com.example.test.settings.presentation.view_model.CreateSystemVM


class ParamItemSystemSettingsFragment : Fragment(), HeaderView.HeaderBack {

    private val createSystemVM: CreateSystemVM by activityViewModels()
    private val Title = "item"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_params_item_system_settings, container, false)
        val binding = FragmentParamsItemSystemSettingsBinding.bind(view)
        with(binding) {
            header.setBack(
                true,
                this@ParamItemSystemSettingsFragment,
                requireActivity(),
                viewLifecycleOwner
            )
            val pairList = createSystemVM.getListsParamItem()

            fun getPair(posEdit:Int):Pair<String, Int>{
                return pairList.second[posEdit]
            }

            CV.setData(pairList.first)
            CV.setListener(
                object : CompactViewString.OnClickEdit {
                    override fun onClickEdit(posEdit: Int) {
                        val pairQ = getPair(posEdit)
                        when (pairQ.first) {
                            createSystemVM.STR -> {
                                view.findNavController().navigate(
                                    R.id.action_paramItemSystemSettingsFragment_to_editParamStr,
                                    EditParamStr().getBundle(pairQ.second, Title)
                                )
                            }
                            createSystemVM.NUM -> {
                                view.findNavController().navigate(
                                    R.id.action_paramItemSystemSettingsFragment_to_editParamNum,
                                    EditParamNum().getBundle(pairQ.second, Title)
                                )
                            }
                            createSystemVM.OPTIONS -> {
                                view.findNavController().navigate(
                                    R.id.action_paramItemSystemSettingsFragment_to_editParamOption,
                                    EditParamOption().getBundle(pairQ.second, Title)
                                )
                            }
                        }
                    }
                },
                object : CompactViewString.OnClickAdd {
                    override fun onClickAdd() {
                        val dialogFragment = ParamTypeDialogFragment(
                            object : ParamTypeDialogFragment.Add {
                                override fun add(type: String) {
                                    val id = createSystemVM.addParamItem(type)
                                    when (type) {
                                        createSystemVM.STR -> {
                                            view.findNavController().navigate(
                                                R.id.action_paramItemSystemSettingsFragment_to_editParamStr,
                                                EditParamStr().getBundle(id, Title)
                                            )
                                        }
                                        createSystemVM.NUM -> {
                                            view.findNavController().navigate(
                                                R.id.action_paramItemSystemSettingsFragment_to_editParamNum,
                                                EditParamNum().getBundle(id, Title)
                                            )
                                        }
                                        createSystemVM.OPTIONS -> {
                                            view.findNavController().navigate(
                                                R.id.action_paramItemSystemSettingsFragment_to_editParamOption,
                                                EditParamOption().getBundle(id, Title)
                                            )
                                        }
                                    }
                                }
                            },
                            object : ParamTypeDialogFragment.Help {
                                override fun help() {
                                    view.findNavController()
                                        .navigate(R.id.action_paramItemSystemSettingsFragment_to_tipTypeParam)
                                }
                            },
                            true,
                            true,
                            true
                        )
                        dialogFragment.show(childFragmentManager, "chooseMod")
                    }
                },
                object : CompactViewString.OnClickDel {
                    override fun onClickDel(pos: Int) {
                        val pairQ = getPair(pos)
                        createSystemVM.delParamItem(pairQ.second, pairQ.first)
                    }
                },
            )
            next.setOnClickListener {
                view.findNavController()
                    .navigate(R.id.action_paramItemSystemSettingsFragment_to_templateItemSystemSettingsFragment)
            }
        }
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

}