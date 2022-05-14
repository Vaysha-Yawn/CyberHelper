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
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.test.R
import com.example.test.adapters.RollAdapterVP2
import com.example.test.data_base.Goal
import com.example.test.databinding.FewRollBinding
import com.example.test.viewModels.*
import kotlin.properties.Delegates

// todo не переживает поворот экрана
class FewRoll : Fragment() {

    private val mSkillVM: SkillTestVM by activityViewModels()
    private val mCharacterVM: CharacterDAO by activityViewModels()

    private lateinit var ViewPager2: ViewPager2

    private var keyFragment by Delegates.notNull<Int>()

    private val VM: FewRollVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.few_roll, container, false)
        //VM = ViewModelProvider(this)[FewRollVM::class.java]
        keyFragment = requireArguments().getInt("keyFragment")

        val binding = FewRollBinding.bind(view)

        val keyAllGoals = mSkillVM.createId()

        VM.add(R.id.radioButton, Roll().apply {
            arguments = Roll().getRollBundle(true, keyAllGoals, keyFragment, R.id.radioButton)
        })

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

        val adapter = RollAdapterVP2(
            childFragmentManager,
            lifecycle
        )

        fun bind() = with(binding) {

            ViewPager2 = VP2
            VP2.adapter = adapter

            VM.fragments.observe(viewLifecycleOwner) {
                adapter.setData(it)
            }

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
                    mSkillVM.deleteRollInFewRoll(keyFragment, id)
                    VM.delete(id, position)
                    adapter.remove()
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
                    VM.add(id, Roll().apply {
                        arguments = Roll().getRollBundle(true, keyAllGoals, keyFragment, id)
                    })
                    adapter.add()
                    radioGroup.check(id)
                } else {
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

        return view
    }

    fun getFewRollBundle(goal: Boolean, keyFragment: Int): Bundle {
        val bundle = Bundle()
        if (goal) {
            bundle.putString("goal", "goal")
        } else {
            bundle.putString("goal", "")
        }
        bundle.putInt("keyFragment", keyFragment)
        return bundle
    }

    fun getFewRoll(): FewRolls {
        val list = mutableListOf<OneRoll>()
        for (fragmentRoll in VM.fragments.value!!) {
            list.add(fragmentRoll.getRoll())
        }
        return FewRolls(list)
    }

}