package com.example.test.components.few_roll.roll.goal_compact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.components.views.drop_down.DropDownAdapterRV
import com.example.test.data_base.realm.other_realm_object.Goal
import com.example.test.databinding.FragmentGoalCompactBinding
import com.example.test.data_base.realm.character.CharacterVM
import com.example.test.viewModels.SkillTestVM

class GoalCompactFragment : Fragment(), GoalCompactTemplateHolder.DeleteGoalCompact,
    GoalCompactTemplateHolder.PutGoalCompactValue,
    DropDownAdapterRV.TemplateHolder.CheckChoose {

    private val mCharacterVM: CharacterVM by activityViewModels()
    private val mSkillVM: SkillTestVM by activityViewModels()
    private var keyListGoal = 0
    private val allGoalsList = mutableListOf<Goal>()
    private lateinit var adapter: GoalCompactAdapterRV

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_goal_compact, container, false)
        //mSkillVM.mapGoal[keyListGoal]?.value =  mutableListOf<Goal>()
        mCharacterVM.characterList.value?.forEach {
            if (it.gameId == mCharacterVM.gameId) {
                if (it.id != mCharacterVM.characterId) {
                    val goal = Goal()
                    goal.characterId = it.id
                    val name = it.attributes.singleOrNull { gp ->
                        gp.title == "Базовые параметры"
                    }?.attributes?.listParamStr?.singleOrNull { pn ->
                        pn.name == "Имя персонажа"
                    }?.value ?: ""
                    goal.name = name
                    allGoalsList.add(goal)
                }
            }
        }

        adapter = GoalCompactAdapterRV(this, this, allGoalsList, this)

        val binding = FragmentGoalCompactBinding.bind(view)

        fun bind() = with(binding) {
            goalRV.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            goalRV.adapter = adapter
            mSkillVM.mapGoal[keyListGoal]?.observe(viewLifecycleOwner) {
                adapter.setData(it)
            }
            addGoal.setOnClickListener {
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

    fun addGoal() {
        mSkillVM.mapGoal[keyListGoal]?.value?.add(Goal())
        adapter.notifyItemInserted(mSkillVM.mapGoal[keyListGoal]?.value?.size ?: 1 - 1)
    }

    override fun deleteGoalCompact(position: Int) {
        mSkillVM.mapGoal[keyListGoal]?.value?.removeAt(position)
    }

    override fun putGoalCompactValue(position: Int, value: Int) {
        mSkillVM.mapGoal[keyListGoal]?.value?.set(position, allGoalsList[value])
    }

    fun getChosenGoals(): MutableList<Goal> {
        return mSkillVM.mapGoal[keyListGoal]?.value!!
    }

    override fun checkChoose(position: Int): Boolean {
        var r = true
        val chosenGoal = allGoalsList[position]
        val map = mSkillVM.mapGoal[keyListGoal]?.value
        if (map != null) {
            for (value in map) {
                if (value == chosenGoal) {
                    r = false
                }
            }
        }else{
            r = false
        }
        return r
    }

    override fun onCheckedFalse() {
        Toast.makeText(
            requireContext(),
            "Вы уже выбрали эту цель, пожалуйста выберите другую",
            Toast.LENGTH_SHORT
        ).show()
    }
}

