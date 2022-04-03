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
        /////////////////////////////////
        val goal = arguments?.getString("goal")
        var keyAllGoals = arguments?.getInt("keyAllGoals")
        var keyRolls = arguments?.getInt("keyRolls")
        /////////////////////////////////

        val rollId = mSkillVM.createId()

        /////////////////////////////////
        if (mSkillVM.mapGoal[keyAllGoals]?.value.isNullOrEmpty() || keyAllGoals == null || keyRolls == null) {
            //Toast.makeText(requireContext(), "Сгенерировано заново", Toast.LENGTH_SHORT).show()
            keyAllGoals = mSkillVM.createId()
            keyRolls = mSkillVM.createId()

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
            mSkillVM.mapRoll[keyRolls] = mutableMapOf()
        }
        mSkillVM.mapRoll[keyRolls]?.put(rollId, RollObject(Goal(), 0, null, mutableListOf<Mod>()))
        /////////////////////////////////
        fun loadFragmentLight(fragment: Fragment, id: Int) {
            childFragmentManager.commit {
                replace(id, fragment)
                addToBackStack(null)
            }
        }
        /////////////////////////////////
        val key1d10 = mSkillVM.createId()
        mSkillVM.mapInt[key1d10] = MutableLiveData<Int>()
        mSkillVM.mapInt[key1d10]?.value = 1

        val keyCrit = mSkillVM.createId()
        mSkillVM.mapInt[keyCrit] = MutableLiveData<Int>()
        mSkillVM.mapInt[keyCrit]?.value = 1

        val fragment = m1D10()
        val bundle = Bundle()
        bundle.putInt("key1d10", key1d10)
        bundle.putInt("keyCrit", keyCrit)
        fragment.arguments = bundle
        loadFragmentLight(fragment, R.id.m1d10Fr)
        /////////////////////////////////
        val keyListMod = mSkillVM.createId()
        val fragmentM = Modificators()
        val bundleM = Bundle()
        bundleM.putInt("keyListMod", keyListMod)
        fragmentM.arguments = bundleM
        loadFragmentLight(fragmentM, R.id.modFr)
        /////////////////////////////////
        if (goal != null && goal != "") {
            val fragmentF = GoalDD()
            val bundleF = Bundle()
            bundleF.putString("main", "Выберите цель")
            bundleF.putString("them", "yellow")
            bundleF.putInt("keyAllGoals", keyAllGoals)
            bundleF.putInt("keyRolls", keyRolls)
            bundleF.putInt("rollId", rollId)
            fragmentF.arguments = bundleF
            loadFragmentLight(fragmentF, R.id.goalFr)
        } else {
            view.findViewById<FragmentContainerView>(R.id.goalFr).visibility = View.GONE
        }
        /////////////////////////////////

        mSkillVM.mapInt[key1d10]?.observe(viewLifecycleOwner) {
            if (it != null) {
                mSkillVM.mapRoll[keyRolls]?.get(rollId)?.m1D10 = it
            }
        }

        mSkillVM.mapInt[keyCrit]?.observe(viewLifecycleOwner) {
            if (it != null) {
                mSkillVM.mapRoll[keyRolls]?.get(rollId)?.crit = it
            }
        }

        mSkillVM.mapMod[keyListMod]?.observe(viewLifecycleOwner) {
            if (it != null) {
                mSkillVM.mapRoll[keyRolls]?.get(rollId)?.modificators = it
            }
        }

        /////////////////////////////////
        return view
    }

}

data class RollObject(
    var goal: Goal,// цель есть всегда, но когда-то это сам игрок, а когда-то кто-то другой
    var m1D10: Int,
    var crit: Int?,
    var modificators: MutableList<Mod>
)