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

    private var list = mutableListOf<MutableList<String?>>()
    private var listTitle = mutableListOf<String>()

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
                                    objStrEdit!!.onEdit(adapterPosition, posEdit,)
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
                                        objStrDel!!.onDel(adapterPosition, pos, )
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
                                    objEdit!!.onEdit(adapterPosition, posEdit, text)
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
        fun onEdit(adapterPos: Int, editPos: Int, idGroup: Int, text: String)
    }

    interface OnStringEdit {
        fun onEdit(adapterPos: Int, editPos: Int, idGroup: Int)
    }

    interface OnStringAdd {
        fun onAdd(adapterPos: Int, idGroup: Int)
    }

    interface OnStringDel {
        fun onDel(adapterPos: Int, editPos: Int, idGroup: Int)
    }
}