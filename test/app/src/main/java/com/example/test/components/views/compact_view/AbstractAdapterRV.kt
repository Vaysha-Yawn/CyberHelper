package com.example.test.components.views.compact_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class AbstractAdapterRV<T>(
    val funBinding: AbstractTemplateHolder.InitBinding<T>,
    val layout: Int
) :
    RecyclerView.Adapter<AbstractTemplateHolder<T>>(), AbstractTemplateHolder.UpdView {
    var list: MutableList<T> = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractTemplateHolder<T> {
        val view =
            LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return AbstractTemplateHolder<T>(view, this, funBinding)
    }

    override fun onBindViewHolder(holder: AbstractTemplateHolder<T>, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(list: MutableList<T>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun updateView() {
        notifyDataSetChanged()
    }

}

class AbstractTemplateHolder<T>(
    private val view: View,
    private val updView: UpdView,
    private val funBinding: InitBinding<T>,
) : RecyclerView.ViewHolder(view) {

    fun bind(param: T, pos:Int) {
        funBinding.funBinding(view, param, updView, pos)
    }

    interface UpdView {
        fun updateView()
    }

    interface InitBinding<T> {
        fun funBinding(view: View, param: T, updView: UpdView, pos:Int)
    }

}

// необходимо сделать абстрактый адаптер и холдер для него, чтобы по одним параметрам создавать какой хочешь
// параметры которые мы хотим передать:
// разные лайауты и биндинги
// рзные действия с ними
// добавить изменения конкретного элемента
