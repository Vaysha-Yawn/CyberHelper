package com.example.test.activity_and_fragments.setting.system

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.databinding.FragmentGroupSystemSettingsBinding
import com.example.test.databinding.FragmentReviewSystemSettingsBinding
import com.example.test.viewModels.GameSystemDAO
import com.example.test.viewModels.SystemSettingsVM
import com.example.test.views.HeaderView


class GroupSystemSettingsFragment : Fragment(), HeaderView.HeaderBack {

    private val gameSystemDAO:GameSystemDAO by activityViewModels()
    private val systemSettingsVM:SystemSettingsVM by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_group_system_settings, container, false)
        val binding = FragmentGroupSystemSettingsBinding.bind(view)
        with(binding){
            header.setBack(true, this@GroupSystemSettingsFragment, requireActivity(), viewLifecycleOwner)
            next.setOnClickListener {
                view.findNavController().navigate(R.id.action_groupSystemSettingsFragment_to_typeItemSystemSettingsFragment)
            }
        }
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

}