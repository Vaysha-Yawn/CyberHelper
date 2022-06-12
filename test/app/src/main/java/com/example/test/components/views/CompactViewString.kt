package com.example.test.components.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.adapters.AbstractAdapterRV
import com.example.test.adapters.AbstractTemplateHolder
import com.example.test.databinding.CardRvCompactEditTextBinding
import com.example.test.databinding.CardRvCompactStringBinding
import com.example.test.databinding.ViewCompactBinding

class CompactViewString(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var binding: ViewCompactBinding
    private var values = mutableListOf<String?>()
    private var obj: OnClickEdit? = null
    private var hint: String = ""
    private lateinit var adapter: AbstractAdapterRV<String?>

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.view_compact, this, true)
        binding = ViewCompactBinding.bind(this)
        initAttrs(attrs, defStyleAttr, defStyleRes)
    }

    private fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CompactViewString,
            defStyleAttr,
            defStyleRes
        )

        with(binding) {

            val titleText = typedArray.getString(
                R.styleable.CompactViewString_CV_title
            )
            hint = typedArray.getString(
                R.styleable.CompactViewString_CV_item_hint
            )?:""
            val addText = typedArray.getString(
                R.styleable.CompactViewString_CV_add_text
            )

            title.text = titleText

            add.text = addText

            adapter = AbstractAdapterRV<String?>(
                object : AbstractTemplateHolder.InitBinding<String?> {
                    override fun funBinding(
                        view: View,
                        param: String?,
                        updView: AbstractTemplateHolder.UpdView,
                        pos: Int
                    ) {
                        val binding = CardRvCompactStringBinding.bind(view)
                        with(binding) {
                            delete.setOnClickListener {
                                values.removeAt(pos)
                                updView.updateView()
                            }
                            if (param != null) {
                                stringEditableText.text = param
                            }else{
                                stringEditableText.text = hint
                            }
                            stringEditableEdit.setOnClickListener {
                                if (obj != null) {
                                    obj!!.onClickEdit(pos)
                                }
                            }
                        }
                    }
                },
                R.layout.card_rv_compact_string
            )
            RV.layoutManager =
                LinearLayoutManager(RV.context, LinearLayoutManager.VERTICAL, false)
            RV.adapter = adapter

            add.setOnClickListener {
                values.add(null)
                adapter.setData(values)
            }
        }

        typedArray.recycle()

    }

    fun setData(listValues: MutableList<String?>) {
        values = listValues
        adapter.setData(values)
    }

    fun setListener(obj: OnClickEdit?) {
        this.obj = obj
    }

    fun getData(): MutableList<String?> {
        return values
    }

    interface OnClickEdit {
        fun onClickEdit(posEdit: Int)
    }


}