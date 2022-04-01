package com.example.test.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import com.example.test.R

class Roll : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.roll, container, false)
        val goal = arguments?.getString("goal")
        fun loadFragmentLight(fragment:Fragment, id:Int){
            childFragmentManager.commit {
                replace(id, fragment)
                addToBackStack(null)
            }
        }
        loadFragmentLight(m1D10(), R.id.m1d10Fr)
        loadFragmentLight(Modificators(), R.id.modFr)
        if (goal!=null && goal!=""){
            val fragment = GoalDD()
            val bundle = Bundle()
            bundle.putString("main", "Выберите цель")
            bundle.putString("them", "yellow")
            fragment.arguments = bundle
            childFragmentManager.commit {
                replace(R.id.goalFr, fragment)
                addToBackStack(null)
            }
        }else{
            view.findViewById<FragmentContainerView>(R.id.goalFr).visibility = View.GONE
        }
        return view
    }


}