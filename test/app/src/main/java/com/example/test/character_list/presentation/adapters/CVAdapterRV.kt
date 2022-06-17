package com.example.test.character_list.presentation.adapters


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.components.views.CompactViewEdit
import com.example.test.components.views.CompactViewString
import com.example.test.data_base.ParamNum
import com.example.test.databinding.CardCvEditBinding
import io.realm.RealmList


class CVAdapterRV :
    RecyclerView.Adapter<CVAdapterRV.TemplateHolder>() {
    private var type = false
    private var list = mutableListOf<MutableList<String?>>()
    private var listTitle = mutableListOf<String>()
    private var objEdit: CompactViewEdit.OnStringEdited? = null
    private var objStrAdd: CompactViewString.OnClickAdd? = null
    private var objStrDel: CompactViewString.OnClickDel? = null
    private var objStrEdit: CompactViewString.OnClickEdit? = null

    private var hint: String = ""
    private var addText: String = ""

    class TemplateHolder(
        private val view: View,
        private val type: Boolean,
        private var objEdit: CompactViewEdit.OnStringEdited? = null,
        private var objStrAdd: CompactViewString.OnClickAdd? = null,
        private var objStrDel: CompactViewString.OnClickDel? = null,
        private var objStrEdit: CompactViewString.OnClickEdit? = null,
        private var hint: String,
        private var addText: String
    ) :
        RecyclerView.ViewHolder(view) {
        fun bind(title: String, list: MutableList<String?>) {
            when (type) {
                true -> {
                    val binding = com.example.test.databinding.CardCvStrBinding.bind(view)
                    with(binding) {
                        str.setTitle(title)
                        str.setData(list)
                        str.setListener(objStrEdit, objStrAdd, objStrDel)
                        str.setHint(hint)
                        str.setAddText(addText)
                    }
                }
                false -> {
                    val binding = CardCvEditBinding.bind(view)
                    with(binding) {
                        edit.setTitle(title)
                        edit.setData(list)
                        edit.setListener(objEdit)
                        edit.setHint(hint)
                        edit.setAddText(addText)
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateHolder {
        val view = when (type) {
            true -> {
                LayoutInflater.from(parent.context).inflate(R.layout.card_cv_str, parent, false)
            }
            false -> {
                LayoutInflater.from(parent.context).inflate(R.layout.card_cv_edit, parent, false)
            }
        }
        return TemplateHolder(
            view, type, objEdit,
            objStrAdd,
            objStrDel,
            objStrEdit,
            hint, addText
        )
    }

    override fun onBindViewHolder(holder: TemplateHolder, position: Int) {
        holder.bind(listTitle[position], list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(
        list: MutableList<MutableList<String?>>, type: Boolean, listTitle: MutableList<String>,
        objEdit: CompactViewEdit.OnStringEdited? = null,
        objStrAdd: CompactViewString.OnClickAdd? = null,
        objStrDel: CompactViewString.OnClickDel? = null,
        objStrEdit: CompactViewString.OnClickEdit? = null,
        addText: String, hint: String
    ) {
        this.addText = addText
        this.hint = hint

        this.type = type
        this.list = list
        this.listTitle = listTitle

        this.objEdit = objEdit
        this.objStrAdd = objStrAdd
        this.objStrDel = objStrDel
        this.objStrEdit = objStrEdit
        notifyDataSetChanged()
    }

    interface OnEdit{
        fun onEdit(){

        }
    }
}