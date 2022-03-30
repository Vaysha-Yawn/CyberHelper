package com.example.test.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.activity_and_fragments.hosts.PresentHost
import com.example.test.databinding.FewRollBinding
import com.example.test.helpers.RadioAdapterRV
import com.example.test.helpers.RollAdapter
import com.example.test.viewModels.FewRollVM


class FewRoll : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.few_roll, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FewRollBinding.bind(view)

        val VM = ViewModelProvider(this)[FewRollVM::class.java]

        fun bind() = with(binding) {
            val adapter = RollAdapter(this@FewRoll)
            adapter.setData(listOf(R.id.radioButton))
            VP2.adapter = adapter

            val adapterRV = RadioAdapterRV()
            radioRV.adapter = adapterRV
            radioRV.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
            VM.list.observe(viewLifecycleOwner) {
                adapterRV.setData(it)
            }

            delete.setOnClickListener {
                val id = radioGroup.checkedRadioButtonId
                VM.list.value?.remove(id)
                Toast.makeText(view.context, "$id", Toast.LENGTH_SHORT).show()
            }
            add.setOnClickListener {
                val id = View.generateViewId()
                VM.list.value?.add(id)
                Toast.makeText(view.context, "$id", Toast.LENGTH_SHORT).show()
            }
        }
        bind()
    }

}