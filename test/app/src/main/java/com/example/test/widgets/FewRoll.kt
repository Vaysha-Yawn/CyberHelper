package com.example.test.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.test.R
import com.example.test.data_base.Goal
import com.example.test.databinding.FewRollBinding
import com.example.test.adapters.RollAdapterVP2
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.FewRollVM
import com.example.test.viewModels.FewRolls
import com.example.test.viewModels.SkillTestVM
import kotlin.properties.Delegates


class FewRoll : Fragment() {

    private val mSkillVM: SkillTestVM by activityViewModels()
    private val mCharacterVM: CharacterDAO by activityViewModels()

    private lateinit var ViewPager2:ViewPager2

    private var keyFragment by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.few_roll, container, false)

        keyFragment = requireArguments().getInt("keyFragment")

        val binding = FewRollBinding.bind(view)

        val VM = ViewModelProvider(this)[FewRollVM::class.java]
        val keyAllGoals = mSkillVM.createId()

        mSkillVM.mapGoal[keyAllGoals] = MutableLiveData()
        mSkillVM.mapRoll[keyFragment] = mutableMapOf()

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

        val adapter = RollAdapterVP2(this@FewRoll, keyAllGoals, keyFragment)

        fun bind() = with(binding) {

            ViewPager2 = VP2
            VP2.adapter = adapter
            VM.add(R.id.radioButton)
            delete.setOnClickListener {
                // todo:он удаляет следующий фрагмент, а не нужный
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
                if (radioGroup.childCount <= 6 && mSkillVM.mapGoal[keyAllGoals]?.value?.size!! > adapter.itemCount) {
                    val id = View.generateViewId()
                    val radio = RadioButton(view.context)
                    radio.buttonTintList = view.context.resources.getColorStateList(R.color.yellow)
                    radio.text = ""
                    radio.id = id
                    radioGroup.addView(radio)
                    VM.add(id)
                    adapter.blablaAdd(VM.list.value?.size ?: 1 - 1)
                    radioGroup.check(id)
                }else{
                    Toast.makeText(
                        requireContext(),
                        "Выбран максимум целей",
                        Toast.LENGTH_SHORT
                    ).show()
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

        return view
    }

    fun getFewRollBundle(goal:Boolean, keyFragment:Int):Bundle{
        val bundle = Bundle()
        if (goal){
            bundle.putString("goal", "goal")
        }else{
            bundle.putString("goal", "")
        }
        bundle.putInt("keyFragment", keyFragment)
        return bundle
    }

    fun getFewRoll():FewRolls{
        return  FewRolls(
            mSkillVM.mapRoll[keyFragment]!!.values.toMutableList()
        )
    }
}