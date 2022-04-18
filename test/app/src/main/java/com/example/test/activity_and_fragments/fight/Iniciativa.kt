package com.example.test.activity_and_fragments.fight

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.activity_and_fragments.hosts.FightHost
import com.example.test.databinding.FightThreeBinding
import com.example.test.databinding.IniciativaBinding
import com.example.test.helpers.FragmentsAdapterRV
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.SkillTestVM
import com.example.test.widgets.FewRoll
import com.example.test.widgets.Header
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.set

class Iniciativa : Fragment(), FragmentsAdapterRV.TemplateHolder.LoadFragment, Header.HeaderBack {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mSkillVM: SkillTestVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.iniciativa, container, false)

        fun loadFragmentLight(fragment: Fragment, id: Int) {
            childFragmentManager.commit {
                replace(id, fragment)
                addToBackStack(null)
            }
        }

        val list = mutableListOf<String>()
        val listFr = mutableListOf<Fragment>()

        mSkillVM.mapGoalMap[0] = MutableLiveData()
        mSkillVM.mapGoalMap[0]?.value = mutableMapOf()
        val fragment = FewRoll()
        val bundle = Bundle()
        bundle.putString("goal", "goal")
        bundle.putInt("keyFragment", 0)
        fragment.arguments = bundle
        list.add( "" )
        listFr.add(fragment)

        val binding = IniciativaBinding.bind(view)
        fun bind() = with(binding) {
            loadFragmentLight(Header(this@Iniciativa), R.id.header)
            title.text = "Проверка инициативы"
            val adapter = FragmentsAdapterRV(list, listFr, this@Iniciativa)
            RV.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            RV.adapter = adapter

            btnNext.setOnClickListener {
                if (edit.text.toString() != ""){
                    //view.findNavController().navigate(R.id.action_fightThree_to_fightResult2)
                }else{
                    Toast.makeText(requireContext(), "Пожалуйста, введите название боя", Toast.LENGTH_SHORT).show()
                }
            }
        }
        bind()

        return view
    }

    override fun loadFragment(position: Int, id: Int, fragment: Fragment) {
        childFragmentManager.commit {
            replace(id, fragment)
            addToBackStack(null)
        }
    }

    override fun back() {
        try {
            (activity as FightHost).backToMain()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "$e", Toast.LENGTH_LONG).show()
            Log.e("A", "$e")
        }

    }

}