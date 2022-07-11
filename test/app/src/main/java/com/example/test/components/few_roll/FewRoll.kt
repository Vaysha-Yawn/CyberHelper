package com.example.test.components.few_roll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.test.R
import com.example.test.components.few_roll.roll.Roll
import com.example.test.databinding.FewRollBinding
import com.example.test.data_base.realm.character.CharacterDAO
import com.example.test.viewModels.FewRolls
import com.example.test.viewModels.SkillTestVM


class FewRoll : Fragment() {

    private val mSkillVM: SkillTestVM by activityViewModels()
    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val VM: FewRollVM by activityViewModels()

    private lateinit var ViewPager2: ViewPager2
    private lateinit var adapter: RollAdapterVP2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.few_roll, container, false)
        val binding = FewRollBinding.bind(view)

        fun bind() = with(binding) {

            if (VM.chosenRolls.isEmpty()) {
                val id = R.id.radioButton
                VM.add(id, Roll().apply {
                    arguments = Roll().getRollBundle(true, id)
                })
            } else {
                reCreateRoll(view.findViewById(R.id.radioGroup), view)
            }

            adapter = RollAdapterVP2(
                childFragmentManager,
                lifecycle
            )


            ViewPager2 = VP2
            VP2.adapter = adapter

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
                    VM.delete(id)
                    VM.fragments.observe(viewLifecycleOwner) {
                        adapter.setData(it.values.toMutableList())
                    }
                }
                adapter.remove()
            }

            add.setOnClickListener {
                if (radioGroup.childCount <= 6 && VM.allGoals.size > adapter.itemCount) {
                    val id = VM.getNewId()
                    val radio = RadioButton(view.context)
                    radio.buttonTintList = view.context.resources.getColorStateList(R.color.yellow)
                    radio.text = ""
                    radio.id = id
                    radioGroup.addView(radio)
                    VM.add(id, Roll().apply {
                        arguments = Roll().getRollBundle(true, id)
                    })
                    VM.fragments.observe(viewLifecycleOwner) {
                        adapter.setData(it.values.toMutableList())
                    }
                    radioGroup.check(id)
                    adapter.add()
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

        VM.fragments.observe(viewLifecycleOwner) {
            adapter.setData(it.values.toMutableList())
        }

        return view
    }

    fun getFewRollBundle(goal: Boolean): Bundle {
        val bundle = Bundle()
        if (goal) {
            bundle.putString("goal", "goal")
        } else {
            bundle.putString("goal", "")
        }
        return bundle
    }

    fun getFewRoll(): FewRolls {
        return FewRolls(VM.chosenRolls.values.toMutableList())
    }

    private fun reCreateRoll(radioGroup: RadioGroup, view: View) {
        radioGroup.removeView(view.findViewById(R.id.radioButton))
        for (id in VM.listId.value!!) {
            val radio = RadioButton(view.context)
            radio.buttonTintList = view.context.resources.getColorStateList(R.color.yellow)
            radio.text = ""
            radio.id = id
            radioGroup.addView(radio)
        }
        radioGroup.check(VM.getElement(0))
    }

}