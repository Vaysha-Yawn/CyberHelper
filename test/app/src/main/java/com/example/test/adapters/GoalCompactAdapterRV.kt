package com.example.test.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data_base.Goal
import com.example.test.data_base.Mod
import com.example.test.data_base.SpecialGameData
import com.example.test.databinding.CardModBinding


class GoalCompactAdapterRV(
    private val deleteGoalCompact: GoalCompactTemplateHolder.DeleteGoalCompact,
    private val listener: GoalCompactTemplateHolder.PutGoalCompactValue,
) :
    RecyclerView.Adapter<GoalCompactTemplateHolder>(), GoalCompactTemplateHolder.updView {

    var list = mutableListOf<Goal>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalCompactTemplateHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_mod, parent, false)
        return GoalCompactTemplateHolder(view, deleteGoalCompact, this, listener)
    }

    override fun onBindViewHolder(holder: GoalCompactTemplateHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(list: MutableList<Goal>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun updateView() {
        notifyDataSetChanged()
    }

}

class GoalCompactTemplateHolder(
    view: View,
    private val deleteGoalCompact: DeleteGoalCompact,
    private val updViewr: updView,
    private val listener: PutGoalCompactValue,
) : RecyclerView.ViewHolder(view), DropDownAdapterRV.TemplateHolder.WhenValueTo {
    private val binding = CardModBinding.bind(view)

    fun bind(goal: Goal) = with(binding) {

        PMLinear.visibility = View.GONE
        DD.visibility = View.VISIBLE
        DD.setDDArrayAndListener(SpecialGameData().modName, this@GoalCompactTemplateHolder)

        delete.setOnClickListener { view ->
            deleteGoalCompact.deleteGoalCompact(adapterPosition)
            updViewr.updateView()
        }
    }

    interface updView {
        fun updateView()
    }

    interface DeleteGoalCompact {
        fun deleteGoalCompact(position: Int)
    }

    interface PutGoalCompactValue {
        fun putGoalCompactValue(position: Int, value: Int)
    }

    override fun whenValueTo(position: Int) {
        listener.putGoalCompactValue(adapterPosition, position)
    }
}