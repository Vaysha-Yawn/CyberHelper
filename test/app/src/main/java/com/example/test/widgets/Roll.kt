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
import kotlin.properties.Delegates

class Roll : Fragment() {

    private val mSkillVM: SkillTestVM by activityViewModels()
    private val mCharacterVM: CharacterDAO by activityViewModels()
    private var keyFragment by Delegates.notNull<Int>()
    private var keyRoll by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.roll, container, false)
        /////////////////////////////////
        val goal = arguments?.getString("goal")
        var keyAllGoals = arguments?.getInt("keyAllGoals")
        val position = requireArguments().getInt("position")
        keyFragment = requireArguments().getInt("keyFragment")

        /////////////////////////////////
        if (mSkillVM.mapGoal[keyAllGoals]?.value.isNullOrEmpty() || keyAllGoals == null) {
            //Toast.makeText(requireContext(), "Сгенерировано заново", Toast.LENGTH_SHORT).show()
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

        keyRoll = mSkillVM.createId()
        mSkillVM.map[keyFragment]?.get(keyRoll)?.set(position, "goal")

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
        bundle.putInt("keyRoll", keyRoll)
        bundle.putInt("keyFragment", keyFragment)
        bundle.putInt("key1d10", key1d10)
        bundle.putInt("keyCrit", keyCrit)
        fragment.arguments = bundle
        loadFragmentLight(fragment, R.id.m1d10Fr)
        /////////////////////////////////
        val keyListMod = mSkillVM.createId()
        val fragmentM = Modificators()
        val bundleM = Bundle()
        bundleM.putInt("keyRoll", keyRoll)
        bundleM.putInt("keyFragment", keyFragment)
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
            bundleF.putInt("keyRoll", keyRoll)
            bundleF.putInt("keyFragment", keyFragment)
            bundleF.putInt("position", position)
            fragmentF.arguments = bundleF
            loadFragmentLight(fragmentF, R.id.goalFr)
        } else {
            view.findViewById<FragmentContainerView>(R.id.goalFr).visibility = View.GONE
        }

        /////////////////////////////////
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        val m =  mSkillVM.map[keyFragment]?.get(keyRoll)
        if (m != null){
            for((key,value) in m){
                when(value){
                    "goal"->{
                        mSkillVM.mapGoal[key]?.value = null
                    }
                    "boolean"->{
                        mSkillVM.mapBoolean[key]?.value = null
                    }
                    "int"->{
                        mSkillVM.mapInt[key]?.value = null
                    }
                    "listInt"->{
                        mSkillVM.mapListInt[key]?.value = null
                    }
                    "mod"->{
                        mSkillVM.mapMod[key]?.value = null
                    }
                    "string"->{
                        mSkillVM.mapString[key]?.value = null
                    }
                }
            }
        }
        // очищение следов
    }

}