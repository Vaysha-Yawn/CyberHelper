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
import com.example.test.components.views.CompactViewString
import com.example.test.components.views.HeaderView
import com.example.test.databinding.FragmentGroupSystemSettingsBinding
import com.example.test.settings.presentation.fragments.system.subFragments.EditGroup
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
        with(binding) {
            header.setBack(
                true,
                this@GroupSystemSettingsFragment,
                requireActivity(),
                viewLifecycleOwner
            )

            fun setListenerForCVStr(compactViewString: CompactViewString, index: Int) {
                compactViewString.setListener(object : CompactViewString.OnClickEdit {
                    override fun onClickEdit(posEdit: Int) {
                        val bundle = EditGroup().getEditGroupBundle(index, posEdit)
                        view.findNavController()
                            .navigate(
                                R.id.action_groupSystemSettingsFragment_to_editGroup,
                                bundle
                            )
                    }
                },
                    object : CompactViewString.OnClickAdd {
                        override fun onClickAdd() {
                            createSystemVM.groups[index].add(null)
                        }
                    },
                    object : CompactViewString.OnClickDel {
                        override fun onClickDel(pos: Int) {
                            createSystemVM.groups[index].removeAt(pos)
                        }
                    }
                )
                val listGPTitle = createSystemVM.getListNameGroup(index)
                compactViewString.setData(listGPTitle)
            }

            setListenerForCVStr(CV1, 0)
            setListenerForCVStr(CV2, 1)
            setListenerForCVStr(CV3, 2)
            setListenerForCVStr(CV4, 3)
            setListenerForCVStr(CV5, 4)

            next.setOnClickListener {
                // добавить по умолчанию базовые параметры
                view.findNavController()
                    .navigate(R.id.action_groupSystemSettingsFragment_to_typeItemSystemSettingsFragment)
            }
        }
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

}