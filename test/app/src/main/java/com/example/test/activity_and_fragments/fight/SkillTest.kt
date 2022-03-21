package com.example.test.activity_and_fragments.fight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.data_base.SpecialGameData
import com.example.test.databinding.SkillTestBinding
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.SkillTestVM
import com.example.test.widgets.DropDownList
import com.example.test.widgets.Modificators
import com.example.test.widgets.PlusAndMinus
import com.example.test.widgets.m1D10


class SkillTest : Fragment() {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mSkillVM: SkillTestVM by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.skill_test, container, false)

        val characterId = mCharacterVM.characterId
        val arg = this.arguments
        val txtitle = arg?.getString("title") ?: ""

        mSkillVM.title = txtitle

        val tvEdit = view.findViewById<TextView>(R.id.edit)

        val difficultValue = SpecialGameData().difficultValue

        // подключаем фрагмент для выбора сложности
        val arr = SpecialGameData().difficultName
        val bundle = Bundle()
        bundle.putString("main", "Выберите сложность")
        bundle.putString("them", "yellow")
        bundle.putStringArrayList("list", arr)
        bundle.putString("goal", "difficult")
        val fragment = DropDownList()
        fragment.arguments = bundle
        childFragmentManager.commit {
            replace(R.id.dropDownDifficult, fragment)
            addToBackStack(null)
        }

        val fragmentMod = Modificators()
        childFragmentManager.commit {
            replace(R.id.mod, fragmentMod)
            addToBackStack(null)
        }

        val fragmentM1d10 = m1D10()
        childFragmentManager.commit {
            replace(R.id.m1d10, fragmentM1d10)
            addToBackStack(null)
        }

        val binding = SkillTestBinding.bind(view)
        fun bind() = with(binding) {
            title.text = txtitle

            mSkillVM.difBoolean.observe(viewLifecycleOwner) {
                tvEdit.text = difficultValue[mSkillVM.dif.value!!]
            }

            back.setOnClickListener {
                view.findNavController().popBackStack()
            }

            // если нет навыка-------------------------------------------------------------------------

            val skill = mCharacterVM.characterList.value?.singleOrNull { character ->
                character.id == characterId
            }?.attributes?.singleOrNull { gp ->
                gp.title == "Навыки"
            }?.attributes?.listParamNum?.singleOrNull { pn ->
                pn.name == txtitle
            }?.value ?: 0

            mSkillVM.skill = skill

            if (skill==0){
                ifNoSkill.visibility = View.VISIBLE
                luckyOrErudit.setOnCheckedChangeListener { group, checkedId ->
                    when(checkedId){
                        R.id.byLucky->{
                            mSkillVM.luckyOrErudit = true

                            byLuckyLinLay.visibility = View.VISIBLE
                            luckLeft.visibility = View.VISIBLE

                        }
                        R.id.byErudition->{
                            mSkillVM.luckyOrErudit = false

                            byLuckyLinLay.visibility = View.GONE
                            luckLeft.visibility = View.GONE
                        }
                    }
                }
                val luck = mCharacterVM.characterList.value?.singleOrNull { character ->
                    character.id == characterId
                }?.attributes?.singleOrNull { gp ->
                    gp.title == "Параметры"
                }?.attributes?.listParamNum?.singleOrNull { pn ->
                    pn.name == "Удача"
                }?.value ?: 0

                luckLeft.text = "Осталось $luck очков удачи"
                // подключаем плюс минус
                val bundleD = Bundle()
                bundleD.putInt("value", 0)
                bundleD.putInt("minValue", 0)
                bundleD.putInt("maxValue", luck)
                bundleD.putString("them", "orange_small")
                bundleD.putString("goal", "luck")
                val fragmentD = PlusAndMinus()
                fragmentD.arguments = bundleD
                childFragmentManager.commit {
                    replace(R.id.luckyPMfr, fragmentD)
                    addToBackStack(null)
                }

                // на всякий ищем эрудицию
                val erudit = mCharacterVM.characterList.value?.singleOrNull { character ->
                    character.id == characterId
                }?.attributes?.singleOrNull { gp ->
                    gp.title == "Навыки"
                }?.attributes?.listParamNum?.singleOrNull { pn ->
                    pn.name == "Образование"
                }?.value ?: 0

                mSkillVM.erudit = erudit
            }else{
                ifNoSkill.visibility = View.GONE
            }

            //-------------------------------------------------------------------------------------------------------
        }

        bind()

        val next = view.findViewById<TextView>(R.id.btn_next)
        next.setOnClickListener {
            // здесь какие-то проверки
            // подсчитываем во VM и передаем значение
            var res = 1
            val difficult = tvEdit.text.toString().toIntOrNull()
            if (difficult != null) {
                mSkillVM.dif.value = difficult
            } else {
                res = 0
            }
            if (res == 1) {
                view.findNavController()
                    .navigate(R.id.action_pres_skillTest_to_skillResult)
            }
        }

        return view
    }

}