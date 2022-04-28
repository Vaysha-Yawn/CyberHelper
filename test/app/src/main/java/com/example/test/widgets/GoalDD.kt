package com.example.test.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.adapters.GoalDDAdapterRV
import com.example.test.viewModels.SkillTestVM

class GoalDD : Fragment(), GoalDDAdapterRV.TemplateHolder.OnItemClickListener {

    private val mSkillVM: SkillTestVM by activityViewModels()

    private lateinit var tvMainTypeWeapon: TextView
    private lateinit var itemsRV: RecyclerView
    private var more: Int = 0
    private var less: Int = 0
    private var indexMod: Int = -1
    private var keyAllGoals = 0
    private var keyRoll = 0
    private var keyFragment = 0
    private var pos = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.drop_down_list, container, false)
        val bundle = this.arguments
        val main = bundle!!.getString("main", "")
        val them = bundle.getString("them", "yellow")
        indexMod = bundle.getInt("indexMod", -1)
        keyAllGoals = bundle.getInt("keyAllGoals")
        keyRoll = bundle.getInt("keyRoll")
        keyFragment = bundle.getInt("keyFragment")
        pos = bundle.getInt("position")

        tvMainTypeWeapon = view.findViewById<TextView>(R.id.main)
        itemsRV = view.findViewById<RecyclerView>(R.id.items)

        tvMainTypeWeapon.text = main
        itemsRV.visibility = View.GONE

        more = R.drawable.more
        less = R.drawable.less

        when (them) {
            "yellow" -> {
                tvMainTypeWeapon.setBackgroundResource(R.drawable.spinner_yellow)
            }
            "green" -> {
                tvMainTypeWeapon.setBackgroundResource(R.drawable.spinner_green)
            }
            "blue" -> {
                more = R.drawable.more_blue
                less = R.drawable.less_blue
                tvMainTypeWeapon.setBackgroundResource(R.drawable.drop_down_dark_blue)
                tvMainTypeWeapon.setTextColor(resources.getColor(R.color.cyan))
            }
        }
        tvMainTypeWeapon.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            more,
            0
        )
        tvMainTypeWeapon.setOnClickListener {
            if (itemsRV.visibility != View.VISIBLE) {
                itemsRV.visibility = View.VISIBLE
                tvMainTypeWeapon.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    less,
                    0
                )
            } else {
                itemsRV.visibility = View.GONE
                tvMainTypeWeapon.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    more,
                    0
                )
            }
        }

        val adapterItems =
            GoalDDAdapterRV(them, this)
        itemsRV.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        itemsRV.adapter = adapterItems
        mSkillVM.mapGoal[keyAllGoals]?.observe(viewLifecycleOwner) {
            val list = mutableListOf<String>()
            it.forEach { goal ->
                list.add(goal.name)
            }
            if (list.isEmpty()) {
                tvMainTypeWeapon.text = "Целей нет"
            }
            adapterItems.setData(list)
        }

        return view
    }

    override fun onItemClick(position: Int) {
        val chosenGoal = mSkillVM.mapGoal[keyAllGoals]?.value?.get(position)
        if (chosenGoal != null) {
            val map = mSkillVM.mapGoalMap[keyFragment]?.value
            var r = true
            if (map != null) {
                for ((key, value) in map) {
                    if (value == chosenGoal) {
                        r = false
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Список выбранных целей не найден",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (r) {
                tvMainTypeWeapon.text = chosenGoal.name
                itemsRV.visibility = View.GONE
                tvMainTypeWeapon.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    more,
                    0
                )
                mSkillVM.mapGoalMap[keyFragment]?.value?.set(pos, chosenGoal)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Вы уже выбрали эту цель, пожалуйста выберите другую",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Выбранная цель не найдена",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val map = mSkillVM.mapGoalMap[keyFragment]?.value
        if (map != null) {
            map.remove(pos)
        } else {
            Toast.makeText(
                requireContext(),
                "Список выбранных целей не найден",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}
