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
import com.example.test.data_base.Goal
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.SkillTestVM
import kotlin.properties.Delegates

class Roll : Fragment() {

    private val mSkillVM: SkillTestVM by activityViewModels()
    private val mCharacterVM: CharacterDAO by activityViewModels()
    private var keyFragment by Delegates.notNull<Int>()
    private var keyRoll by Delegates.notNull<Int>()

    private lateinit var m1D10FR:m1D10
    private lateinit var modificatorsFR:Modificators

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
        //mSkillVM.map[keyFragment]?.get(keyRoll)?.set(position, "goal")

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

        m1D10FR = view.findViewById<FragmentContainerView>(R.id.m1d10Fr).getFragment()

        /////////////////////////////////
        val keyListMod = mSkillVM.createId()
        val fragmentM = Modificators()
        val bundleM = Bundle()
        bundleM.putInt("keyRoll", keyRoll)
        bundleM.putInt("keyFragment", keyFragment)
        bundleM.putInt("keyListMod", keyListMod)
        fragmentM.arguments = bundleM
        loadFragmentLight(fragmentM, R.id.modFr)

        modificatorsFR = view.findViewById<FragmentContainerView>(R.id.modFr).getFragment()

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

   /*fun getRoll():OneRoll{
       val g1d10 = m1D10FR.get1d10()
       val critical  = m1D10FR.getCritical()
       val mods = modificatorsFR.getListMods()
       return OneRoll(
            goal: Goal,
           mods,
       g1d10,
       critical)
   }*/

}