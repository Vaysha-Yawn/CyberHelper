package com.example.test.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data_base.Goal


class GoalAdapterRV(
    private val deleteMod: GoalTemplateHolder.DeleteGoal,
    private val loadFragment: GoalTemplateHolder.LoadFragment,
    private val updIdMode: GoalTemplateHolder.updIdGoal,
) :
    RecyclerView.Adapter<GoalTemplateHolder>(), GoalTemplateHolder.updView {

    var list = mutableListOf<Goal>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalTemplateHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_mod, parent, false)
        return GoalTemplateHolder(view, deleteMod, loadFragment, this, updIdMode)
    }

    override fun onBindViewHolder(holder: GoalTemplateHolder, position: Int) {
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

class GoalTemplateHolder(
    view: View,
    private val deleteGoal: GoalTemplateHolder.DeleteGoal,
    private val loadFragment: GoalTemplateHolder.LoadFragment,
    private val updViewr: GoalTemplateHolder.updView,
    private val updId: GoalTemplateHolder.updIdGoal,
) : RecyclerView.ViewHolder(view) {
    private val binding = com.example.test.databinding.CardModBinding.bind(view)

    fun bind(goal: Goal) = with(binding) {
        try {
            val id =if(goal.resId == 0){
                 View.generateViewId()
            }else{
                goal.resId
            }
            //fr.id = id
            updId.updIdMod(adapterPosition, id)
            loadFragment.loadFragment(adapterPosition, id)
            delete.setOnClickListener { view ->
                deleteGoal.deleteGoal(adapterPosition)
                updViewr.updateView()
            }

        } catch (e: Exception) {
            Toast.makeText(delete.context, "$e", Toast.LENGTH_LONG).show()
        }
    }
    interface updView {
        fun updateView()
    }

    interface DeleteGoal {
        fun deleteGoal(position: Int)
    }

    interface LoadFragment {
        fun loadFragment(position: Int, id: Int)
    }

    interface updIdGoal {
        fun updIdMod(position: Int, id: Int)
    }
}

