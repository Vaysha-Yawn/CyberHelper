package com.example.test.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.example.test.R
import com.example.test.viewModels.SkillTestVM

class m1D10 : Fragment() {

    private val mSkillVM: SkillTestVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.m1_d10, container, false)

        val key1d10 = mSkillVM.addToMapInt(1)
        val keyCrit = mSkillVM.addToMapInt(1)

        // подключаем фрагмент плюс и минус 1 d 10
        val bundleD = Bundle()
        bundleD.putInt("value", 1)
        bundleD.putInt("minValue", 1)
        bundleD.putInt("maxValue", 10)
        bundleD.putInt("editKey", key1d10)
        bundleD.putString("them", "yellow")
        bundleD.putString("goal", "1d10")
        val fragmentD = PlusAndMinus()
        fragmentD.arguments = bundleD
        childFragmentManager.commit {
            replace(R.id.frPlusMinusSmallYellow1D10, fragmentD)
            addToBackStack(null)
        }

        // настроить чтобы когда 1д10 = 10, то мы меняем critic grey на critic yellow , сохраняя его значение во VM
        // подключаем фрагмент плюс и минус critic

        val greyCritic = view.findViewById<LinearLayout>(R.id.plmingrey)
        greyCritic.visibility = View.VISIBLE

        val bundleC = Bundle()
        bundleC.putInt("value", 1)
        bundleC.putInt("minValue", 1)
        bundleC.putInt("maxValue", 10)
        bundleC.putInt("editKey", keyCrit)
        bundleC.putString("them", "yellow")
        bundleC.putString("goal", "critical")
        val fragmentC = PlusAndMinus()
        fragmentC.arguments = bundleC
        childFragmentManager.commit {
            replace(R.id.frPlusMinusSmallGreyCritical, fragmentC)
            addToBackStack(null)
        }

        val critText = view.findViewById<TextView>(R.id.critText)
        val frCritic =
            view.findViewById<FragmentContainerView>(R.id.frPlusMinusSmallGreyCritical)
        frCritic.visibility = View.GONE
        critText.setTextColor(resources.getColor(R.color.grey))

        mSkillVM.mapInt[key1d10]?.observe(viewLifecycleOwner) {
            if (it >= 10) {
                greyCritic.visibility = View.GONE
                frCritic.visibility = View.VISIBLE
                critText.setTextColor(resources.getColor(R.color.yellow))
                mSkillVM.boolCritical.value = true
            } else {
                greyCritic.visibility = View.VISIBLE
                frCritic.visibility = View.GONE
                critText.setTextColor(resources.getColor(R.color.grey))
                mSkillVM.boolCritical.value = false
            }
        }

        return view
    }
}