package com.example.test.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data_base.Mod
import com.example.test.data_base.SpecialGameData
import com.example.test.databinding.CardModBinding
import com.example.test.views.PlusMinusView


class ModAdapterRV(
    private val deleteMod: ModTemplateHolder.DeleteMod,
    private val listener: ModTemplateHolder.PutModValue,
) :
    RecyclerView.Adapter<ModTemplateHolder>(), ModTemplateHolder.updView {

    var list = mutableListOf<Mod>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModTemplateHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_mod, parent, false)
        return ModTemplateHolder(view, deleteMod, this, listener)
    }

    override fun onBindViewHolder(holder: ModTemplateHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(list: MutableList<Mod>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun updateView() {
        notifyDataSetChanged()
    }

}

class ModTemplateHolder(
    view: View,
    private val deleteMod: DeleteMod,
    private val updViewr: updView,
    private val listener: PutModValue,
) : RecyclerView.ViewHolder(view), DropDownAdapterRV.TemplateHolder.WhenValueTo, PlusMinusView.NumberEvent {
    private val binding = CardModBinding.bind(view)

    fun bind(mod:Mod) = with(binding) {
        if (mod.style){
            PMLinear.visibility = View.GONE
            DD.visibility = View.VISIBLE
            DD.setDDArrayAndListener(SpecialGameData().modName, this@ModTemplateHolder)
        }else{
            PMLinear.visibility = View.VISIBLE
            DD.visibility = View.GONE
            PM.setListener(99, 0, this@ModTemplateHolder)
        }
        delete.setOnClickListener { view ->
            deleteMod.deleteMod(adapterPosition)
            updViewr.updateView()
        }
    }

    interface updView {
        fun updateView()
    }

    interface DeleteMod {
        fun deleteMod(position: Int)
    }

    interface PutModValue {
        fun putModValue(position: Int, value:Int)
    }

    override fun whenValueTo(position: Int) {
        listener.putModValue(adapterPosition, position)
    }

    override fun numberEvent(number: Int) {
        listener.putModValue(adapterPosition, position)
    }
}