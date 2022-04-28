package com.example.test.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.data_base.Goal
import com.example.test.databinding.GoalsBinding
import com.example.test.adapters.GoalAdapterRV
import com.example.test.adapters.GoalTemplateHolder
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.SkillTestVM

class Goals : Fragment(), GoalTemplateHolder.LoadFragment, GoalTemplateHolder.DeleteGoal,
    GoalTemplateHolder.updIdGoal {
    private val mCharacterDAO: CharacterDAO by activityViewModels()
    private val mSkillVM: SkillTestVM by activityViewModels()
    private val adapter = GoalAdapterRV(this, this, this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val goalsList = mutableListOf<Goal>()
        mCharacterDAO.characterList.value?.forEach {
            if (it.gameId == mCharacterDAO.gameId) {
                if (it.id != mCharacterDAO.characterId) {
                    val goal = Goal()
                    goal.characterId = it.id
                    val name = it.attributes.singleOrNull { gp ->
                        gp.title == "Базовые параметры"
                    }?.attributes?.listParamStr?.singleOrNull { pn ->
                        pn.name == "Имя персонажа"
                    }?.value ?: ""
                    goal.name = name
                    goalsList.add(goal)
                }
            }
        }
        //mSkillVM.allGoals.value = goalsList

        val view = inflater.inflate(R.layout.goals, container, false)

            val binding = GoalsBinding.bind(view)

            fun bind() = with(binding) {
                modRV.layoutManager =
                    LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
                modRV.adapter = adapter
                /*mSkillVM.chosenGoals.observe(viewLifecycleOwner) {
                    adapter.setData(it)
                    adapter.notifyDataSetChanged()
                }*/
                addMod.setOnClickListener {
                    try {
                    addGoal()
                    } catch (e: Exception) {
                        Toast.makeText(view.context, "$e", Toast.LENGTH_LONG).show()
                    }
                }
            }
            bind()

        return view
    }

    override fun loadFragment(position: Int, id: Int) {
        val bundle = Bundle()
        bundle.putString("main", "Выберите цель")
        bundle.putString("them", "blue")
        bundle.putInt("indexMod", position)
        bundle.putString("goal", "goal")
        val options = ArrayList<String>()
        /*for (g in mSkillVM.allGoals.value!!){
            options.add(g.name)
        }
        bundle.putStringArrayList("list", options)
        val fragment = DropDownList()
        fragment.arguments = bundle
        childFragmentManager.commit {
            replace(id, fragment)
            addToBackStack(null)
        }*/
    }

    override fun deleteGoal(position: Int) {
//        val id = mSkillVM.chosenGoals.value!![position].resId
//        mSkillVM.deletedIdByGoal.add(id)
//        mSkillVM.allGoals.value?.add(mSkillVM.chosenGoals.value!![position])
//        mSkillVM.chosenGoals.value!!.removeAt(position)
    }

    override fun updIdMod(position: Int, id: Int) {
        //mSkillVM.allGoals.value!![position].resId = id
    }

    fun addGoal() {
        var id = 0
        /*if (mSkillVM.deletedIdByGoal.isNotEmpty()) {
            id = mSkillVM.deletedIdByGoal.minOrNull() ?: 0
            if (id != 0) {
                mSkillVM.deletedIdByGoal.remove(id)
            }
        }
        mSkillVM.chosenGoals.value?.add(Goal("randomName", id, 0))*/
        adapter.notifyDataSetChanged()
    }
}

