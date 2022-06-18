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
    private var titleText: String = ""
    private var addText: String = ""
    private var values = mutableListOf<String?>()
    private var objEdit: OnClickEdit? = null
    private var objAdd: OnClickAdd? = null
    private var objDel: OnClickDel? = null

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

             titleText = typedArray.getString(
                R.styleable.CompactViewEdit_CV_edit_title
            )?: ""

             addText = typedArray.getString(
                R.styleable.CompactViewEdit_CV_edit_add_text
            )?: ""

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
                                if (objDel != null) {
                                    objDel!!.onClickDel(pos)
                                }
                                updView.updateView()
                            }
                            if (param != null) {
                                (editText as TextView).text = param
                            }
                            editText.doOnTextChanged { text, start, before, count ->
                                values[pos] = text.toString()
                                if (objEdit != null) {
                                    objEdit!!.onClickEdit(pos, text.toString())
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
                if (objAdd != null) {
                    objAdd!!.onClickAdd()
                }
                adapterEdit.setData(values)
            }
        }

        typedArray.recycle()

    }

    fun setData(listValues: MutableList<String?>) {
        values = listValues
        adapterEdit.setData(values)
    }

    fun setListener(objEdit: OnClickEdit?=null, objAdd: OnClickAdd? = null, objDel: OnClickDel?=null) {
        this.objEdit = objEdit
        this.objAdd = objAdd
        this.objDel = objDel
    }

    fun setTitle(titles:String){
        this.titleText = titles
        with(binding) {
            title.text = titleText
        }
    }

    fun setAddText(addText:String){
        this.addText = addText
        with(binding) {
            add.text = addText
        }
    }

    fun setHint(hints:String){
        this.hint = hints
    }

    fun getData(): MutableList<String?> {
        return values
    }

    interface OnClickEdit {
        fun onClickEdit(posEdit: Int, text: String,)
    }

    interface OnClickAdd {
        fun onClickAdd()
    }

    interface OnClickDel {
        fun onClickDel( pos:Int)
    }


}