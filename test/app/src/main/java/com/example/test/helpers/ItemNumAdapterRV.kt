package com.example.test.helpers


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data_base.ParamNum
import com.example.test.databinding.CardNumBinding
import com.example.test.databinding.CardStringBinding
import io.realm.RealmList


class ItemNumAdapterRV():
    RecyclerView.Adapter<ItemNumAdapterRV.TemplateHolder>() {

    private var list = listOf<ParamNum>()

    class TemplateHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CardNumBinding.bind(view)
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(param: ParamNum) = with(binding){
            name.text = param.name
            num.text = param.value.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_num, parent, false)
        return TemplateHolder(view)
    }

    override fun onBindViewHolder(holder: TemplateHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(list: RealmList<ParamNum> ){
        this.list = list
        notifyDataSetChanged()
    }
}