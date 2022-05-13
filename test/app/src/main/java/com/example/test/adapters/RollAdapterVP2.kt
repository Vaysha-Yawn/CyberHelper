package com.example.test.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.test.viewModels.FewRolls
import com.example.test.widgets.Roll

class RollAdapterVP2(fragment: Fragment, private val keyAllGoals: Int,  private val keyFragment: Int) :
    FragmentStateAdapter(fragment) {
    var list = listOf<Int>()
    var lastIndex = 0

    fun newIndex():Int{
        lastIndex+=1
        return lastIndex
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = Roll()
        val bundle = Bundle()
        bundle.putString("goal", "goal")
        bundle.putInt("keyAllGoals", keyAllGoals )
        bundle.putInt("keyFragment", keyFragment )
        bundle.putInt("position",  newIndex())
        fragment.arguments = bundle
        return fragment
    }

    fun setData(list: List<Int>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun blablaAdd(position: Int) {
        notifyItemInserted(position)
    }

    fun blablaRemove(position: Int) {
        notifyItemRemoved(position)
    }

}