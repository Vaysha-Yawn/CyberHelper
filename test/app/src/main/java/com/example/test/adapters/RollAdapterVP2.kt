package com.example.test.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.test.widgets.Roll

class RollAdapterVP2(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    var fragments = mutableListOf<Roll>()

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemId(position: Int): Long {
        return fragments[position].hashCode().toLong()
    }

    fun add() {
        notifyDataSetChanged()
    }

    fun remove() {
        notifyDataSetChanged()
    }

    fun setData(fragments: MutableList<Roll>) {
        this.fragments = fragments
        notifyDataSetChanged()
    }

}