package com.example.test.components.views.compact_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.components.views.PlusMinusView
import com.example.test.databinding.CardRvCompactPmBinding
import com.example.test.databinding.ViewCompactBinding

class CompactViewPM(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var binding: ViewCompactBinding
    private var titleText: String = ""
    private var addText: String = ""
    private var values = mutableListOf<Int?>()
    private var obj: OnNumberEdited? = null
    private lateinit var adapter: AbstractAdapterRV<Int?>
    private var maxValue: Int? = null
    private var minValue: Int? = null


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
            R.styleable.CompactViewPM,
            defStyleAttr,
            defStyleRes
        )

        with(binding) {

            titleText = typedArray.getString(
                R.styleable.CompactViewPM_CV_PM_title
            )?:""

            addText = typedArray.getString(
                R.styleable.CompactViewPM_CV_PM_add_text
            )?:""

            title.text = titleText

            add.text = addText

            adapter = AbstractAdapterRV<Int?>(
                object : AbstractTemplateHolder.InitBinding<Int?> {
                    override fun funBinding(
                        view: View,
                        param: Int?,
                        updView: AbstractTemplateHolder.UpdView,
                        pos: Int
                    ) {
                        val binding = CardRvCompactPmBinding.bind(view)
                        with(binding) {
                            delete.setOnClickListener {
                                values.removeAt(pos)
                                updView.updateView()
                            }
                            if (param != null) {
                                PM.setValue(param)
                            }
                            PM.setListener(maxValue, minValue, object : PlusMinusView.NumberEvent {
                                override fun numberEvent(number: Int) {
                                    values[pos] = number
                                    if (obj != null) {
                                        obj!!.onNumberEdited(pos, number)
                                    }
                                }
                            })
                        }
                    }
                },
                R.layout.card_rv_compact_pm
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

    fun setData(listValues: MutableList<Int?>) {
        values = listValues
        adapter.setData(values)
    }

    fun setListener(obj: OnNumberEdited?, maxValue: Int?, minValue: Int?) {
        this.obj = obj
        this.maxValue = maxValue
        this.minValue = minValue
    }

    fun getData(): MutableList<Int?> {
        return values
    }

    fun setTitle(title:String){
        this.titleText = title
    }

    fun setAddText(addText:String){
        this.addText = addText
    }

    interface OnNumberEdited {
        fun onNumberEdited(posEdit: Int, value: Int)
    }


}