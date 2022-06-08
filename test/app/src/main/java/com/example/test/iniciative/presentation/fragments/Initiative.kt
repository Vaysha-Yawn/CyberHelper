package com.example.test.iniciative.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.iniciative.presentation.activity.FightHost
import com.example.test.data_base.Goal
import com.example.test.databinding.IniciativaBinding
import com.example.test.viewModels.CharacterDAO
import com.example.test.iniciative.presentation.view_model.FewRollVM
import com.example.test.viewModels.FewRolls
import com.example.test.viewModels.SkillTestVM
import com.example.test.components.views.HeaderView
import com.example.test.components.widgets.FewRoll
import kotlin.collections.set

class Initiative : Fragment(),
    HeaderView.HeaderBack {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mSkillVM: SkillTestVM by activityViewModels()
    private val VM: FewRollVM by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (VM.allGoals.isEmpty()) {
            VM.setAllGoals(mCharacterVM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.iniciativa, container, false)

        val binding = IniciativaBinding.bind(view)
        fun bind() = with(binding) {
            title.text = "Проверка инициативы"

            // может есть менее радикальный метод,чем каждый раз создавать новый экемпляр?
            val fragment = FewRoll()
            fragment.arguments = FewRoll().getFewRollBundle(true)
            childFragmentManager.commit {
                replace(R.id.fr, fragment)
            }

            btnNext.setOnClickListener {
                val fewRolls = fr.getFragment<FewRoll>().getFewRoll()
                var res = 1
                if (edit.text.toString() == ""){
                    res = 0
                    Toast.makeText(
                        requireContext(),
                        "Введите название боя",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                for (roll in fewRolls.rolls){
                    if (roll.goal.name == ""){
                        res = 0
                        Toast.makeText(
                            requireContext(),
                            "Выберите цель",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                if (fewRolls.rolls.size<2){
                    res = 0
                    Toast.makeText(
                        requireContext(),
                        "Не менее 2х целей",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                if (res==1) {
                    val bundle = calculateFewRollToBundle(fewRolls, edit.text.toString())
                    view.findNavController()
                        .navigate(R.id.action_Initiative_to_InitiativeResult, bundle)
                }
            }
            header.setBack(true, this@Initiative, requireActivity(), viewLifecycleOwner)
        }
        bind()

        return view
    }

    override fun back() {
        (activity as FightHost).backToHome()
    }

    private fun calculateFewRollToBundle(fewRolls: FewRolls, nameFight: String): Bundle {
        val list = mutableListOf<Goal>()
        val mapIDtoRes = mutableMapOf<Goal, Int>()
        val listMore = mutableListOf<String>()
        for (c in fewRolls.rolls) {
            val goal = c.goal
            val res = mSkillVM.calculateOneRoll(
                "",
                c,
                mapOf("Параметры" to "Реакция"),
                mCharacterVM.characterList.value!!
            )
            mapIDtoRes[goal] = res.first
            for (s in res.second) {
                listMore.add(s)
            }
        }
        while (mapIDtoRes.isNotEmpty()) {
            val i = mapIDtoRes.maxByOrNull { it.value }?.key ?: break
            list.add(i)
            mapIDtoRes.remove(i)
        }
        return bundleOf(
            Pair("listGoal", list),
            Pair("listMore", listMore),
            Pair("nameFight", nameFight)
        )
    }

}