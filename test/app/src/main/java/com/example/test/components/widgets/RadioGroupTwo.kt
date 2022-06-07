package com.example.test.components.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.test.R
import com.example.test.databinding.RadioGroupTwoBinding

class RadioGroupTwo(private val onCheckedRadio: OnCheckedRadio?) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.radio_group_two, container, false)
        val args = arguments
        val text1 = args?.getString("text1", "") ?: ""
        val text2 = args?.getString("text2", "") ?: ""
        val binding = RadioGroupTwoBinding.bind(view)
        fun bind() = with(binding) {
            radio1.text = text1
            radio2.text = text2
            if (onCheckedRadio!=null){
                group.setOnCheckedChangeListener { radioGroup, checkedId ->
                    when (checkedId) {
                        R.id.radio1 -> {
                            onCheckedRadio.checkedRadio1()
                        }
                        R.id.radio2 -> {
                            onCheckedRadio.checkedRadio2()
                        }
                    }
                }
            }
        }
        bind()
        return view
    }

    interface OnCheckedRadio{
        fun checkedRadio1()
        fun checkedRadio2()
    }

}