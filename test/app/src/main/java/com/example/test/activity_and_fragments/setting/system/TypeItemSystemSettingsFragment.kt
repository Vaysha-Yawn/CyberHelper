package com.example.test.activity_and_fragments.setting.system

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.databinding.FragmentReviewSystemSettingsBinding
import com.example.test.databinding.FragmentTypeItemsSystemSettingsBinding
import com.example.test.viewModels.GameSystemDAO
import com.example.test.viewModels.SystemSettingsVM
import com.example.test.views.HeaderView


class TypeItemSystemSettingsFragment : Fragment(), HeaderView.HeaderBack {

    private val gameSystemDAO:GameSystemDAO by activityViewModels()
    private val systemSettingsVM:SystemSettingsVM by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_type_items_system_settings, container, false)
        val binding = FragmentTypeItemsSystemSettingsBinding.bind(view)
        with(binding){
            header.setBack(true, this@TypeItemSystemSettingsFragment, requireActivity(), viewLifecycleOwner)
            next.setOnClickListener {
                view.findNavController().navigate(R.id.action_typeItemSystemSettingsFragment_to_paramCharacterSystemSettingsFragment)
            }
        }
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

}