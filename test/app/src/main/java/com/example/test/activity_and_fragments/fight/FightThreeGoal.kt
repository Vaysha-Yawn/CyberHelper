package com.example.test.activity_and_fragments.fight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.example.test.R
import com.example.test.data_base.EffectWeapon
import com.example.test.data_base.FightType
import com.example.test.data_base.SpecialGameData
import com.example.test.databinding.FightThreeGoalBinding
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.SkillTestVM
import com.example.test.widgets.*

class FightThreeGoal : Fragment() {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mSkillVM: SkillTestVM by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fight_three_goal, container, false)
        val fightType = mSkillVM.attack?.fightType?: FightType()
        val listFragments = listOf<String>()

        fun loadDD(main:String, them:String, goal:String, list:ArrayList<String>, id:Int){
            val fragment = DropDownList()
            val bundle = Bundle()
            bundle.putString("main", main)
            bundle.putString("them", them)
            bundle.putString("goal", goal)
            bundle.putStringArrayList("list", list)
            fragment.arguments = bundle
            childFragmentManager.commit {
                replace(id, fragment)
                addToBackStack(null)
            }
        }

        fun loadFragmentLight(fragment:Fragment, id:Int){
            childFragmentManager.commit {
                replace(id, fragment)
                addToBackStack(null)
            }
        }

        fun loadPM(value:Int, minValue:Int, maxValue:Int?, resId:Int){
            val bundleD = Bundle()
            bundleD.putInt("value", value)
            bundleD.putInt("minValue", minValue)
            if (maxValue!=null){
                bundleD.putInt("maxValue", maxValue)
            }
            bundleD.putString("them", "green")
            bundleD.putString("goal", "")
            val fragmentD = PlusAndMinus()
            fragmentD.arguments = bundleD
            childFragmentManager.commit {
                replace(resId, fragmentD)
                addToBackStack(null)
            }
        }

        fun setVisibility(view:View, visible:Boolean){
            if (visible){
                view.visibility = View.VISIBLE
            }else{
                view.visibility = View.GONE
            }
        }

        val binding = FightThreeGoalBinding.bind(view)
        fun bind() = with(binding) {

            loadFragmentLight(Header(), R.id.header)

//////////////////
            when (fightType.difficult) {
                "one roll" -> {
                    val fragment = Roll()
                    val bundle = Bundle()
                    bundle.putString("goal", "goal")
                    fragment.arguments = bundle
                    loadFragmentLight(fragment, R.id.mainFr)
                }
                "few roll" -> {
                    // VP2
                    val fragment = Roll()
                    val bundle = Bundle()
                    bundle.putString("goal", "goal")
                    fragment.arguments = bundle
                    loadFragmentLight(fragment, R.id.mainFr)
                }
                "arbitrary number" -> {
                    loadPM(0, 0, null, R.id.mainFr)
                }
                "DD by list" -> {

                    loadDD(main:String, "green", goal:String, list:ArrayList<String>, resId:Int)
                }
                "DD by param" -> {

                    loadDD(main:String, "green", goal:String, list:ArrayList<String>, resId:Int)
                }
            }
///////////////////////////// DD distance
            if (listFragments.contains("DD distance")) {
                // TODO: вообще у оружия должны быть подтипы, которые мы выбираем из списка и эти подтипы являются ключами здесь

                val listOne = ArrayList<String>()
                SpecialGameData().mapDifficultByDistance[mSkillVM.attack?.name
                    ?: ""]?.keys?.forEach {
                    listOne.add(it)
                }
                if (listOne.isEmpty()){
                    loadDD("Оружие не опознано", "blue", "", listOne, R.id.frDDChoseDistanceOne)
                }else{
                    loadDD("Выберите расстояние", "blue", "", listOne, R.id.frDDChoseDistanceOne)
                }


                if (listFragments.contains("howManyShoot")) {

                    setVisibility(frDDChoseDistanceThree, false)

                    val listThree = ArrayList<String>()
                    SpecialGameData().mapDifficultByDistanceThreeShotBurst[mSkillVM.attack?.name
                        ?: ""]?.keys?.forEach {
                        listThree.add(it)
                    }

                    if (listThree.isEmpty()){
                        loadDD("Оружие не опознано", "blue", "", listThree, R.id.frDDChoseDistanceThree)
                    }else{
                        loadDD("Выберите расстояние", "blue", "", listThree, R.id.frDDChoseDistanceThree)
                    }

                    howManyShoot.setOnCheckedChangeListener { group, checkedId ->
                        when (checkedId) {
                            R.id.oneShoot -> {
                                setVisibility(frDDChoseDistanceOne, true)
                                setVisibility(frDDChoseDistanceThree, false)
                            }
                            R.id.threeShoot -> {
                                setVisibility(frDDChoseDistanceOne, false)
                                setVisibility(frDDChoseDistanceThree, true)
                            }
                        }
                    }
                }
            } else {
                setVisibility(frDDChoseDistanceOne, false)
                setVisibility(frDDChoseDistanceThree, false)
            }
//////////////////////// bodyOrHead

            if (listFragments.contains("bodyOrHead")) {
                setVisibility(bodyOrHead, true)
                setVisibility(textBodyOrHead, true)
            } else {
                setVisibility(bodyOrHead, false)
                setVisibility(textBodyOrHead, false)
            }
//////////////////////// difficultByGoalOrDistance
            if (listFragments.contains("difficultByGoalOrDistance")) {
                setVisibility(frDDChoseDistanceOne, false)
                setVisibility(frDDChoseDistanceThree, false)
                setVisibility(modFr, true)
                // TODO: надо сделать это проще, а то черт ногу сломит в этой логике не логике
                //  добавить функций и использовать их и сделать созависмости проще
                difficultByGoalOrDistance.setOnCheckedChangeListener { group, checkedId ->
                    when (checkedId) {
                        R.id.difficultByGoal -> {
                            setVisibility(modFr, true)
                            setVisibility(frDDChoseDistanceOne, false)
                            setVisibility(frDDChoseDistanceThree, false)
                        }
                        R.id.difficultByDistance -> {
                            setVisibility(modFr, false)
                            setVisibility(frDDChoseDistanceOne, true)
                            setVisibility(frDDChoseDistanceThree, false)
                        }
                    }
                }

            } else {
                setVisibility(difficultByGoalOrDistance, false)
                setVisibility(textDifficultByGoalOrDistance, false)
            }

///////////////////// typeShoot
            if (listFragments.contains("typeShoot")) {
                setVisibility(typeShoot, true)
                setVisibility(textTypeShoot, true)

                typeShoot.setOnCheckedChangeListener { group, checkedId ->
                    when (checkedId) {
                        R.id.justShoot -> {
                        }
                        R.id.hardShoot -> {
                            //TODO: проработать подробнее
                            setVisibility(frDDChoseDistanceOne, false)
                            setVisibility(frDDChoseDistanceThree, false)
                            setVisibility(modFr, true)
                        }
                    }
                }

            } else {
                setVisibility(typeShoot, false)
                setVisibility(textTypeShoot, false)
            }

///////////////////// howManyShoot
            if (listFragments.contains("howManyShoot")) {
                setVisibility(howManyShoot, true)
                setVisibility(textHowManyShoot, true)
            } else {
                setVisibility(howManyShoot, false)
                setVisibility(textHowManyShoot, false)
            }

        }
        bind()

        return view
    }

}
//TODO: вообще эта идея с ручным регулированием того, что показывать, а что - нет, это плохая затея
// так будут проблемы с наложением и приоритетами
//  стоит подробнее ориентироваться на тип атаки и подтип оружия