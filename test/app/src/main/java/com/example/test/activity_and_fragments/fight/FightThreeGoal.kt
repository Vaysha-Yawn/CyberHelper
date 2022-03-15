package com.example.test.activity_and_fragments.fight

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.example.test.R
import com.example.test.data_base.SpecialGameData
import com.example.test.databinding.FightSecondBinding
import com.example.test.databinding.FightThreeGoalBinding
import com.example.test.viewModels.SkillTestVM
import com.example.test.widgets.Goals
import com.example.test.widgets.Header
import com.example.test.widgets.Modificators
import com.example.test.widgets.m1D10

class FightThreeGoal : Fragment() {

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

        if (listFragments.contains("Modificators")) {
            val fragmentMod = Modificators()
            childFragmentManager.commit {
                replace(R.id.modFr, fragmentMod)
                addToBackStack(null)
            }
        }

        if (listFragments.contains("m1D10")) {
            val fragmentM1d10 = m1D10()
            childFragmentManager.commit {
                replace(R.id.fr1d10, fragmentM1d10)
                addToBackStack(null)
            }
        }

        if (listFragments.contains("Goals")) {
            val fragmentGoal = Goals()
            childFragmentManager.commit {
                replace(R.id.goalFr, fragmentGoal)
                addToBackStack(null)
            }
        }

        val fragmentHeader = Header()
        childFragmentManager.commit {
            replace(R.id.header, fragmentHeader)
            addToBackStack(null)
        }
        // TODO: добавить "radio header or body", "radio difficult by goal on by distance", "radio type shoot", "radio number shoot"
        //  "DD goal one", "DD distance"

        val binding = FightThreeGoalBinding.bind(view)
        fun bind() = with(binding) {

        }
        bind()

        return view
    }


}