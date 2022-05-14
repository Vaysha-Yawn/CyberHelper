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
import com.example.test.viewModels.OneRoll
import com.example.test.viewModels.SkillTestVM
import com.example.test.views.DropDownView
import kotlin.properties.Delegates

class Roll : Fragment(), DropDownAdapterRV.TemplateHolder.WhenValueTo,
    DropDownAdapterRV.TemplateHolder.CheckChoose {

    private val mSkillVM: SkillTestVM by activityViewModels()
    private val mCharacterVM: CharacterDAO by activityViewModels()
    private var keyFragment by Delegates.notNull<Int>()
    private var keyAllGoals = 0
    private var pos = 0
    private var goal = ""

    private lateinit var m1D10FR: m1D10
    private lateinit var modificatorsFR: Modificators
    private lateinit var goalDD: DropDownView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goal = arguments?.getString("goal", "")?:""
        keyAllGoals = arguments?.getInt("keyAllGoals") ?: 0
        pos = requireArguments().getInt("pos")
        keyFragment = requireArguments().getInt("keyFragment")

        mSkillVM.mapRoll[keyFragment]?.set(pos, OneRoll())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.roll, container, false)
        /////////////////////////////////

        if (mSkillVM.mapGoal[keyAllGoals]?.value.isNullOrEmpty() || keyAllGoals == 0) {
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
        bundle.putInt("keyRoll", pos)
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
        bundleM.putInt("keyRoll", pos)
        bundleM.putInt("keyFragment", keyFragment)
        bundleM.putInt("keyListMod", keyListMod)
        fragmentM.arguments = bundleM
        loadFragmentLight(fragmentM, R.id.modFr)

        modificatorsFR = view.findViewById<FragmentContainerView>(R.id.modFr).getFragment()

        /////////////////////////////////
        goalDD = view.findViewById(R.id.goalDD)
        if ( goal == "") {
            goalDD.visibility = View.GONE
        } else {
            val list = mutableListOf<String>()
            mSkillVM.mapGoal[keyAllGoals]?.value?.forEach { g ->
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

    override fun whenValueTo(position: Int) {
        val chosenGoal = mSkillVM.mapGoal[keyAllGoals]?.value?.get(position)!!
        mSkillVM.mapGoalMap[keyFragment]?.value?.set(pos, chosenGoal)

        Log.d("DD", mSkillVM.mapGoalMap[keyFragment]?.value?.values.toString())

        mSkillVM.mapRoll[keyFragment]?.get(pos)?.goal = chosenGoal
    }

    override fun checkChoose(position: Int): Boolean {
        var r = true
        val chosenGoal = mSkillVM.mapGoal[keyAllGoals]?.value?.get(position)
        val map = mSkillVM.mapGoalMap[keyFragment]?.value
        if (chosenGoal != null) {
            if (map != null) {
                for ((key, value) in map) {
                    if (value == chosenGoal) {
                        Toast.makeText(
                            requireContext(),
                            "Вы уже выбрали эту цель, пожалуйста выберите другую",
                            Toast.LENGTH_SHORT
                        ).show()
                        r = false
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Список выбранных целей не найден",
                    Toast.LENGTH_SHORT
                ).show()
                r = false
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Выбранная цель не найдена",
                Toast.LENGTH_SHORT
            ).show()
            r = false
        }

        return r
    }

    override fun onCheckedFalse() {
        //
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}