package com.example.test.helpers


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data_base.EffectWeapon
import com.example.test.data_base.Item
import com.example.test.databinding.CardWeaponFightBinding


class WeaponFightAdapterRV(val map: Map<EffectWeapon, Item>, private val onClick: WeaponFightAdapterRV.TemplateHolder.OnItemClickListener) :
    RecyclerView.Adapter<WeaponFightAdapterRV.TemplateHolder>() {

    class TemplateHolder(view: View, private val onClick: WeaponFightAdapterRV.TemplateHolder.OnItemClickListener) : RecyclerView.ViewHolder(view) {
        private val binding = CardWeaponFightBinding.bind(view)

        @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
        fun bind( item: Item,effect: EffectWeapon) = with(binding) {

            when (effect.fightType) {
                "Взрывчатка" -> {
                    image.setImageDrawable(image.context.getDrawable(R.drawable.bomb))
                    text.background.setTint(text.context.getColor(R.color.purple))
                }
                "Дальний бой" -> {
                    image.setImageDrawable(image.context.getDrawable(R.drawable.gun))
                    text.background.setTint(text.context.getColor(R.color.yellow))
                }
                "Автоматический огонь" -> {
                    image.setImageDrawable(image.context.getDrawable(R.drawable.gun))
                    text.background.setTint(text.context.getColor(R.color.yellow))
                }
                "Рукопашный бой" -> {
                    image.setImageDrawable(image.context.getDrawable(R.drawable.punch))
                    text.background.setTint(text.context.getColor(R.color.orange))
                }
                "Ближний бой" -> {
                    image.setImageDrawable(image.context.getDrawable(R.drawable.sword))
                    text.background.setTint(text.context.getColor(R.color.cyan))
                }
            }
            text.text = item.name
            text.setOnClickListener{
                onClick.onItemClick(adapterPosition, item, effect)
            }
        }

        interface OnItemClickListener{
            fun onItemClick(position: Int, item: Item, effect: EffectWeapon)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_weapon_fight, parent, false)
        return TemplateHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: TemplateHolder, position: Int) {
        holder.bind(map.values.elementAt(position), map.keys.elementAt(position))
    }

    override fun getItemCount(): Int {
        return map.size
    }
}