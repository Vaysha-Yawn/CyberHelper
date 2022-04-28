package com.example.test.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.adapters.ModAdapterRV
import com.example.test.adapters.ModTemplateHolder
import com.example.test.data_base.Mod
import com.example.test.databinding.DialogChooseAddModificationBinding
import com.example.test.viewModels.SkillTestVM
import kotlin.properties.Delegates

class Modificators : Fragment(), ModTemplateHolder.DeleteMod,
     ModDialogFragment.AddMod, ModTemplateHolder.PutModValue {

    private val mSkillVM: SkillTestVM by activityViewModels()
    private val adapter = ModAdapterRV(this,this)
    private var keyListMod by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.modificators, container, false)
        keyListMod = arguments?.getInt("keyListMod") ?: mSkillVM.createId()

        mSkillVM.mapMod[keyListMod] = MutableLiveData<MutableList<Mod>>()
        mSkillVM.mapMod[keyListMod]?.value = mutableListOf<Mod>()

        try {
            val binding = com.example.test.databinding.ModificatorsBinding.bind(view)

            fun bind() = with(binding) {
                modRV.layoutManager =
                    LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
                modRV.adapter = adapter
                mSkillVM.mapMod[keyListMod]?.observe(viewLifecycleOwner) {
                    adapter.setData(it)
                }
                addMod.setOnClickListener {
                    val dialogFragment = ModDialogFragment(this@Modificators)
                    dialogFragment.show(childFragmentManager, "chooseMod")
                }
            }
            bind()
        } catch (e: Exception) {
            Toast.makeText(view.context, "$e", Toast.LENGTH_LONG).show()
        }
        return view
    }

    override fun deleteMod(position: Int) {
        mSkillVM.mapMod[keyListMod]?.value?.removeAt(position)
    }

    override fun addMod(style: Boolean) {
        when (style) {
            true -> {
                mSkillVM.mapMod[keyListMod]?.value?.add(Mod(true, 0))
            }
            false-> {
                mSkillVM.mapMod[keyListMod]?.value?.add(Mod(false, 0))
            }
        }
        adapter.notifyItemInserted(mSkillVM.mapMod[keyListMod]?.value?.size ?: 1 - 1)
    }

    fun getListMods():MutableList<Mod>{
        return mSkillVM.mapMod[keyListMod]?.value!!
    }

    override fun putModValue(position: Int, value: Int) {
        mSkillVM.mapMod[keyListMod]?.value?.get(position)?.value = value
    }


}

class ModDialogFragment(private val addMod:AddMod) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.dialog_choose_add_modification, container, false)

        val binding = DialogChooseAddModificationBinding.bind(view)
        fun bind() = with(binding) {

            modStyleDD.setOnClickListener {
                addMod.addMod(true)
                dismiss()
            }

            modStylePM.setOnClickListener {
                addMod.addMod(false)
                dismiss()
            }
        }
        bind()

        return view
    }

    interface AddMod{
        fun addMod(style:Boolean)
    }
}

