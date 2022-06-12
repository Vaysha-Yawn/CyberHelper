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
import com.example.test.databinding.ViewCompactBinding

class CompactViewEdit(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var binding: ViewCompactBinding
    private var hint: String = ""
    private var values = mutableListOf<String?>()
    private var obj: OnStringEdited? = null
    private lateinit var adapterEdit: AbstractAdapterRV<String?>

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
            R.styleable.CompactViewEdit,
            defStyleAttr,
            defStyleRes
        )

        with(binding) {

            hint = typedArray.getString(
                R.styleable.CompactViewEdit_CV_edit_item_hint
            ) ?: ""

            val titleText = typedArray.getString(
                R.styleable.CompactViewEdit_CV_edit_title
            )

            val addText = typedArray.getString(
                R.styleable.CompactViewEdit_CV_edit_add_text
            )

            title.text = titleText

            add.text = addText

            adapterEdit = AbstractAdapterRV<String?>(
                object : AbstractTemplateHolder.InitBinding<String?> {
                    override fun funBinding(
                        view: View,
                        param: String?,
                        updView: AbstractTemplateHolder.UpdView,
                        pos: Int
                    ) {
                        val binding = CardRvCompactEditTextBinding.bind(view)
                        with(binding) {
                            editText.hint = hint
                            delete.setOnClickListener {
                                values.removeAt(pos)
                                updView.updateView()
                            }
                            if (param != null) {
                                (editText as TextView).text = param
                            }
                            editText.doOnTextChanged { text, start, before, count ->
                                values[pos] = text.toString()
                                if (obj != null) {
                                    obj!!.onStringEdited(pos, text.toString(), start, before, count)
                                }
                            }
                        }
                    }
                },
                R.layout.card_rv_compact_edit_text
            )
            RV.layoutManager =
                LinearLayoutManager(RV.context, LinearLayoutManager.VERTICAL, false)
            RV.adapter = adapterEdit

            add.setOnClickListener {
                values.add(null)
                adapterEdit.setData(values)
            }
        }

        typedArray.recycle()

    }

    fun setData(listValues: MutableList<String?>) {
        values = listValues
        adapterEdit.setData(values)
    }

    fun setListener(obj: OnStringEdited?) {
        this.obj = obj
    }

    fun getData(): MutableList<String?> {
        return values
    }

    interface OnStringEdited {
        fun onStringEdited(posEdit: Int, text: String, start: Int, before: Int, count: Int)
    }


}