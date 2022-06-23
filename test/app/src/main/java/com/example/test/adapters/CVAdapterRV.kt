package com.example.test.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.components.views.CompactViewEdit
import com.example.test.components.views.CompactViewString
import com.example.test.databinding.CardCvEditBinding


class CVAdapterRV(private var addText: String, private var hint: String) :
    RecyclerView.Adapter<CVAdapterRV.TemplateHolder>() {

    private var type = false

    private var map = mutableMapOf<String, MutableList<String?>>()

    private var objEdit: OnEdit? = null
    private var objStrAdd: OnStringAdd? = null
    private var objStrDel: OnStringDel? = null
    private var objStrEdit: OnStringEdit? = null


    class TemplateHolder(
        private val view: View,
        private val type: Boolean,
        private var objEdit: OnEdit? = null,
        private var objStrAdd: OnStringAdd? = null,
        private var objStrDel: OnStringDel? = null,
        private var objStrEdit: OnStringEdit? = null,
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
                                    objStrEdit!!.onEdit(adapterPosition, posEdit,title)
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
                                    objEdit!!.onEdit(adapterPosition, posEdit, text, title)
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
        val pair = map.toList()[position]
        holder.bind(pair.first, pair.second)
    }

    override fun getItemCount(): Int {
        return map.toList().size
    }

    fun setData(
        map: MutableMap<String, MutableList<String?>>
    ) {
        this.map = map

        notifyDataSetChanged()
    }

    fun setStrListener(
        objStrAdd: OnStringAdd? = null,
        objStrDel: OnStringDel? = null,
        objStrEdit: OnStringEdit? = null,
    ) {
        type = true
        this.objStrAdd = objStrAdd
        this.objStrDel = objStrDel
        this.objStrEdit = objStrEdit
        notifyDataSetChanged()
    }

    fun setEditListener(objEdit: OnEdit? = null) {
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