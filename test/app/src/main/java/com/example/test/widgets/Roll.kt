package com.example.test.widgets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.MutableLiveData
import com.example.test.R
import com.example.test.adapters.DropDownAdapterRV
import com.example.test.data_base.Goal
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.FewRollVM
import com.example.test.viewModels.OneRoll
import com.example.test.viewModels.SkillTestVM
import com.example.test.views.DropDownView

private var pos = R.id.radioButton

class Roll : Fragment(), DropDownAdapterRV.TemplateHolder.WhenValueTo,
    DropDownAdapterRV.TemplateHolder.CheckChoose {

    private val mSkillVM: SkillTestVM by activityViewModels()
    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val VM: FewRollVM by activityViewModels()

    private var goal = ""

    private lateinit var m1D10FR: m1D10
    private lateinit var modificatorsFR: Modificators
    private lateinit var goalDD: DropDownView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goal = arguments?.getString("goal", "") ?: ""

        pos = requireArguments().getInt("pos")

        if (VM.chosenRolls[pos] == null) {
            VM.chosenRolls[pos] = OneRoll()
        }

        /////////////////////////////////

        if (VM.allGoals.isNullOrEmpty()) {
            VM.setAllGoals(mCharacterVM)
        }

        /////////////////////////////////

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.roll, container, false)
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
        fragment.arguments = fragment.getM1d10Bundle(pos, pos, 0, key1d10, keyCrit)
        loadFragmentLight(fragment, R.id.m1d10Fr)

        m1D10FR = view.findViewById<FragmentContainerView>(R.id.m1d10Fr).getFragment()

        /////////////////////////////////
        val keyListMod = mSkillVM.createId()
        val fragmentM = Modificators()
        val bundleM = Bundle()
        bundleM.putInt("keyRoll", pos)
        bundleM.putInt("pos", pos)
        bundleM.putInt("keyFragment", 0)
        bundleM.putInt("keyListMod", keyListMod)
        fragmentM.arguments = bundleM
        loadFragmentLight(fragmentM, R.id.modFr)

        modificatorsFR = view.findViewById<FragmentContainerView>(R.id.modFr).getFragment()

        /////////////////////////////////
        goalDD = view.findViewById(R.id.goalDD)
        if (goal == "") {
            goalDD.visibility = View.GONE
        } else {
            val list = mutableListOf<String>()
            VM.allGoals.forEach { g ->
                list.add(g.name)
            }
            if (list.isEmpty()) {
                goalDD.setMainText("Целей нет")
            }
            goalDD.setDDArrayAndListener(list, this, this)
        }

        /////////////////////////////////
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = savedInstanceState?.getInt("pos")
        if (id != null) {
            val m1D10FR =
                view.findViewById<FragmentContainerView>(R.id.m1d10Fr).getFragment<m1D10>()
            val modificatorsFR =
                view.findViewById<FragmentContainerView>(R.id.modFr).getFragment<Modificators>()
            val goalDD = view.findViewById<DropDownView>(R.id.goalDD)

            pos = id
            val oneRoll = VM.chosenRolls[pos]
            if (oneRoll != null) {
                Log.d("eeeee", pos.toString() + "Успешно загружен")
                if (oneRoll.crit != null) {
                    m1D10FR.setCritical(oneRoll.crit!!)
                }
                //m1D10FR.setM1d10(oneRoll.m1d10)
                m1D10FR.setM1d10(6)
                oneRoll.mods?.let { modificatorsFR.setListMods(it) }
                goalDD.setMainText(oneRoll.goal.name)
            }
        }
    }

    override fun whenValueTo(position: Int) {
        val chosenGoal = VM.allGoals[position]
        VM.chosenRolls[pos]?.goal = chosenGoal

        /*Log.d("aaa", pos.toString())
        Log.d("aaa", mSkillVM.mapGoalMap[keyFragment]?.value?.keys.toString())
        Log.d("aaa", mSkillVM.mapGoalMap[keyFragment]?.value?.values.toString())*/
    }

    override fun checkChoose(position: Int): Boolean {
        var r = true
        val chosenGoal = VM.allGoals[position]
        val map = mutableListOf<Goal>()
        for (roll in VM.chosenRolls.values) {
            map.add(roll.goal)
        }
        for (value in map) {
            if (value == chosenGoal) {
                Toast.makeText(
                    requireContext(),
                    "Вы уже выбрали эту цель, пожалуйста выберите другую",
                    Toast.LENGTH_SHORT
                ).show()
                r = false
            }
        }
        return r
    }

    override fun onCheckedFalse() {
        //
    }

    fun getRollBundle(goal: Boolean, id: Int): Bundle {
        val bundle = Bundle()
        if (goal) {
            bundle.putString("goal", "goal")
        } else {
            bundle.putString("goal", "")
        }
        bundle.putInt("pos", id)
        return bundle
    }

    fun getRoll(): OneRoll {
        return VM.chosenRolls[pos]!!
    }

}