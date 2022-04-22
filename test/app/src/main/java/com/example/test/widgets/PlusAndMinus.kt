package com.example.test.widgets

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.test.R
import com.example.test.viewModels.SkillTestVM


class PlusAndMinus : Fragment() {

    private val mSkillVM: SkillTestVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.plus_and_minus_full_orange, container, false)

        val args = this.arguments
        val value = args?.getInt("value", 0) ?: 0
        var maxValue = args?.getInt("maxValue", -1)
        var minValue = args?.getInt("minValue", -1)
        val them = args?.getString("them", "orange") ?: "orange"
        val goal = args?.getString("goal", "") ?: ""
        val index = args?.getInt("indexMod", -1) ?: -1
        val editKey = args?.getInt("editKey", -1) ?: mSkillVM.createId()

        when (them) {
            "orange" -> {
                view = inflater.inflate(R.layout.plus_and_minus_full_orange, container, false)
            }
            "orange_small" -> {
                view = inflater.inflate(R.layout.plus_and_minus_small_orange, container, false)
            }
            "green" -> {
                view = inflater.inflate(R.layout.plus_and_minus_small_green, container, false)
            }
            "yellow" -> {
                view = inflater.inflate(R.layout.plus_and_minus_small_yellow, container, false)
            }
            "blue" -> {
                view = inflater.inflate(R.layout.plus_and_minus_small_blue, container, false)
            }
        }

        val edit = view.findViewById<TextView>(R.id.edit)
        val plus = view.findViewById<Button>(R.id.plus)
        val minus = view.findViewById<Button>(R.id.minus)

        if (maxValue == -1) {
            maxValue = null
        }
        if (minValue == -1) {
            minValue = null
        }

        edit.text = value.toString()

        fun setValueForSkillVM(goal:String, value:Int){
            when (goal) {
                "1d10" -> {
                    //mSkillVM.m1d10.value = value
                }
                "critical" -> {
                    //mSkillVM.critical.value = value
                }
                "mod" -> {
                    if (index >= 0) {
                        mSkillVM.mapMod[editKey]?.value?.get(index)?.value = value
                    }
                }
                "luck" -> {
                    mSkillVM.usingLuckyPoint = value
                }
            }
            mSkillVM.mapInt[editKey]?.value = value
        }


        if (goal == "1d10" || goal == "critical" || goal == "mod") {
            edit.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val x = edit.text.toString().toIntOrNull()
                    if (x != null) {
                        setValueForSkillVM(goal, x)
                    }
                    // добавить невозможность ввести значение больше чем макс или мин или не число
                }
                override fun afterTextChanged(s: Editable?) {
                }
            })
        }


        plus.setOnClickListener {
            if (maxValue != null) {
                var res = edit.text.toString().toInt()
                if (res < maxValue) {
                    res += 1
                    edit.text = res.toString()
                    setValueForSkillVM(goal, res)
                } else {
                    Toast.makeText(
                        view.context, "Значение параметра не должно превышать $maxValue",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                var res = edit.text.toString().toInt()
                res += 1
                edit.text = res.toString()
                setValueForSkillVM(goal, res)
            }
        }

        minus.setOnClickListener {
            if (minValue != null) {
                var res = edit.text.toString().toInt()
                if (res > minValue) {
                    res -= 1
                    edit.text = res.toString()
                    setValueForSkillVM(goal, res)
                } else {
                    Toast.makeText(
                        view.context, "Значение параметра не может быть меньше $minValue",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                var res = edit.text.toString().toInt()
                res -= 1
                edit.text = res.toString()
                setValueForSkillVM(goal, res)
            }
        }
        return view
    }
}

/* Для подключения фрагмента в ваш layout используйте код ниже

loadFragment(PlusAndMinus())

private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.Character_list_fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

 */