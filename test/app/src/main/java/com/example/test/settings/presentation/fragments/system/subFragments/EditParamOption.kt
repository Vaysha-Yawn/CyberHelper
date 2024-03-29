package com.example.test.settings.presentation.fragments.system.subFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.components.views.compact_view.CompactViewEdit
import com.example.test.components.views.HeaderView
import com.example.test.databinding.EditParamOptionsBinding
import com.example.test.settings.presentation.view_model.CreateSystemVM


class EditParamOption : Fragment(), HeaderView.HeaderBack {

    private val createSystemVM: CreateSystemVM by activityViewModels()
    private var idParam = -1
    private var groupTitle = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idParam = arguments?.getInt("idParam") ?: -1
        groupTitle = arguments?.getString("groupTitle") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_param_options, container, false)
        val binding = EditParamOptionsBinding.bind(view)
        with(binding) {
            header.setBack(true, this@EditParamOption, requireActivity(), viewLifecycleOwner)
            if (idParam == -1 || groupTitle == "") {
                Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
                view.findNavController().popBackStack()
            }
            val param = createSystemVM.getParamOption(groupTitle, idParam)
            if (param!=null){
                val names = param.name
                val removables = param.removable
                val defMains = param.defMain
                val options = param.options
                (name as TextView).text = names
                (defMain as TextView).text = defMains
                removable.isChecked = removables
                CVEdit.setData(options)
            }

            name.doOnTextChanged { text, start, before, count ->
                param?.name = text.toString()
            }
            defMain.doOnTextChanged { text, start, before, count ->
                param?.defMain = text.toString()
            }

            CVEdit.setListener(object : CompactViewEdit.OnClickEdit {
                override fun onClickEdit(
                    posEdit: Int,
                    text: String,
                ) {
                    param?.options?.set(posEdit, text)
                }
            },
                object : CompactViewEdit.OnClickAdd {
                    override fun onClickAdd() {
                        // ничего добавлять не нужно
                    }
                },
                object : CompactViewEdit.OnClickDel {
                    override fun onClickDel(pos: Int) {
                        param?.options?.removeAt(pos)
                    }
                }
            )

            removable.setOnClickListener {
                param?.removable = removable.isChecked
            }

            next.setOnClickListener {
                // проверка?
                view.findNavController().popBackStack()
            }
        }
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

    fun getBundle(idParam: Int, groupTitle: String): Bundle {
        val bundle = Bundle()
        bundle.putInt("idParam", idParam)
        bundle.putString("groupTitle", groupTitle)
        return bundle
    }

}