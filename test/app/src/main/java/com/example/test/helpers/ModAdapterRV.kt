package com.example.test.helpers


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.databinding.CardModDropDownBinding
import com.example.test.widgets.Mod


class ModAdapterRV(
    private val deleteMod: ModTemplateHolder.DeleteMod,
    private val loadFragment: ModTemplateHolder.LoadFragment,
    private val updIdMode: ModTemplateHolder.updIdMod,
) :
    RecyclerView.Adapter<ModTemplateHolder>(), ModTemplateHolder.updView {

    var list = mutableListOf<Mod>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModTemplateHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_mod_drop_down, parent, false)
        return ModTemplateHolder(view, deleteMod, loadFragment, this, updIdMode)
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
    private val loadFragment: LoadFragment,
    private val updViewr: updView,
    private val updIdMode: updIdMod,
) : RecyclerView.ViewHolder(view) {
    private val binding = CardModDropDownBinding.bind(view)

    fun bind(mod: Mod) = with(binding) {
        try {
            val id =if(mod.resId == 0){
                 View.generateViewId()
            }else{
                mod.resId
            }
            fr.id = id
            updIdMode.updIdMod(adapterPosition, id)
            loadFragment.loadFragment(adapterPosition, mod.style, mod.value, id)
            delete.setOnClickListener { view ->
                deleteMod.deleteMod(adapterPosition)
                updViewr.updateView()
            }
            Toast.makeText(
                delete.context,
                "This bind value ${mod.value}, position $adapterPosition, resId ${mod.resId},!",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            Toast.makeText(delete.context, "$e", Toast.LENGTH_LONG).show()
        }
    }

    interface updView {
        fun updateView()
    }

    interface DeleteMod {
        fun deleteMod(position: Int)
    }

    interface LoadFragment {
        fun loadFragment(position: Int, style: Boolean, value: Int, id: Int)
    }

    interface updIdMod {
        fun updIdMod(position: Int, id: Int)
    }
}