package com.example.test.activity_and_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.data_base.SpecialGameData
import com.example.test.databinding.SkillTest2Binding
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.SkillTestVM
import com.example.test.widgets.DropDownList
import com.example.test.widgets.Modificators
import com.example.test.widgets.m1D10
import kotlin.reflect.typeOf


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

        val binding = SkillTest2Binding.bind(view)
        fun bind() = with(binding) {
            title.text = txtitle

            mSkillVM.difBoolean.observe(viewLifecycleOwner) {
                tvEdit.text = difficultValue[mSkillVM.dif.value!!]
            }

            back.setOnClickListener {
                view.findNavController().popBackStack()
            }
        }

        bind()

        val next = view.findViewById<TextView>(R.id.btn_next)
        next.setOnClickListener {
            // здесь какие-то проверки
            // подсчитываем во VM и передаем значение
            var res = 1
            val difficult = tvEdit.text.toString().toIntOrNull()
            if (difficult != null) {
                try {
                    mSkillVM.dif.value = difficult
                } catch (e: Exception) {
                    Toast.makeText(view.context, "$e", Toast.LENGTH_LONG).show()
                }
            } else {
                res = 0
            }
            if (res == 1) {
                val bundleF = Bundle()
                bundleF.putString("title", txtitle)
                view.findNavController()
                    .navigate(R.id.action_pres_skillTest_to_skillResult, bundleF)
            }
        }


        return view
    }

}