package com.example.test.activity_and_fragments.fight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.data_base.FightType
import com.example.test.databinding.FightThreeBinding
import com.example.test.helpers.FragmentsAdapterRV
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.SkillTestVM
import com.example.test.widgets.Header
import com.example.test.widgets.PlusAndMinus
import com.example.test.widgets.Roll

class FightThree : Fragment(), FragmentsAdapterRV.TemplateHolder.LoadFragment, Header.HeaderBack {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mSkillVM: SkillTestVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fight_three, container, false)
        val fightType = mSkillVM.attack?.fightType ?: FightType()

        fun loadFragmentLight(fragment:Fragment, id:Int){
            childFragmentManager.commit {
                replace(id, fragment)
                addToBackStack(null)
            }
        }

        val list = mutableListOf<String>()
        val listFr = mutableListOf<Fragment>()

        when(fightType.difficult){
            "one roll" -> {
                val fragment = Roll()
                list.add("")
                listFr.add(fragment)
            }
            "few roll" -> {
                // VP2
                val fragment = Roll()
                list.add("")
                listFr.add(fragment)
            }
            "arbitrary number" -> {
                val bundleD = Bundle()
                bundleD.putInt("value", 0)
                bundleD.putInt("minValue", 0)
                bundleD.putString("them", "green")
                val fragmentD = PlusAndMinus()
                fragmentD.arguments = bundleD
                list.add("")
                listFr.add(fragmentD)
            }
            "DD by list" -> {

                //loadDD(main:String, "green", goal:String, list:ArrayList<String>, resId:Int)
            }
            "DD by param" -> {

                //loadDD(main:String, "green", goal:String, list:ArrayList<String>, resId:Int)
            }
        }

        val binding = FightThreeBinding.bind(view)
        fun bind() = with(binding) {
            loadFragmentLight(Header(this@FightThree), R.id.header)
            val adapter = FragmentsAdapterRV(list, listFr, this@FightThree)
            RV.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            RV.adapter = adapter
            btnNext.setOnClickListener {
                view.findNavController().navigate(R.id.action_fightThree_to_fightResult2)
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
        view?.findNavController()?.popBackStack()
    }

}