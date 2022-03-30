package com.example.test.helpers


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data_base.*
import com.example.test.databinding.CardFragmentsBinding
import com.example.test.databinding.CardItemBinding
import com.example.test.databinding.CardModBinding
import com.example.test.databinding.CardRadioBinding
import com.example.test.widgets.Goal
import com.example.test.widgets.Mod
import io.realm.RealmList


class RadioAdapterRV() :
    RecyclerView.Adapter<RadioAdapterRV.TemplateHolder>() {

    var list = listOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_radio, parent, false)
        return TemplateHolder(view)
    }

    override fun onBindViewHolder(holder: TemplateHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class TemplateHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CardRadioBinding.bind(view)

        fun bind(id: Int) = with(binding) {
            radioButton.id = id
        }
    }

    fun setData(list: List<Int>){
        this.list = list
        notifyDataSetChanged()
    }
}

