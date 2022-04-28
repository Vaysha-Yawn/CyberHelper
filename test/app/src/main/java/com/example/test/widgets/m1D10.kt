package com.example.test.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.test.R
import com.example.test.viewModels.SkillTestVM
import com.example.test.views.PlusMinusView

class m1D10 : Fragment(), PlusMinusView.NumberEvent {

    private val mSkillVM: SkillTestVM by activityViewModels()

    private lateinit var criticalDummy: LinearLayout
    private  lateinit var PMCritical : PlusMinusView
    private  lateinit var m1d10 : PlusMinusView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.m1_d10, container, false)

        criticalDummy = view.findViewById<LinearLayout>(R.id.criticalDummy)
        PMCritical = view.findViewById<PlusMinusView>(R.id.PMCritical)
        m1d10 = view.findViewById<PlusMinusView>(R.id.m1d10)

        PMCritical.setListener(10, 1,null)
        m1d10.setListener(10, 1, this)

        return view
    }

    fun get1d10():Int{
        return m1d10.getValue()?:0
    }

    fun getCritical():Int{
        return if (get1d10()<10){0}else{PMCritical.getValue()?:0}
    }

    override fun numberEvent(number: Int) {
        if (number >= 10) {
            PMCritical.visibility = View.VISIBLE
            criticalDummy.visibility = View.GONE
        } else {
            criticalDummy.visibility = View.VISIBLE
            PMCritical.visibility = View.GONE
        }
    }
}