package com.example.test.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.databinding.DropDownListItemBinding

class GoalDDAdapterRV( val them: String, private val onClick: TemplateHolder.OnItemClickListener) :
    RecyclerView.Adapter<GoalDDAdapterRV.TemplateHolder>() {

    var list = emptyList<String>()

    fun setData(list:List<String>){
        this.list = list
        notifyDataSetChanged()
    }

    class TemplateHolder(view: View, private val onClick: OnItemClickListener) : RecyclerView.ViewHolder(view){
        private val binding = DropDownListItemBinding.bind(view)

        fun bind(item: String, them: String) = with(binding) {
            tvItem.text = item
            when (them) {
                "yellow" -> {
                    tvItem.setTextColor( tvItem.context.getColor(R.color.yellow))
                }
                "green" -> {
                    tvItem.setTextColor( tvItem.context.getColor(R.color.green))
                }
                "blue" -> {
                    tvItem.setTextColor( tvItem.context.getColor(R.color.cyan))
                }
            }
            tvItem.setOnClickListener{
                onClick.onItemClick(adapterPosition)
            }

        }

        interface OnItemClickListener{
            fun onItemClick(position: Int)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GoalDDAdapterRV.TemplateHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.drop_down_list_item, parent, false)
        return GoalDDAdapterRV.TemplateHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: GoalDDAdapterRV.TemplateHolder, position: Int) {
        holder.bind(list[position], them)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
