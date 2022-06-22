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
import com.example.test.databinding.EditParamStringBinding
import com.example.test.settings.presentation.view_model.CreateSystemVM


class EditParamStr : Fragment(), HeaderView.HeaderBack {

    private val createSystemVM: CreateSystemVM by activityViewModels()
    private var idParam = -1
    private var idGroup = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        idParam = arguments?.getInt("idParam") ?: -1
        idGroup = arguments?.getInt("groupTitle") ?: -1
        //Log.e("e", " position $idParam")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_param_string, container, false)
        val binding = EditParamStringBinding.bind(view)
        with(binding) {
            header.setBack(true, this@EditParamStr, requireActivity(), viewLifecycleOwner)
            if (idParam == -1 ||  idGroup==-1) {
                Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
                view.findNavController().popBackStack()
            }
            var param = createSystemVM.paramsStr.get(posInSec)
            val names = param.name
            val removables = param.removable
            (name as TextView).text = names
            removable.isChecked = removables

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

    fun getBundle( idParam:Int, idGroup: Int):Bundle{
        val bundle = Bundle()
        bundle.putInt("idParam", idParam)
        bundle.putInt("idGroup", idGroup)
        return bundle
    }

}