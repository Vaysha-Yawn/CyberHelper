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
import com.example.test.data_base.ParamStr
import com.example.test.databinding.EditGroupBinding
import com.example.test.databinding.EditParamStringBinding
import com.example.test.settings.presentation.view_model.CreateSystemVM


class EditParamStr : Fragment(), HeaderView.HeaderBack {

    private val createSystemVM: CreateSystemVM by activityViewModels()
    private var section = 6
    private var posInSec = -1
    private var groupTitle = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        section = arguments?.getInt("section") ?: 6
        posInSec = arguments?.getInt("posInSec") ?: -1
        groupTitle = arguments?.getString("groupTitle") ?: ""
        Log.e("e", "section $section , position $posInSec")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_param_string, container, false)
        val binding = EditParamStringBinding.bind(view)
        with(binding) {
            header.setBack(true, this@EditParamStr, requireActivity(), viewLifecycleOwner)
            if (posInSec == -1 || section == 6 || groupTitle=="") {
                Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
                view.findNavController().popBackStack()
            }
            var param = createSystemVM.characterParamsStr[groupTitle]?.get(posInSec)
            if (param !=null){
                val names = param.name
                val removables = param.removable
                (name as TextView).text = names
                removable.isChecked = removables
            }else{
                param = ParamStr()
                createSystemVM.characterParamsStr[groupTitle]?.set(posInSec, param)
            }

            name.doOnTextChanged { text, start, before, count ->
                param.name = text.toString()
            }
            removable.setOnClickListener {
                param.removable = removable.isChecked
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

    fun getBundle(section:Int, posInSec:Int, groupTitle:String):Bundle{
        val bundle = Bundle()
        bundle.putInt("section", section)
        bundle.putInt("posInSec", posInSec)
        bundle.putString("groupTitle", groupTitle)
        return bundle
    }

}