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
    private var pos = -1
    private var tileGroup = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pos = arguments?.getInt("pos") ?: -1
        tileGroup = arguments?.getString("tileGroup") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_param_string, container, false)
        val binding = EditParamStringBinding.bind(view)
        with(binding) {
            header.setBack(true, this@EditParamStr, requireActivity(), viewLifecycleOwner)
            if (pos == -1 ||  tileGroup=="") {
                Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
                view.findNavController().popBackStack()
            }
            val param = createSystemVM.getParamStr(tileGroup, pos)
            if (param!=null){
                val names = param.name
                val removables = param.removable
                (name as TextView).text = names
                removable.isChecked = removables
            }

            name.doOnTextChanged { text, start, before, count ->
                param?.name = text.toString()
            }
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

    fun getBundle( pos:Int, tileGroup: String):Bundle{
        val bundle = Bundle()
        bundle.putInt("pos", pos)
        bundle.putString("tileGroup", tileGroup)
        return bundle
    }

}