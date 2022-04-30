package com.example.test.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.databinding.DropDownListItemBinding

class DropDownAdapterRV(
    val list: List<String>,
    val color: Int,
    private val onClick: TemplateHolder.WhenValueTo?,
    private val onDDClick: TemplateHolder.Select,
    private val check: TemplateHolder.CheckChoose?
) :
    RecyclerView.Adapter<DropDownAdapterRV.TemplateHolder>() {

    class TemplateHolder(view: View, private val onClick: TemplateHolder.WhenValueTo?, private val onDDClick: TemplateHolder.Select,  private val check: CheckChoose?) :
        RecyclerView.ViewHolder(view) {
        private val binding = DropDownListItemBinding.bind(view)

        fun bind(item: String, color: Int) = with(binding) {
            tvItem.text = item
            tvItem.setTextColor(color)
            tvItem.setOnClickListener{
                if (check==null){
                    onClick?.whenValueTo(adapterPosition)
                    onDDClick.select(adapterPosition)
                }else{
                    if (check.checkChoose(adapterPosition)){
                        onClick?.whenValueTo(adapterPosition)
                        onDDClick.select(adapterPosition)
                    }else{
                        check.onCheckedFalse()
                    }
                }
            }
        }

        interface WhenValueTo{
            fun whenValueTo(position: Int)
        }

        interface Select{
            fun select(position: Int)
        }

        interface CheckChoose{
            fun checkChoose(position: Int):Boolean
            fun onCheckedFalse()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DropDownAdapterRV.TemplateHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.drop_down_list_item, parent, false)
        return DropDownAdapterRV.TemplateHolder(view, onClick, onDDClick, check)
    }

    override fun onBindViewHolder(holder: DropDownAdapterRV.TemplateHolder, position: Int) {
        holder.bind(list[position], color)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
