package com.example.test.activity_and_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.data_base.SpecialGameData
import com.example.test.databinding.FightAttackBinding
import com.example.test.databinding.SkillTest2Binding
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.SkillTestVM
import com.example.test.widgets.DropDownList
import com.example.test.widgets.PlusAndMinus


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
        val view = inflater.inflate(R.layout.skill_test2, container, false)

        val characterId = mCharacterVM.characterId
        val arg = this.arguments
        val txtitle = arg?.getString("title") ?: ""

        val binding = SkillTest2Binding.bind(view)
        fun bind() = with(binding) {
            title.text = txtitle
        }
        bind()

        try {
            /*val back = view.findViewById<ImageButton>(R.id.back)
            val tvEdit = view.findViewById<TextView>(R.id.edit)
            val tvEditMod = view.findViewById<TextView>(R.id.editModifier)

            // подключаем фрагмент для выбора сложности
            val arr = SpecialGameData().difficultName
            val bundle = Bundle()
            bundle.putString("main", "Выберите сложность")
            bundle.putString("them", "yellow")
            bundle.putStringArrayList("list", arr)
            bundle.putString("goal", "toEdit")
            bundle.putString("valToVM", "difficult")
            val fragment = DropDownList()
            fragment.arguments = bundle
            childFragmentManager.commit {
                replace(R.id.dropDownDifficult, fragment)
                addToBackStack(null)
            }

            // подключаем фрагмент для выбора модификатора
            val mods = SpecialGameData().modName
            val bundleM = Bundle()
            bundleM.putString("main", "Выберите модификатор")
            bundleM.putString("them", "blue")
            bundleM.putString("goal", "toEdit")
            bundleM.putString("valToVM", "modification")
            bundleM.putStringArrayList("list", mods)
            val fragmentM = DropDownList()
            fragmentM.arguments = bundleM
            childFragmentManager.commit {
                replace(R.id.dropDownModifier, fragmentM)
                addToBackStack(null)
            }

            // подключаем фрагмент плюс и минус 1 d 10
            val bundleD = Bundle()
            bundleD.putInt("value", 1)
            bundleD.putInt("minValue", 1)
            bundleD.putInt("maxValue", 10)
            bundleD.putString("them", "yellow")
            bundleD.putString("goal", "skillTest")
            val fragmentD = PlusAndMinus()
            fragmentD.arguments = bundleD
            childFragmentManager.commit {
                replace(R.id.frPlusMinusSmallYellow1D10, fragmentD)
                addToBackStack(null)
            }

            // настроить чтобы когда 1д10 = 10, то мы меняем critic grey на critic yellow , сохраняя его значение во VM
            // подключаем фрагмент плюс и минус critic

            val greyCritic = view.findViewById<LinearLayout>(R.id.plmingrey)
            greyCritic.visibility = View.VISIBLE

            val bundleC = Bundle()
            bundleC.putInt("value", 1)
            bundleC.putInt("minValue", 1)
            bundleC.putInt("maxValue", 10)
            bundleC.putString("them", "yellow")
            val fragmentC = PlusAndMinus()
            fragmentC.arguments = bundleC
            childFragmentManager.commit {
                replace(R.id.frPlusMinusSmallGreyCritical, fragmentC)
                addToBackStack(null)
            }

            val critText = view.findViewById<TextView>(R.id.critText)
            val frCritic =
                view.findViewById<FragmentContainerView>(R.id.frPlusMinusSmallGreyCritical)
            frCritic.visibility = View.GONE
            critText.setTextColor(resources.getColor(R.color.grey))

            mSkillVM.m1d10.observe(viewLifecycleOwner) {
                if (it >= 10) {
                    greyCritic.visibility = View.GONE
                    frCritic.visibility = View.VISIBLE
                    critText.setTextColor(resources.getColor(R.color.yellow))
                } else {
                    greyCritic.visibility = View.VISIBLE
                    frCritic.visibility = View.GONE
                    critText.setTextColor(resources.getColor(R.color.grey))
                }
            }

            // Подключаем слежение за изменением списков для editMod or editDifficult
            val difficultValue = SpecialGameData().difficultValue
            val modValue = SpecialGameData().modValue
            mSkillVM.difficult.observe(viewLifecycleOwner) {
                tvEdit.text = difficultValue[it]
            }
            mSkillVM.modification.observe(viewLifecycleOwner) {
                tvEditMod.text = modValue[it]
            }

            back.setOnClickListener {
                view.findNavController().popBackStack()
            }

            val next = view.findViewById<TextView>(R.id.btn_next)
            next.setOnClickListener {
                // здесь какие-то проверки
                // подсчитываем во VM и передаем значение
                val difficult = tvEdit.text.toString().toIntOrNull()
                val mod = tvEditMod.text.toString().toIntOrNull()

                val fr1d10: Fragment =
                    childFragmentManager.findFragmentById(R.id.frPlusMinusSmallYellow1D10)!!
                val res1d10 =
                    fr1d10.view?.findViewById<EditText>(R.id.plus_and_minus_edit)?.text.toString()
                        .toIntOrNull()

                var resCrit: Int? = 0
                if (res1d10 != null) {
                    if (res1d10 == 10) {
                        val frCrit: Fragment =
                            childFragmentManager.findFragmentById(R.id.frPlusMinusSmallGreyCritical)!!
                        resCrit =
                            frCrit.view?.findViewById<EditText>(R.id.plus_and_minus_edit)?.text.toString()
                                .toIntOrNull()
                    }
                }
                // передаем ответ
                val bundleF = Bundle()
                bundleF.putString("title", title)
                if (difficult != null) {
                    bundleF.putString("difficult", difficult.toString())
                }
                if (mod != null) {
                    bundleF.putString("mod", mod.toString())
                }
               if (res1d10 != null) {
                    bundleF.putString("1d10", res1d10.toString())
                }
                if (resCrit != null) {
                bundleF.putString("crit", resCrit.toString())
                }

                view.findNavController()
                    .navigate(R.id.action_pres_skillTest_to_skillResult, bundleF)
            }*/

        } catch (e: Exception) {
            Toast.makeText(view.context, "$e", Toast.LENGTH_LONG).show()
        }
        return view
    }

}