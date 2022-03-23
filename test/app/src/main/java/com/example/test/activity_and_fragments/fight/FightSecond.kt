package com.example.test.activity_and_fragments.fight

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import com.example.test.R
import com.example.test.data_base.EffectWeapon
import com.example.test.databinding.FightSecondBinding
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.SkillTestVM
import com.example.test.widgets.*


class FightSecond : Fragment() {

    private val mSkillVM: SkillTestVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fight_second, container, false)

        fun loadPM(value:Int, minValue:Int, maxValue:Int?, resId:Int){
            val bundleD = Bundle()
            bundleD.putInt("value", value)
            bundleD.putInt("minValue", minValue)
            if (maxValue!=null){
                bundleD.putInt("maxValue", maxValue)
            }
            bundleD.putString("them", "green")
            bundleD.putString("goal", "")
            val fragmentD = PlusAndMinus()
            fragmentD.arguments = bundleD
            childFragmentManager.commit {
                replace(resId, fragmentD)
                addToBackStack(null)
            }
        }
        fun loadDD(main:String, them:String, goal:String, list:ArrayList<String>, id:Int){
            val fragment = DropDownList()
            val bundle = Bundle()
            bundle.putString("main", main)
            bundle.putString("them", them)
            bundle.putString("goal", goal)
            bundle.putStringArrayList("list", list)
            fragment.arguments = bundle
            childFragmentManager.commit {
                replace(id, fragment)
                addToBackStack(null)
            }
        }

        fun loadFragmentLight(fragment:Fragment, id:Int){
            childFragmentManager.commit {
                replace(id, fragment)
                addToBackStack(null)
            }
        }

        val attack = mSkillVM.attack ?: EffectWeapon()
        when (attack.fightType.roll) {
            "one roll" -> {
                loadFragmentLight(Roll(), R.id.mainFr)
            }
            "few roll" -> {
                //здесь добавить VP2 в качестве отдельного фрагмента
                loadFragmentLight(Roll(), R.id.mainFr)
            }
            "arbitrary number" -> {
                loadPM(0, 0, null, R.id.mainFr)
            }
        }

        val fragmentHeader = Header()
        childFragmentManager.commit {
            replace(R.id.header, fragmentHeader)
            addToBackStack(null)
        }

        val binding = FightSecondBinding.bind(view)
        fun bind() = with(binding) {
            title.text = attack.fightType.name

            btnNext.setOnClickListener {
                view.findNavController().navigate(R.id.action_fightAttack_to_fightThreeGoal)
            }
        }

        bind()

        return view
    }


}