package com.example.test.widgets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.data_base.SpecialGameData
import com.example.test.databinding.FightAttackBinding
import com.example.test.databinding.ModificatorsBinding
import com.example.test.helpers.ModAdapterRV
import com.example.test.helpers.ModTemplateHolder

class Modificators : Fragment(), ModTemplateHolder.LoadFragment {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.modificators, container, false)
        val binding = ModificatorsBinding.bind(view)

        fun bind() = with(binding) {

            modRV.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            val list = mutableListOf<Boolean>()
            val adapter = ModAdapterRV(list, this@Modificators)
            modRV.adapter = adapter

            addMod.setOnClickListener {
                adapter.addMod(true)// добавить выбор стиля true - DD, false - PM
            }
        }

        bind()
        return view
    }
    override fun loadFragment(position: Int, style: Boolean) {
        if (style){
            val bundle = Bundle()
            bundle.putString("main", "Выберите можификатор")
            bundle.putString("them", "blue")
            val options = SpecialGameData().modName
            bundle.putStringArrayList("list", options)
            val fragment = DropDownList()
            fragment.arguments = bundle
            childFragmentManager.commit {
                replace(R.id.fr, fragment)
                addToBackStack(null)
            }
        }else{
            val bundle = Bundle()
            bundle.putInt("value", 0)
            bundle.putInt("minValue", 0)
            bundle.putInt("maxValue", 30)
            bundle.putString("them", "blue")
            val fragment = PlusAndMinus()
            fragment.arguments = bundle
            childFragmentManager.commit {
                replace(R.id.fr, fragment)
                addToBackStack(null)
            }
        }
    }
}