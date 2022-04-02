package com.example.test.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.MutableLiveData
import com.example.test.R
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.SkillTestVM

class Roll : Fragment() {

    private val mSkillVM: SkillTestVM by activityViewModels()
    private val mCharacterVM: CharacterDAO by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.roll, container, false)
        val goal = arguments?.getString("goal")
        var keyAllGoals = arguments?.getInt("keyAllGoals")

        if (mSkillVM.mapGoal[keyAllGoals]?.value.isNullOrEmpty() || keyAllGoals == null) {

            keyAllGoals = mSkillVM.createId()

            mSkillVM.mapGoal[keyAllGoals] = MutableLiveData()
            // заполняем лист
            val goalsList = mutableListOf<Goal>()
            mCharacterVM.characterList.value?.forEach {
                if (it.gameId == mCharacterVM.gameId) {
                    if (it.id != mCharacterVM.characterId) {
                        val goale = Goal()
                        goale.characterId = it.id
                        val name = it.attributes.singleOrNull { gp ->
                            gp.title == "Базовые параметры"
                        }?.attributes?.listParamStr?.singleOrNull { pn ->
                            pn.name == "Имя персонажа"
                        }?.value ?: ""
                        goale.name = name
                        goalsList.add(goale)
                    }
                }
            }
            mSkillVM.mapGoal[keyAllGoals]?.value = goalsList
        }

        fun loadFragmentLight(fragment: Fragment, id: Int) {
            childFragmentManager.commit {
                replace(id, fragment)
                addToBackStack(null)
            }
        }
        loadFragmentLight(m1D10(), R.id.m1d10Fr)
        loadFragmentLight(Modificators(), R.id.modFr)
        if (goal != null && goal != "") {
            val fragment = GoalDD()
            val bundle = Bundle()
            bundle.putString("main", "Выберите цель")
            bundle.putString("them", "yellow")
            bundle.putInt("keyAllGoals", keyAllGoals)
            fragment.arguments = bundle
            childFragmentManager.commit {
                replace(R.id.goalFr, fragment)
                addToBackStack(null)
            }
        } else {
            view.findViewById<FragmentContainerView>(R.id.goalFr).visibility = View.GONE
        }
        return view
    }


}

data class GoalRoll(
    var goal: Goal?,
    var mods: MutableList<Mod>,
    var m1d10: Int,
    var crit: Int?
)