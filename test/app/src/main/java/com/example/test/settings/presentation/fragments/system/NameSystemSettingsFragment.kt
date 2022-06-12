package com.example.test.settings.presentation.fragments.system

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.databinding.FragmentNameSystemSettingsBinding
import com.example.test.viewModels.GameSystemDAO
import com.example.test.settings.presentation.view_model.SystemSettingsVM
import com.example.test.components.views.HeaderView
import com.example.test.settings.presentation.view_model.CreateSystemVM


class NameSystemSettingsFragment : Fragment(), HeaderView.HeaderBack  {

    private val createSystemVM: CreateSystemVM by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_name_system_settings, container, false)
        val binding = FragmentNameSystemSettingsBinding.bind(view)
        with(binding){
            (editText as TextView).text = createSystemVM.name
            header.setBack(true, this@NameSystemSettingsFragment, requireActivity(), viewLifecycleOwner)
            editText.doOnTextChanged { text, start, before, count ->
                createSystemVM.name = text.toString()
            }
            next.setOnClickListener {
                // сделать проверку на уникальность названия
                if ((editText as TextView).text != ""){
                    view.findNavController().navigate(R.id.action_nameSystemSettingsFragment_to_typeDamageSystemSettingsFragment)
                }else{
                    Toast.makeText(context, "Пожалуйста, введите название", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }
}