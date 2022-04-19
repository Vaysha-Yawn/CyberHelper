package com.example.test.helpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.databinding.DropDownListItemBinding
import com.example.test.views.DropDownView

class DropDownAdapterRV(
    val list: List<String>,
    val them: String,
    private val onClick: DropDownView.OnItemClickListener
) :
    RecyclerView.Adapter<DropDownAdapterRV.TemplateHolder>() {

    class TemplateHolder(view: View, private val onClick: DropDownView.OnItemClickListener) :
        RecyclerView.ViewHolder(view) {
        private val binding = DropDownListItemBinding.bind(view)

        fun bind(item: String, them: String) = with(binding) {
            tvItem.text = item
            when (them) {
                "yellow" -> {
                    tvItem.setTextColor(tvItem.context.getColor(R.color.yellow))
                }
                "green" -> {
                    tvItem.setTextColor(tvItem.context.getColor(R.color.green))
                }
                "blue" -> {
                    tvItem.setTextColor( tvItem.context.getColor(R.color.cyan))
                }
            }
            tvItem.setOnClickListener{
                onClick.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DropDownAdapterRV.TemplateHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.drop_down_list_item, parent, false)
        return DropDownAdapterRV.TemplateHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: DropDownAdapterRV.TemplateHolder, position: Int) {
        holder.bind(list[position], them)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
