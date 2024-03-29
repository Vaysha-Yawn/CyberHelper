package com.example.test.components.few_roll.roll.modificators


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data_base.data_sample.test_data.DSpecialGameData
import com.example.test.data_base.realm.other_realm_object.Mod
import com.example.test.databinding.CardModBinding
import com.example.test.components.views.PlusMinusView
import com.example.test.components.views.drop_down.DropDownAdapterRV


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

    fun bind(mod: Mod) = with(binding) {
        if (mod.style){
            PMLinear.visibility = View.GONE
            DD.visibility = View.VISIBLE
            DD.setMainText(DSpecialGameData().modName[mod.value])
            DD.setDDArrayAndListener(DSpecialGameData().modName, this@ModTemplateHolder, null)
        }else{
            PMLinear.visibility = View.VISIBLE
            DD.visibility = View.GONE
            PM.setValue(mod.value)
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
        listener.putModValue(adapterPosition, number)
    }
}