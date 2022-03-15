package com.example.test.activity_and_fragments.fight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.example.test.R
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
        val fightType = mSkillVM.attack?.fightType
        val listFragments = SpecialGameData().mapFightTypeToFragment[fightType] ?: emptyList()

        val binding = FightThreeGoalBinding.bind(view)
        fun bind() = with(binding) {

            if (listFragments.contains("Modificators")) {
                val fragmentMod = Modificators()
                childFragmentManager.commit {
                    replace(R.id.modFr, fragmentMod)
                    addToBackStack(null)
                }
            } else {
                modFr.visibility = View.GONE
            }

            if (listFragments.contains("m1D10")) {
                val fragmentM1d10 = m1D10()
                childFragmentManager.commit {
                    replace(R.id.fr1d10, fragmentM1d10)
                    addToBackStack(null)
                }
            } else {
                fr1d10.visibility = View.GONE
            }

            if (listFragments.contains("Goals")) {
                val fragmentGoal = Goals()
                childFragmentManager.commit {
                    replace(R.id.goalFr, fragmentGoal)
                    addToBackStack(null)
                }
            } else {
                goalFr.visibility = View.GONE
            }
/////////////////////

            if (listFragments.contains("DD goal one")) {

                val list = ArrayList<String>()
                val goalsList = mutableListOf<Goal>()
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
                            list.add(name)
                            goalsList.add(goal)
                        }
                    }
                }
                mSkillVM.allGoals.value = goalsList

                val fragmentGoal = DropDownList()
                val bundle = Bundle()
                bundle.putString("main", "Выберите цель")
                bundle.putString("them", "yellow")
                bundle.putString("goal", "goal")
                bundle.putStringArrayList("list", list)
                fragmentGoal.arguments = bundle
                childFragmentManager.commit {
                    replace(R.id.frDDoneGoal, fragmentGoal)
                    addToBackStack(null)
                }
            } else {
                frDDoneGoal.visibility = View.GONE
            }
/////////////////////////////
            if (listFragments.contains("DD distance")) {
                // TODO: вообще у оружия должны быть подтипы, которые мы выбираем из списка и эти подтипы являются ключами здесь

                val listOne = ArrayList<String>()
                SpecialGameData().mapDifficultByDistance[mSkillVM.attack?.name
                    ?: ""]?.keys?.forEach {
                    listOne.add(it)
                }

                val fragmentOne = DropDownList()
                val bundleOne = Bundle()
                bundleOne.putString("main", "Выберите расстояние")
                bundleOne.putString("them", "blue")
                bundleOne.putStringArrayList("list", listOne)
                fragmentOne.arguments = bundleOne
                childFragmentManager.commit {
                    replace(R.id.frDDChoseDistanceOne, fragmentOne)
                    addToBackStack(null)
                }

                if (listFragments.contains("howManyShoot")) {
                    val listThree = ArrayList<String>()
                    SpecialGameData().mapDifficultByDistanceThreeShotBurst[mSkillVM.attack?.name
                        ?: ""]?.keys?.forEach {
                        listThree.add(it)
                    }

                    val fragmentThree = DropDownList()
                    val bundleThree = Bundle()
                    bundleThree.putString("main", "Выберите расстояние")
                    bundleThree.putString("them", "blue")
                    bundleThree.putStringArrayList("list", listThree)
                    fragmentThree.arguments = bundleThree
                    childFragmentManager.commit {
                        replace(R.id.frDDChoseDistanceThree, fragmentThree)
                        addToBackStack(null)
                    }
                    howManyShoot.setOnCheckedChangeListener { group, checkedId ->
                        when (checkedId) {
                            R.id.oneShoot -> {
                                frDDChoseDistanceOne.visibility = View.VISIBLE
                                frDDChoseDistanceThree.visibility = View.GONE
                            }
                            R.id.threeShoot -> {
                                frDDChoseDistanceOne.visibility = View.GONE
                                frDDChoseDistanceThree.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            } else {
                frDDChoseDistanceThree.visibility = View.GONE
                frDDChoseDistanceOne.visibility = View.GONE
            }
////////////////////////
            val fragmentHeader = Header()
            childFragmentManager.commit {
                replace(R.id.header, fragmentHeader)
                addToBackStack(null)
            }

            if (listFragments.contains("bodyOrHead")) {
                bodyOrHead.visibility = View.VISIBLE
                textBodyOrHead.visibility = View.VISIBLE
            } else {
                bodyOrHead.visibility = View.GONE
                textBodyOrHead.visibility = View.GONE
            }

            if (listFragments.contains("difficultByGoalOrDistance")) {
                difficultByGoalOrDistance.visibility = View.VISIBLE
                textDifficultByGoalOrDistance.visibility = View.VISIBLE
            } else {
                difficultByGoalOrDistance.visibility = View.GONE
                textDifficultByGoalOrDistance.visibility = View.GONE
            }

            if (listFragments.contains("typeShoot")) {
                typeShoot.visibility = View.VISIBLE
                textTypeShoot.visibility = View.VISIBLE
            } else {
                typeShoot.visibility = View.GONE
                textTypeShoot.visibility = View.GONE
            }

            if (listFragments.contains("howManyShoot")) {
                howManyShoot.visibility = View.VISIBLE
                textHowManyShoot.visibility = View.VISIBLE
            } else {
                howManyShoot.visibility = View.GONE
                textHowManyShoot.visibility = View.GONE
            }

        }
        bind()

        return view
    }


}