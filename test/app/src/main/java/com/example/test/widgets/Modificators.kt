package com.example.test.widgets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.data_base.SpecialGameData
import com.example.test.databinding.ModificatorsBinding
import com.example.test.helpers.ModAdapterRV
import com.example.test.helpers.ModTemplateHolder
import com.example.test.viewModels.SkillTestVM

class Modificators : Fragment(), ModTemplateHolder.LoadFragment,  ModTemplateHolder.DeleteMod, ModTemplateHolder.updIdMod {

    private val mSkillVM: SkillTestVM by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.modificators, container, false)
        try{
        val binding = ModificatorsBinding.bind(view)


        fun bind() = with(binding) {
            modRV.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            val adapter = ModAdapterRV(this@Modificators, this@Modificators, this@Modificators,)
            modRV.adapter = adapter
            mSkillVM.modification.observe(viewLifecycleOwner){
                adapter.setData(it)
            }
            addMod.setOnClickListener {
                mSkillVM.modification.value!!.add( Mod(true, 0, 0))
                adapter.notifyDataSetChanged()
            }
        }
        bind()
        } catch (e: Exception) {
        Toast.makeText(view.context, "$e", Toast.LENGTH_LONG).show()
    }
        return view
    }

    override fun loadFragment(position: Int, style: Boolean, value:Int, id:Int) {
        if (style){
            val bundle = Bundle()
            bundle.putString("main", "Выберите модификатор")
            bundle.putString("them", "blue")
            bundle.putInt("indexMod", position)
            bundle.putString("goal", "modification")
            bundle.putInt("value", value)
            val options = SpecialGameData().modName
            bundle.putStringArrayList("list", options)
            val fragment = DropDownList()
            fragment.arguments = bundle
            childFragmentManager.commit {
                replace(id, fragment)
                addToBackStack(null)
            }
        } else {
            val bundle = Bundle()
            bundle.putInt("value", 0)
            bundle.putInt("minValue", 0)
            bundle.putInt("maxValue", 30)
            bundle.putString("them", "blue")
            bundle.putString("goal", "mod")
            bundle.putInt("indexMod", position)
            val fragment = PlusAndMinus()
            fragment.arguments = bundle
            childFragmentManager.commit {
                replace(id, fragment)
                addToBackStack(null)
            }
        }
    }

    override fun deleteMod(position: Int) {
        mSkillVM.modification.value!!.removeAt(position)
    }

    override fun updIdMod(position: Int, id: Int) {
        mSkillVM.modification.value!![position].resId = id
    }

}

data class Mod(var style: Boolean, var value: Int , var resId: Int )