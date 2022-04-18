package com.example.test.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.test.R

class Header(private val headerBack: HeaderBack) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewq = inflater.inflate(R.layout.header, container, false)
        viewq.findViewById<ImageButton>(R.id.back).setOnClickListener {
            headerBack.back()
        }
        return viewq
    }

    interface HeaderBack {
        fun back()
    }
}