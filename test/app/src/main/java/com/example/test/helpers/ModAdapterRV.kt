package com.example.test.helpers


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.databinding.*


class ModAdapterRV(
    private val list:MutableList<Boolean>,
    private val loadFragment: ModTemplateHolder.LoadFragment
) :
    RecyclerView.Adapter<ModTemplateHolder>(), ModTemplateHolder.DeleteMod {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModTemplateHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_mod_drop_down, parent, false)
        return ModTemplateHolder(view, this, loadFragment)
    }

    override fun onBindViewHolder(holder: ModTemplateHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addMod(style:Boolean) {
        list.add(style)
        notifyDataSetChanged()
    }

    override fun deleteMod(position: Int){
        list.removeAt(position)
        notifyDataSetChanged()
    }
}

class ModTemplateHolder(view: View, private val deleteMod: DeleteMod,  private val loadFragment: LoadFragment) : RecyclerView.ViewHolder(view) {
    private val binding = CardModDropDownBinding.bind(view)

    fun bind(style: Boolean) = with(binding) {


        loadFragment.loadFragment(adapterPosition, style)
        delete.setOnClickListener { view ->
            deleteMod.deleteMod(adapterPosition)
        }

    }
    interface DeleteMod {
        fun deleteMod(position: Int)
    }
    interface LoadFragment {
        fun loadFragment(position: Int, style: Boolean)
    }
}