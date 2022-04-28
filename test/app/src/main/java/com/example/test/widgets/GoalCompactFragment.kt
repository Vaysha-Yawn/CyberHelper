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
import com.example.test.adapters.GoalCompactAdapterRV
import com.example.test.adapters.GoalCompactTemplateHolder
import com.example.test.data_base.Goal
import com.example.test.databinding.FragmentGoalCompactBinding
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.SkillTestVM
import kotlin.properties.Delegates

class GoalCompactFragment : Fragment(), GoalCompactTemplateHolder.DeleteGoalCompact, GoalCompactTemplateHolder.PutGoalCompactValue {

    private val mCharacterDAO: CharacterDAO by activityViewModels()
    private val mSkillVM: SkillTestVM by activityViewModels()
    private var keyListGoal by Delegates.notNull<Int>()
    private val allGoalsList = mutableListOf<Goal>()
    private lateinit var adapter: GoalCompactAdapterRV

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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
                    allGoalsList.add(goal)
                }
            }
        }

        adapter = GoalCompactAdapterRV(this, this, allGoalsList)

        val view = inflater.inflate(R.layout.fragment_goal_compact, container, false)

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
}

