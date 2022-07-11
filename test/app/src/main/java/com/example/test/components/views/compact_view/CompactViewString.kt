package com.example.test.components.views.compact_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.databinding.CardRvCompactStringBinding
import com.example.test.databinding.ViewCompactBinding

class CompactViewString(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var binding: ViewCompactBinding
    private var values = mutableListOf<String?>()
    private var objEdit: OnClickEdit? = null
    private var objAdd: OnClickAdd? = null
    private var objDel: OnClickDel? = null
    private var hint: String = ""
    private var titleText: String = ""
    private var addText: String = ""
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

             titleText = typedArray.getString(
                R.styleable.CompactViewString_CV_String_title
            )?:""
            hint = typedArray.getString(
                R.styleable.CompactViewString_CV_String_item_hint
            )?:""
             addText = typedArray.getString(
                R.styleable.CompactViewString_CV_String_add_text
            )?:""

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
                                if (objDel != null) {
                                    objDel!!.onClickDel(pos)
                                }
                                values.removeAt(pos)
                                updView.updateView()
                            }
                            if (param != null) {
                                stringEditableText.text = param
                            }else{
                                stringEditableText.text = hint
                            }
                            stringEditableEdit.setOnClickListener {
                                if (objEdit != null) {
                                    objEdit!!.onClickEdit(pos)
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
            adapter.setData(values)

            add.setOnClickListener {
                values.add(null)
                if (objAdd != null) {
                    objAdd!!.onClickAdd()
                }
                adapter.setData(values)
            }
        }

        typedArray.recycle()

    }

    fun setData(listValues: MutableList<String?>) {
        values = listValues
        adapter.setData(values)
    }

    fun setListener(objEdit: OnClickEdit?, objAdd: OnClickAdd?, objDel: OnClickDel?) {
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

    interface OnClickEdit {
        fun onClickEdit(posEdit: Int)
    }

    interface OnClickAdd {
        fun onClickAdd()
    }

    interface OnClickDel {
        fun onClickDel( pos:Int)
    }

    fun getData(): MutableList<String?> {
        return values
    }

}