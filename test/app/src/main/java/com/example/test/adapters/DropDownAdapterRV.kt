package com.example.test.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.databinding.DropDownListItemBinding

class DropDownAdapterRV(
    val list: List<String>,
    private val colorText: Int,
    private var colorBackground: Int?,
    private val onClick: TemplateHolder.WhenValueTo?,
    private val onDDClick: TemplateHolder.Select,
    private val check: TemplateHolder.CheckChoose?
) :
    RecyclerView.Adapter<DropDownAdapterRV.TemplateHolder>() {

    class TemplateHolder(view: View, private val onClick: TemplateHolder.WhenValueTo?, private val onDDClick: TemplateHolder.Select,  private val check: CheckChoose?) :
        RecyclerView.ViewHolder(view) {
        private val binding = DropDownListItemBinding.bind(view)

        fun bind(item: String, colorText: Int, colorBackground: Int?) = with(binding) {

            if (colorBackground!=null){
                tvItem.background = tvItem.context.resources.getDrawable(R.drawable.background_black)
                if (colorBackground == tvItem.context.resources.getColor(R.color.black)){
                    tvItem.backgroundTintList = ColorStateList.valueOf(tvItem.context.resources.getColor(R.color.dark_grey))
                }else{
                    tvItem.backgroundTintList = ColorStateList.valueOf(colorBackground)
                }
            }
            tvItem.text = item

            if (colorText == tvItem.context.resources.getColor(R.color.dark_grey)){
                tvItem.setTextColor(tvItem.context.resources.getColor(R.color.black))
            }else{
                tvItem.setTextColor(colorText)
            }

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
        holder.bind(list[position], colorText, colorBackground)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
