package com.example.test.activity_and_fragments.fight

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import com.example.test.R
import com.example.test.data_base.EffectWeapon
import com.example.test.databinding.FightSecondBinding
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.SkillTestVM
import com.example.test.widgets.Modificators
import com.example.test.widgets.m1D10


class FightSecond : Fragment() {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mSkillVM: SkillTestVM by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fight_second, container, false)
        val attack = mSkillVM.attack?: EffectWeapon()

        val fragmentMod = Modificators()
        childFragmentManager.commit {
            replace(R.id.modFr, fragmentMod)
            addToBackStack(null)
        }

        val fragmentM1d10 = m1D10()
        childFragmentManager.commit {
            replace(R.id.fr1d10, fragmentM1d10)
            addToBackStack(null)
        }

        val binding = FightSecondBinding.bind(view)
        fun bind() = with(binding) {
            title.text = attack.fightType


        }

        bind()

        return view
    }


}