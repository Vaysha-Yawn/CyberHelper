package com.example.test.settings.presentation.fragments.system.subFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.components.views.HeaderView
import com.example.test.databinding.TipTypeParamBinding


class TipTypeParam : Fragment(), HeaderView.HeaderBack {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.tip_type_param, container, false)
        val binding = TipTypeParamBinding.bind(view)
        with(binding) {
            header.setBack(true, this@TipTypeParam, requireActivity(), viewLifecycleOwner)
            next.setOnClickListener {
                view.findNavController().popBackStack()
            }
        }
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

}