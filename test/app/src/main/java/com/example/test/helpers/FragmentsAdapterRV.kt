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
import com.example.test.widgets.Goal
import com.example.test.widgets.Mod
import io.realm.RealmList


class FragmentsAdapterRV(
    private val list :List<String>,
    private val listFr :List<Fragment>,
    private val loadFragment: TemplateHolder.LoadFragment,
) :
    RecyclerView.Adapter<FragmentsAdapterRV.TemplateHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_fragments, parent, false)
        return TemplateHolder(view,  loadFragment)
    }

    override fun onBindViewHolder(holder: TemplateHolder, position: Int) {
        holder.bind(list[position], listFr[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class TemplateHolder(view: View, private val loadFragment: TemplateHolder.LoadFragment,) : RecyclerView.ViewHolder(view) {
        private val binding = CardFragmentsBinding.bind(view)

        fun bind(textT: String, fragment:Fragment) = with(binding) {
            text.text = textT
            val id = View.generateViewId()
            fr.id = id
            loadFragment.loadFragment(adapterPosition, id, fragment)
        }
        interface LoadFragment {
            fun loadFragment(position: Int, id: Int, fragment:Fragment)
        }
    }

}

