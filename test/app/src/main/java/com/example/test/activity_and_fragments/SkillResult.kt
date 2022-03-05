package com.example.test.activity_and_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.compose.ui.text.toLowerCase
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.data_base.SpecialGameData
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.SkillTestVM


class SkillResult : Fragment() {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mSkillVM: SkillTestVM by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.skill_result, container, false)
        try {
            val characterId = mCharacterVM.characterId
            val arg = this.arguments
            val title = arg?.getString("title") ?: ""
            val difficult = arg?.getString("difficult") ?: ""
            val mod = arg?.getString("mod") ?: ""
            val res1d10 = arg?.getString("1d10") ?: ""
            val crit = arg?.getString("crit") ?: ""

            // взаимосвясь с навыком и параметром
            val skill = mCharacterVM.characterList.value?.singleOrNull { character ->
                character.id == characterId
            }?.attributes?.singleOrNull { gp ->
                gp.title == "Навыки"
            }?.attributes?.listParamNum?.singleOrNull { pn ->
                pn.name == title
            }?.value ?: 0

            val tvParam = SpecialGameData().mapParameterToSkill[title] ?: ""
            val param = mCharacterVM.characterList.value?.singleOrNull { character ->
                character.id == characterId
            }?.attributes?.singleOrNull { gp ->
                gp.title == "Параметры"
            }?.attributes?.listParamNum?.singleOrNull { pn ->
                pn.name == tvParam
            }?.value ?: 0

            val result = (param + skill + res1d10.toInt() + crit.toInt() - mod.toInt()).toString()

            val back = view.findViewById<ImageButton>(R.id.back)
            val tvForResult = view.findViewById<TextView>(R.id.test_for_value)
            val tvOppositeResult = view.findViewById<TextView>(R.id.test_opposite_value)
            val tvTitle = view.findViewById<TextView>(R.id.title)
            val tvSuccessOrFail = view.findViewById<ImageView>(R.id.imageView)

            tvTitle.text = title
            tvForResult.text = difficult
            tvOppositeResult.text = result

            if (difficult.toInt() >= result.toInt()) {
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

            val list = arrayOf(
                "Сложность = $difficult", "1D10 = $res1d10",
                "Критический = $crit", "Модификатор = $mod",
                "Навык ${title.toLowerCase()} = $skill", "Параметр ${tvParam.toLowerCase()} = $param",
                "Формула для подсчета суммы броска: параметр + навык + 1D10 + критический - модификатор"
            )

            val adapterItems =
                ArrayAdapter(view.context, R.layout.drop_down_list_item_white, list)
            moreRV.adapter = adapterItems

            val relatedSkill = view.findViewById<Button>(R.id.relatedSkill)
            relatedSkill.setOnClickListener {

            }

            val apply = view.findViewById<Button>(R.id.apply)
            apply.setOnClickListener {

            }

            back.setOnClickListener {
                view.findNavController().popBackStack()
            }
        } catch (e: Exception) {
            Toast.makeText(view.context, "$e", Toast.LENGTH_LONG).show()
        }
        return view
    }

}