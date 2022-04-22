package com.example.test.activity_and_fragments.fight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.test.R
import com.example.test.activity_and_fragments.hosts.FightHost
import com.example.test.data_base.SpecialGameData
import com.example.test.data_base.TemplateFightType
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.SkillTestVM


class FightResult : Fragment() {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mSkillVM: SkillTestVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fight_result, container, false)
        // нужно передать сюда все ключи, их значение, формулу, чтобы расчитать бросок
        val characterId = mCharacterVM.characterId
        val attack = mSkillVM.attack!!
        val title = attack.fightType?:""
        val fightType = TemplateFightType().mapFightType[title]


        // Рассчитываем сложность и бросок
        // здесь мы должны получить все ключи и указания где находятся и к чему относятся значения
        /*for ((keyFragment, value) in mSkillVM.map[0]!!){

        }*/

        when(fightType?.difficult){
            "one roll" -> {

            }
            "few roll" -> {

            }
            "arbitrary number" -> {

            }
            "DD by table" -> {

                //loadDD(main:String, "green", goal:String, list:ArrayList<String>, resId:Int)
            }
        }


        val tvForResult = view.findViewById<TextView>(R.id.test_for_value)
        val tvOppositeResult = view.findViewById<TextView>(R.id.test_opposite_value)
        val tvTitle = view.findViewById<TextView>(R.id.title)
        val tvSuccessOrFail = view.findViewById<ImageView>(R.id.imageView)

        tvTitle.text = title
        //tvForResult.text = difficult.toString()
        //tvOppositeResult.text = result

        /*when {
            difficult > result.toInt() -> {
                tvSuccessOrFail.setImageDrawable(resources.getDrawable(R.drawable.draw_fail))
            }
            difficult = result.toInt() -> {

            }
            difficult < result.toInt() -> {
                tvSuccessOrFail.setImageDrawable(resources.getDrawable(R.drawable.draw_success))
            }
        }*/

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

        val list = arrayOf<String>(
            /*"Сложность = $difficult",
            "1D10 = $res1d10",
            "Критический = $crit",
            "Модификатор = $mod",
            "Навык ${title.lowercase(Locale.getDefault())} = $skill",
            "Параметр ${tvParam.lowercase(Locale.getDefault())} = $param",
            str*/
        )

        val adapterItems =
            ArrayAdapter(view.context, R.layout.drop_down_list_item_white, list)
        moreRV.adapter = adapterItems


        val apply = view.findViewById<Button>(R.id.apply)
        apply.setOnClickListener {
            (activity as FightHost).backToMain()
        }

        return view
    }

}

//val difficult = mSkillVM.dif.value
//var mod = 0
//val mods = mSkillVM.modification.value
/*for (m in mods) {
    val res = if (m.style) {
        SpecialGameData().modValue[(m.value - 1)].toInt()
    } else {
        m.value
    }
    mod += res
}*/

/*val res1d10 = mSkillVM.m1d10.value
var crit = 0
if (mSkillVM.boolCritical.value) {
    crit = mSkillVM.critical.value
}*/

// взаимосвясь с навыком и параметром
/*val skill = mSkillVM.skill ?: 0
var luckyOrEdit = 0
if (skill == 0) {
    if (mSkillVM.usingLuckyPoint == 0 || mSkillVM.usingLuckyPoint == null) {
        mSkillVM.luckyOrErudit = false
    }
    luckyOrEdit = if (mSkillVM.luckyOrErudit == true) {
        mSkillVM.usingLuckyPoint ?: 0
    } else {
        mSkillVM.erudit ?: 0
    }
}*/

/*val tvParam = SpecialGameData().mapParameterToSkill[title] ?: ""
val param = mCharacterVM.characterList.value?.singleOrNull { character ->
    character.id == characterId
}?.attributes?.singleOrNull { gp ->
    gp.title == "Параметры"
}?.attributes?.listParamNum?.singleOrNull { pn ->
    pn.name == tvParam
}?.value ?: 0*/


/*var str = ""
var result = ""


if (skill == 0) {
    if (mSkillVM.luckyOrErudit == true) {
        str =
            "Формула для подсчета суммы броска: параметр + удача + 1D10 + критический - модификатор"
    } else {
        str =
            "Формула для подсчета суммы броска: параметр + эрудиция + 1D10 + критический - модификатор"
    }
    result = (param + luckyOrEdit + res1d10 + crit - mod).toString()
} else {
    str =
        "Формула для подсчета суммы броска: параметр + навык + 1D10 + критический - модификатор"
    result = (param + skill + res1d10 + crit - mod).toString()
}*/
