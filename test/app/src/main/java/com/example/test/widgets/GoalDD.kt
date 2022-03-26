package com.example.test.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.helpers.GoalDDAdapterRV
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.SkillTestVM

class GoalDD : Fragment(), GoalDDAdapterRV.TemplateHolder.OnItemClickListener {

    private val mSkillVM: SkillTestVM by activityViewModels()
    private val mCharacterVM: CharacterDAO by activityViewModels()

    private lateinit var tvMainTypeWeapon: TextView
    private lateinit var itemsRV: RecyclerView
    private var more: Int = 0
    private var less: Int = 0
    private var indexMod: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.drop_down_list, container, false)
        val bundle = this.arguments
        val main = bundle!!.getString("main", "")
        val them = bundle.getString("them", "yellow")
        indexMod = bundle.getInt("indexMod", -1) ?: -1

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
        mSkillVM.allGoals.observe(viewLifecycleOwner) {
            val list = mutableListOf<String>()
            it.forEach { goal ->
                list.add(goal.name)
            }
            if (list.isEmpty()) {
                tvMainTypeWeapon.text = "Целей нет"
            }
            adapterItems.setData(list)
        }

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
        mSkillVM.allGoals.value = goalsList
        if (goalsList.isEmpty()) {
            tvMainTypeWeapon.text = "Цели не найдены"
        }

        return view
    }

    override fun onItemClick(position: Int) {
        tvMainTypeWeapon.text = mSkillVM.allGoals.value?.get(position)?.name
        itemsRV.visibility = View.GONE
        tvMainTypeWeapon.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            more,
            0
        )

        val chosenGoal = mSkillVM.allGoals.value?.get(position)
        if (chosenGoal != null) {
            mSkillVM.chosenGoals.value?.set(position, chosenGoal)
            mSkillVM.allGoals.value?.remove(chosenGoal)
        }

    }
}