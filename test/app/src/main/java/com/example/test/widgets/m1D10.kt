package com.example.test.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.test.R
import com.example.test.viewModels.FewRollVM
import com.example.test.viewModels.SkillTestVM
import com.example.test.views.PlusMinusView

class m1D10 : Fragment() {

    private val mSkillVM: SkillTestVM by activityViewModels()

    private val VM: FewRollVM by activityViewModels()

    private lateinit var criticalDummy: LinearLayout
    private lateinit var PMCritical: PlusMinusView
    private lateinit var m1d10: PlusMinusView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.m1_d10, container, false)

        val arg = this.arguments
        val keyRoll = arg?.getInt("keyRoll") ?: 0
        val keyFragment = arg?.getInt("keyFragment") ?: 0

        val pos = arg?.getInt("pos") ?: 0

        criticalDummy = view.findViewById<LinearLayout>(R.id.criticalDummy)
        PMCritical = view.findViewById<PlusMinusView>(R.id.PMCritical)
        m1d10 = view.findViewById<PlusMinusView>(R.id.m1d10)

        PMCritical.setListener(10, 1, object : PlusMinusView.NumberEvent {
            override fun numberEvent(number: Int) {
                if (pos!=0){
                    VM.chosenRolls[pos]?.crit = number
                }
                mSkillVM.mapRoll[keyFragment]?.get(keyRoll)?.crit = number

            }
        }
        )

        m1d10.setListener(10, 1, object : PlusMinusView.NumberEvent {
            override fun numberEvent(number: Int) {
                if (number >= 10) {
                    PMCritical.visibility = View.VISIBLE
                    criticalDummy.visibility = View.GONE
                } else {
                    criticalDummy.visibility = View.VISIBLE
                    PMCritical.visibility = View.GONE
                }
                if (pos!=0){
                    VM.chosenRolls[pos]?.m1d10 = number
                }
                mSkillVM.mapRoll[keyFragment]?.get(keyRoll)?.m1d10 = number

            }

        })

        return view
    }

    fun get1d10(): Int {
        return m1d10.getValue().toIntOrNull() ?: 0
    }

    fun getCritical(): Int {
        return if (get1d10() < 10) {
            0
        } else {
            PMCritical.getValue().toIntOrNull() ?: 0
        }
    }

    fun setCritical(crit:Int) {
        val PMCritical = view?.findViewById<PlusMinusView>(R.id.PMCritical)
        PMCritical?.setValue(crit)
    }

    fun setM1d10(value:Int) {
        val m1d10 = view?.findViewById<PlusMinusView>(R.id.m1d10)
            m1d10?.setValue(value)
    }

    fun getM1d10Bundle(keyRoll: Int, pos: Int, keyFragment: Int, key1d10: Int, keyCrit: Int, ):Bundle{
        val bundle = Bundle()
        bundle.putInt("keyRoll", keyRoll)
        bundle.putInt("pos", pos)
        bundle.putInt("keyFragment", keyFragment)
        bundle.putInt("key1d10", key1d10)
        bundle.putInt("keyCrit", keyCrit)
        return bundle
    }
}