package com.example.test.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.test.R
import com.example.test.databinding.FewRollBinding
import com.example.test.helpers.RollAdapterVP2
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.FewRollVM
import com.example.test.viewModels.SkillTestVM


class FewRoll : Fragment() {

    private val mSkillVM: SkillTestVM by activityViewModels()
    private val mCharacterVM: CharacterDAO by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.few_roll, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FewRollBinding.bind(view)

        val VM = ViewModelProvider(this)[FewRollVM::class.java]

        val keyAllGoals = mSkillVM.createId()
        val keyRolls = mSkillVM.createId()

        mSkillVM.mapRoll[keyRolls] = mutableMapOf()

        mSkillVM.mapGoal[keyAllGoals] = MutableLiveData()

        // заполняем лист
        val goalsList = mutableListOf<Goal>()
        mCharacterVM.characterList.value?.forEach {
            if (it.gameId == mCharacterVM.gameId) {
                if (it.id != mCharacterVM.characterId) {
                    val goal = Goal()
                    goal.characterId = it.id
                    val name = it.attributes.singleOrNull { gp ->
                        gp.title == "Базовые параметры"
                    }?.attributes?.listParamStr?.singleOrNull { pn ->
                        pn.name == "Имя персонажа"
                    }?.value ?: ""
                    goal.name = name
                    goalsList.add(goal)
                }
            }
        }
        mSkillVM.mapGoal[keyAllGoals]?.value = goalsList

        val adapter = RollAdapterVP2(this@FewRoll, keyAllGoals, keyRolls)

        fun bind() = with(binding) {
            VP2.adapter = adapter

            VM.add(R.id.radioButton)

            delete.setOnClickListener {
                if (radioGroup.childCount >= 2) {
                    val id = radioGroup.checkedRadioButtonId
                    val position = VM.getIndex(id)
                    if (position != 0) {
                        radioGroup.check(VM.getElement(position - 1))
                    } else {
                        radioGroup.check(VM.getElement(1))
                    }
                    radioGroup.removeView(view.findViewById(id))
                    adapter.blablaRemove(VM.getIndex(id))
                    VM.delete(id)
                }
            }
            add.setOnClickListener {
                if (radioGroup.childCount <= 6) {
                    val id = View.generateViewId()
                    val radio = RadioButton(view.context)
                    radio.buttonTintList = view.context.resources.getColorStateList(R.color.yellow)
                    radio.text = ""
                    radio.id = id
                    radioGroup.addView(radio)
                    VM.add(id)
                    adapter.blablaAdd(VM.list.value?.size ?: 1 - 1)
                    radioGroup.check(id)
                }
            }

            radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
                VP2.currentItem = VM.getIndex(checkedId)
            }

            VP2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    radioGroup.check(VM.getElement(position))
                }
            })
        }
        bind()

        VM.list.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

    }

}