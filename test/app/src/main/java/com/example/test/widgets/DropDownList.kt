package com.example.test.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.activity_and_fragments.hosts.PresentHost
import com.example.test.helpers.DropDownAdapterRV
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.SkillTestVM

class DropDownList : Fragment(), DropDownAdapterRV.TemplateHolder.OnItemClickListener {

    private val mSkillVM: SkillTestVM by activityViewModels()
    private val mCharacterVM: CharacterDAO by activityViewModels()

    private lateinit var goal: String
    private lateinit var tvMainTypeWeapon: TextView
    private lateinit var itemsRV: RecyclerView
    private lateinit var list: ArrayList<String>
    private var more: Int = 0
    private var less: Int = 0
    private var indexMod: Int = -1
    private var key: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.drop_down_list, container, false)
        val bundle = this.arguments
        val main = bundle!!.getString("main", "")
        val them = bundle.getString("them", "yellow")
        list = bundle.getStringArrayList("list")!!
        goal = bundle.getString("goal", "")
        indexMod = bundle.getInt("indexMod", -1)
        key = bundle.getInt("key")

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
            DropDownAdapterRV(list, them, this)
        itemsRV.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        itemsRV.adapter = adapterItems

        if (goal == "modification") {
            val value = mSkillVM.mapMod[key]?.value?.get(indexMod)?.value ?: 1
            if (indexMod >= 0 && value > 0) {
                tvMainTypeWeapon.text = list[value - 1]
            }

        }

        return view
    }

    override fun onItemClick(position: Int) {
        tvMainTypeWeapon.text = list[position]
        itemsRV.visibility = View.GONE
        tvMainTypeWeapon.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            more,
            0
        )

        if (goal == "characterMenu") {
            (activity as PresentHost).openSkillTest(mCharacterVM.characterId)
        }

        if (goal == "difficult") {
            mSkillVM.dif.value = position
            mSkillVM.difBoolean.value = !mSkillVM.difBoolean.value!!
        }

        if (goal == "modification") {
            if (indexMod >= 0) {
                mSkillVM.mapMod[key]?.value?.get(indexMod)?.value = position + 1
            }
        }
        if (goal == "goal") {
            val chosenGoal = mSkillVM.allGoals.value?.get(position)
            if (chosenGoal!=null){
                mSkillVM.chosenGoals.value?.set(position, chosenGoal)
                mSkillVM.allGoals.value?.remove(chosenGoal)
            }
        }
    }
}