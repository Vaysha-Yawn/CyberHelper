package com.example.test.components.few_roll.roll

import android.os.Bundle
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
import com.example.test.components.views.drop_down.DropDownAdapterRV
import com.example.test.data_base.realm.other_realm_object.Goal
import com.example.test.data_base.realm.character.CharacterDAO
import com.example.test.components.few_roll.FewRollVM
import com.example.test.viewModels.OneRoll
import com.example.test.viewModels.SkillTestVM
import com.example.test.components.views.drop_down.DropDownView
import com.example.test.components.few_roll.roll.modificators.Modificators

private var pos = R.id.radioButton

class Roll : Fragment(), DropDownAdapterRV.TemplateHolder.WhenValueTo,
    DropDownAdapterRV.TemplateHolder.CheckChoose {

    private val mSkillVM: SkillTestVM by activityViewModels()
    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val VM: FewRollVM by activityViewModels()

    private var goal = ""

    private lateinit var m1D10FR: m1D10
    private lateinit var modificatorsFR: Modificators
    private lateinit var goalDDView: DropDownView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goal = arguments?.getString("goal", "") ?: ""

        pos = requireArguments().getInt("pos")

        if (VM.chosenRolls[pos] == null) {
            VM.chosenRolls[pos] = OneRoll()
        }

        if (VM.allGoals.isEmpty()) {
            VM.setAllGoals(mCharacterVM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.roll, container, false)

        fun loadFragmentLight(fragment: Fragment, id: Int) {
            childFragmentManager.commit {
                replace(id, fragment)
                addToBackStack(null)
            }
        }

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
        goalDDView = view.findViewById(R.id.goalDD)
        if (goal == "") {
            goalDDView.visibility = View.GONE
        } else {
            val list = mutableListOf<String>()
            VM.allGoals.forEach { g ->
                list.add(g.name)
            }
            if (list.isEmpty()) {
                goalDDView.setMainText("Целей нет")
            }
            goalDDView.setDDArrayAndListener(list, this, this)
        }
        VM.chosenRolls[pos]?.goal?.name?.let { goalDDView.setMainText(it) }
        /*val binding = RollBinding.bind(view)
        with(binding){
            val oneRoll = VM.chosenRolls[pos]
            if (oneRoll != null) {
                Log.d("eeeee", pos.toString() + "Успешно загружен")
                if (oneRoll.crit != null) {
                    m1D10FR.setCritical(oneRoll.crit!!)
                }
                m1d10Fr.getFragment<m1D10>().setM1d10(oneRoll.m1d10)
                oneRoll.mods?.let {
                    modFr.getFragment<Modificators>().setListMods(it) }

            }
        }*/

        /////////////////////////////////

        return view
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