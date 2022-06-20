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


class CVAdapterRV(private var addText: String, private var hint: String) :
    RecyclerView.Adapter<CVAdapterRV.TemplateHolder>() {

    private var type = false

    private var list = mutableListOf<MutableList<String?>>()
    private var listTitle = mutableListOf<String>()

    private var objEdit: CVAdapterRV.OnEdit? = null
    private var objStrAdd: CVAdapterRV.OnStringAdd? = null
    private var objStrDel: CVAdapterRV.OnStringDel? = null
    private var objStrEdit: CVAdapterRV.OnStringEdit? = null


    class TemplateHolder(
        private val view: View,
        private val type: Boolean,
        private var objEdit: CVAdapterRV.OnEdit? = null,
        private var objStrAdd: CVAdapterRV.OnStringAdd? = null,
        private var objStrDel: CVAdapterRV.OnStringDel? = null,
        private var objStrEdit: CVAdapterRV.OnStringEdit? = null,
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
                        str.setListener(object : CompactViewString.OnClickEdit {
                            override fun onClickEdit(posEdit: Int) {
                                if (objStrEdit != null) {
                                    objStrEdit!!.onEdit(adapterPosition, posEdit, title)
                                }
                            }
                        },
                            object : CompactViewString.OnClickAdd {
                                override fun onClickAdd() {
                                    if (objStrAdd != null) {
                                        objStrAdd!!.onAdd(adapterPosition, title)
                                    }
                                }
                            },
                            object : CompactViewString.OnClickDel {
                                override fun onClickDel(pos: Int) {
                                    if (objStrDel != null) {
                                        objStrDel!!.onDel(adapterPosition, pos, title)
                                    }
                                }
                            })
                        str.setHint(hint)
                        str.setAddText(addText)
                    }
                }
                false -> {
                    val binding = CardCvEditBinding.bind(view)
                    with(binding) {
                        edit.setTitle(title)
                        edit.setData(list)
                        edit.setListener(//TODO добавить ADD DEL
                            object : CompactViewEdit.OnClickEdit {
                            override fun onClickEdit(
                                posEdit: Int,
                                text: String,
                            ) {
                                if (objEdit != null) {
                                    objEdit!!.onEdit(adapterPosition, posEdit, title, text)
                                }
                            }
                        })
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
        list: MutableList<MutableList<String?>>,
        listTitle: MutableList<String>,
    ) {
        this.list = list
        this.listTitle = listTitle

        notifyDataSetChanged()
    }

    fun setStrListener(
        objStrAdd: CVAdapterRV.OnStringAdd? = null,
        objStrDel: CVAdapterRV.OnStringDel? = null,
        objStrEdit: CVAdapterRV.OnStringEdit? = null,
    ) {
        type = true
        this.objStrAdd = objStrAdd
        this.objStrDel = objStrDel
        this.objStrEdit = objStrEdit
        notifyDataSetChanged()
    }

    fun setEditListener(objEdit: CVAdapterRV.OnEdit? = null) {
        type = false
        this.objEdit = objEdit
        notifyDataSetChanged()
    }

    interface OnEdit {
        fun onEdit(adapterPos: Int, editPos: Int, title: String, text: String)
    }

    interface OnStringEdit {
        fun onEdit(adapterPos: Int, editPos: Int, title: String)
    }

    interface OnStringAdd {
        fun onAdd(adapterPos: Int, title: String)
    }

    interface OnStringDel {
        fun onDel(adapterPos: Int, editPos: Int, title: String)
    }
}