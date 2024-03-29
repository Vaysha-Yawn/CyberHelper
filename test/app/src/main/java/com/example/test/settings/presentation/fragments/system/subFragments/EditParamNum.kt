package com.example.test.settings.presentation.fragments.system.subFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.components.views.HeaderView
import com.example.test.components.views.PlusMinusView
import com.example.test.databinding.EditParamNumBinding
import com.example.test.settings.presentation.view_model.CreateSystemVM


class EditParamNum : Fragment(), HeaderView.HeaderBack {

    private val createSystemVM: CreateSystemVM by activityViewModels()
    private var idParam = -1
    private var groupTitle = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idParam = arguments?.getInt("idParam") ?: -1
        groupTitle = arguments?.getString("groupTitle") ?: ""
        Log.e("e", " position $idParam")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_param_num, container, false)
        val binding = EditParamNumBinding.bind(view)
        with(binding) {
            header.setBack(true, this@EditParamNum, requireActivity(), viewLifecycleOwner)
            fun setVisible(checkBox: CheckBox, PM:PlusMinusView){
                if (checkBox.isChecked){
                    PM.visibility = View.VISIBLE
                }else{
                    PM.visibility = View.GONE
                }
            }
            if (idParam == -1 || groupTitle=="") {
                Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
                view.findNavController().popBackStack()
            }
            val param = createSystemVM.getParamNum(groupTitle, idParam)
            val names = param?.name
            val removables = param?.removable
            value.setValue(param?.value?:0)
            if (param?.maxValue != null){
                booleanMaxValue.isChecked = true
                maxValue.setValue(param.maxValue!!)
            }
            setVisible(booleanMaxValue, maxValue)
            if (param?.minValue != null){
                booleanMinValue.isChecked = true
                minValue.setValue(param.minValue!!)
            }
            setVisible(booleanMinValue, minValue)
            (name as TextView).text = names
            if (removables != null) {
                removable.isChecked = removables
            }

            name.doOnTextChanged { text, start, before, count ->
                param?.name = text.toString()
            }
            removable.setOnClickListener {
                param?.removable = removable.isChecked
            }
            booleanMinValue.setOnClickListener {
                setVisible(booleanMinValue, minValue)
            }
            booleanMaxValue.setOnClickListener {
                setVisible(booleanMaxValue, maxValue)
            }
            value.setListener(null, null, object: PlusMinusView.NumberEvent{
                override fun numberEvent(number: Int) {
                    param?.value = number
                }
            })
            maxValue.setListener(null, null, object: PlusMinusView.NumberEvent{
                override fun numberEvent(number: Int) {
                    param?.maxValue = number
                }
            })
            minValue.setListener(null, null, object: PlusMinusView.NumberEvent{
                override fun numberEvent(number: Int) {
                    param?.minValue = number
                }
            })
            next.setOnClickListener {
                // проверка?
                if (!booleanMinValue.isChecked){
                    param?.minValue = null
                }
                if (!booleanMaxValue.isChecked){
                    param?.maxValue = null
                }
                view.findNavController().popBackStack()
            }
        }
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

    fun getBundle(idParam:Int, groupTitle:String):Bundle{
        val bundle = Bundle()
        bundle.putInt("idParam", idParam)
        bundle.putString("groupTitle", groupTitle)
        return bundle
    }

}