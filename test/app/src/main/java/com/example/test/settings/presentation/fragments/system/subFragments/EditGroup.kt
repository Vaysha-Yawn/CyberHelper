package com.example.test.settings.presentation.fragments.system.subFragments

import android.os.Bundle
import android.util.Log
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
import com.example.test.components.views.HeaderView
import com.example.test.data_base.GroupParam
import com.example.test.databinding.EditGroupBinding
import com.example.test.settings.presentation.view_model.CreateSystemVM


class EditGroup : Fragment(), HeaderView.HeaderBack {

    private val createSystemVM: CreateSystemVM by activityViewModels()
    private var section = 6
    private var posInSec = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        section = arguments?.getInt("section") ?: 6
        posInSec = arguments?.getInt("posInSec") ?: -1
        Log.e("e", "section $section , position $posInSec")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_group, container, false)
        val binding = EditGroupBinding.bind(view)
        with(binding) {
            header.setBack(true, this@EditGroup, requireActivity(), viewLifecycleOwner)
            if (posInSec == -1 || section == 6) {
                Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
                view.findNavController().popBackStack()
            }
            var group = createSystemVM.groups[section][posInSec]
            if (group !=null){
                val name = group.title
                val pefNum = group.prefNum
                val prefDD = group.prefDD
                val prefStr = group.prefStr
                val prefItem = group.prefItem
                (editText as TextView).text = name
                num.isChecked = pefNum
                dd.isChecked = prefDD
                str.isChecked = prefStr
                item.isChecked = prefItem
            }else{
                group = GroupParam()
                createSystemVM.groups[section].set(posInSec, group)
            }

            editText.doOnTextChanged { text, start, before, count ->
                group.title = text.toString()
                createSystemVM.listGroupNames[section][posInSec] = group.title
            }
            num.setOnClickListener {
                group.prefNum = num.isChecked
            }
            str.setOnClickListener {
                group.prefStr = str.isChecked
            }
            dd.setOnClickListener {
                group.prefDD = dd.isChecked
            }
            item.setOnClickListener {
                group.prefItem = item.isChecked
            }

            help.setOnClickListener {
                view.findNavController().navigate(R.id.action_editGroup_to_tipTypeParam)
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

    fun getEditGroupBundle(section:Int, posInSec:Int):Bundle{
        val bundle = Bundle()
        bundle.putInt("section", section)
        bundle.putInt("posInSec", posInSec)
        return bundle
    }

}