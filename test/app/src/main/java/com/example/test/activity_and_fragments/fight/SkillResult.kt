package com.example.test.activity_and_fragments.fight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.data_base.SpecialGameData
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.SkillTestVM
import java.util.*


class SkillResult : Fragment() {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mSkillVM: SkillTestVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.skill_result, container, false)

        val characterId = mCharacterVM.characterId
        val title = mSkillVM.attack?.fightType ?: ""
        val skill = ""
        val tvParam = SpecialGameData().mapParameterToSkill[skill] ?: ""

        val result = mSkillVM.calculateSkillTestOneRoll(
            nameRoll = "броска",
            roll:OneRoll,
            parameters = mapOf<String, String>("Параметры" to tvParam),
            skill = Pair("Навыки", skill),
            luckyOrErudition:Boolean,
            usingLuckyPoint:Int?,
        erudition = Pair("Навыки", "Эрудиция"), listCharacter = mCharacterVM.characterList.value!!)
        // взаимосвясь с навыком и параметром


        var str = ""

        val tvForResult = view.findViewById<TextView>(R.id.test_for_value)
        val tvOppositeResult = view.findViewById<TextView>(R.id.test_opposite_value)
        val tvTitle = view.findViewById<TextView>(R.id.title)
        val tvSuccessOrFail = view.findViewById<ImageView>(R.id.imageView)

        tvTitle.text = title
        tvForResult.text = difficult.toString()
        tvOppositeResult.text = result.toString()

        if (difficult >= result.toInt()) {
            tvSuccessOrFail.setImageDrawable(resources.getDrawable(R.drawable.draw_fail))
        } else {
            tvSuccessOrFail.setImageDrawable(resources.getDrawable(R.drawable.draw_success))
        }

        val more = view.findViewById<TextView>(R.id.more)
        val moreRV = view.findViewById<ListView>(R.id.moreRV)
        moreRV.visibility = View.GONE

        more.setOnClickListener {
            if (moreRV.visibility == View.VISIBLE) {
                moreRV.visibility = View.GONE
                more.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_next,
                    0,
                    0,
                    0
                )
            } else {
                moreRV.visibility = View.VISIBLE
                more.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_drop_down,
                    0,
                    0,
                    0
                )
            }
        }

        val adapterItems =
            ArrayAdapter(
                view.context,
                R.layout.drop_down_list_item_white,
                mSkillVM.listMore.toTypedArray()
            )
        moreRV.adapter = adapterItems


        val apply = view.findViewById<Button>(R.id.apply)
        apply.setOnClickListener {
            view.findNavController().navigate(R.id.action_skillResult_to_home2)
        }

        return view
    }

}
