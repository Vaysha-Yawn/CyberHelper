package com.example.test.helpers

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.test.widgets.Roll

class RollAdapterVP2(fragment: Fragment) : FragmentStateAdapter(fragment) {
    var list = listOf<Int>()

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = Roll()
        val bundle = Bundle()
        bundle.putString("goal", "goal")
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