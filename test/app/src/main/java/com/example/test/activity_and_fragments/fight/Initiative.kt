package com.example.test.activity_and_fragments.fight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.activity_and_fragments.hosts.FightHost
import com.example.test.data_base.Goal
import com.example.test.databinding.IniciativaBinding
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.FewRolls
import com.example.test.viewModels.SkillTestVM
import com.example.test.views.HeaderView
import com.example.test.widgets.FewRoll
import kotlin.collections.set

class Initiative : Fragment(),
    HeaderView.HeaderBack {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mSkillVM: SkillTestVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.iniciativa, container, false)

        mSkillVM.mapGoalMap[0] = MutableLiveData()
        mSkillVM.mapGoalMap[0]?.value = mutableMapOf()

        val binding = IniciativaBinding.bind(view)
        fun bind() = with(binding) {
            title.text = "Проверка инициативы"

            val fragment = FewRoll()
            fragment.arguments = FewRoll().getFewRollBundle(true, 0)
            childFragmentManager.commit {
                replace(R.id.fr, fragment)
            }

            btnNext.setOnClickListener {
                if (edit.text.toString() != "") {
                    val fewRolls = fr.getFragment<FewRoll>().getFewRoll()
                    val bundle = calculateFewRollToBundle(fewRolls)
                    view.findNavController()
                        .navigate(R.id.action_Initiative_to_InitiativeResult, bundle)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Пожалуйста, введите название боя",
                        Toast.LENGTH_SHORT
                    ).show()
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

    private fun calculateFewRollToBundle(fewRolls: FewRolls): Bundle {
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
            for (s in res.second){
                listMore.add(s)
            }
        }
        while (mapIDtoRes.isNotEmpty()) {
            val i = mapIDtoRes.maxByOrNull { it.value }?.key ?: break
            list.add(i)
            mapIDtoRes.remove(i)
        }
        return bundleOf(Pair("listGoal", list), Pair("listMore", listMore))
    }

}