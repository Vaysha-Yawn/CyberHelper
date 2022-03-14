package com.example.test.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.data_base.SpecialGameData
import com.example.test.databinding.DialogChooseAddModificationBinding
import com.example.test.databinding.ModificatorsBinding
import com.example.test.helpers.GoalAdapterRV
import com.example.test.helpers.ModAdapterRV
import com.example.test.helpers.ModTemplateHolder
import com.example.test.viewModels.SkillTestVM

class Goals : Fragment(), ModTemplateHolder.LoadFragment, ModTemplateHolder.DeleteMod,
    ModTemplateHolder.updIdMod {

    private val mSkillVM: SkillTestVM by activityViewModels()
    private val adapter = GoalAdapterRV(this, this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.modificators, container, false)
        try {
            val binding = ModificatorsBinding.bind(view)

            fun bind() = with(binding) {
                modRV.layoutManager =
                    LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
                modRV.adapter = adapter
                mSkillVM.modification.observe(viewLifecycleOwner) {
                    adapter.setData(it)
                }
                addMod.setOnClickListener {
                    addGoal()
                }
            }
            bind()
        } catch (e: Exception) {
            Toast.makeText(view.context, "$e", Toast.LENGTH_LONG).show()
        }
        return view
    }

    override fun loadFragment(position: Int, id: Int, goal:Goal) {
        if (style) {
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
            bundle.putInt("value", value)
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
        val id = mSkillVM.goals.value!![position].resId
        mSkillVM.deletedIdByGoal.add(id)
        mSkillVM.goals.value!!.removeAt(position)
    }

    override fun updIdMod(position: Int, id: Int) {
        mSkillVM.goals.value!![position].resId = id
    }

    fun addGoal() {
        var id = 0
        if (mSkillVM.deletedIdByGoal.isNotEmpty()) {
            id = mSkillVM.deletedIdByGoal.minOrNull() ?: 0
            if (id != 0) {
                mSkillVM.deletedIdByGoal.remove(id)
            }
        }
        mSkillVM.goals.value!!.add(Goal("", id, 0))
        adapter.notifyDataSetChanged()
    }
}

data class Goal(var name:String, var resId:Int, var characterId:Int)