package com.example.test.activity_and_fragments.fight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.databinding.IniciativaResultBinding
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.SkillTestVM


class IniciativaResult : Fragment() {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mSkillVM: SkillTestVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.iniciativa_result, container, false)

        val characterId = mCharacterVM.characterId


        val binding = IniciativaResultBinding.bind(view)
        fun bind() = with(binding) {


            apply.setOnClickListener {
                view.findNavController().navigate(R.id.action_skillResult_to_home2)
            }
        }
        bind()

        return view
    }

}
