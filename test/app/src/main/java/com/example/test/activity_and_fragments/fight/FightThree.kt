package com.example.test.activity_and_fragments.fight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.data_base.FightType
import com.example.test.databinding.FightThreeBinding
import com.example.test.helpers.FragmentsAdapterRV
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.SkillTestVM
import com.example.test.widgets.*

class FightThree : Fragment(), FragmentsAdapterRV.TemplateHolder.LoadFragment {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mSkillVM: SkillTestVM by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fight_three, container, false)
        val fightType = mSkillVM.attack?.fightType?: FightType()

        fun loadDD(main:String, them:String, goal:String, list:ArrayList<String>, id:Int){
            val fragment = DropDownList()
            val bundle = Bundle()
            bundle.putString("main", main)
            bundle.putString("them", them)
            bundle.putString("goal", goal)
            bundle.putStringArrayList("list", list)
            fragment.arguments = bundle
            childFragmentManager.commit {
                replace(id, fragment)
                addToBackStack(null)
            }
        }

        fun loadFragmentLight(fragment:Fragment, id:Int){
            childFragmentManager.commit {
                replace(id, fragment)
                addToBackStack(null)
            }
        }

        fun loadPM(value:Int, minValue:Int, maxValue:Int?, resId:Int){
            val bundleD = Bundle()
            bundleD.putInt("value", value)
            bundleD.putInt("minValue", minValue)
            if (maxValue!=null){
                bundleD.putInt("maxValue", maxValue)
            }
            bundleD.putString("them", "green")
            bundleD.putString("goal", "")
            val fragmentD = PlusAndMinus()
            fragmentD.arguments = bundleD
            childFragmentManager.commit {
                replace(resId, fragmentD)
                addToBackStack(null)
            }
        }

        fun setVisibility(view:View, visible:Boolean){
            if (visible){
                view.visibility = View.VISIBLE
            }else{
                view.visibility = View.GONE
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
            loadFragmentLight(Header(), R.id.header)
            val adapter = FragmentsAdapterRV(list, listFr, this@FightThree)
            RV.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            RV.adapter = adapter

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


}