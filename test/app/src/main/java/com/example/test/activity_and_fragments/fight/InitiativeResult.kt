package com.example.test.activity_and_fragments.fight

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.activity_and_fragments.hosts.FightHost
import com.example.test.data_base.Goal
import com.example.test.databinding.CardIniciativaBinding
import com.example.test.databinding.IniciativaResultBinding
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.InitiativeFightVM
import com.example.test.viewModels.SkillTestVM
import com.example.test.views.HeaderView
import io.realm.RealmList


private var list = mutableListOf<String>()

class InitiativeResult : Fragment(), HeaderView.HeaderBack {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mSkillVM: SkillTestVM by activityViewModels()
    private lateinit var mInitiativeFightVM: InitiativeFightVM

    private lateinit var listGoal: MutableList<Goal>
    private var listMore = mutableListOf<String>()
    private lateinit var nameFight: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mInitiativeFightVM = ViewModelProvider(this)[InitiativeFightVM::class.java]
        nameFight = requireArguments().get("nameFight") as String
        list = mutableListOf<String>()
        listGoal = requireArguments().get("listGoal") as MutableList<Goal>
        for (i in listGoal) {
            list.add(i.name)
        }
        listMore = requireArguments().get("listMore") as MutableList<String>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.iniciativa_result, container, false)

        val binding = IniciativaResultBinding.bind(view)
        fun bind() = with(binding) {
            header.setBack(true, this@InitiativeResult, requireActivity(), viewLifecycleOwner)
            resultRv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val adapter = AdapterInitiative()
            resultRv.adapter = adapter
            adapter.setData(list)

            val adapterList = ArrayAdapter(requireContext(), R.layout.more, listMore)
            moreRV.adapter = adapterList

            more.setOnClickListener {
                if (moreRV.visibility == View.VISIBLE) {
                    moreRV.visibility = View.GONE
                } else {
                    moreRV.visibility = View.VISIBLE
                }
            }

            apply.setOnClickListener {
                val listId = RealmList<Int>()
                for (i in listGoal){
                    listId.add(i.characterId)
                }
                mInitiativeFightVM.addInitiativeFight(mCharacterVM.gameId, nameFight, listId)
                (activity as FightHost).backToHome()
            }
        }
        bind()

        return view
    }

    override fun back() {
        (activity as FightHost).backToHome()
    }

    class AdapterInitiative(
    ) :
        RecyclerView.Adapter<AdapterInitiativeTemplateHolder>() {
        var list = mutableListOf<String>()
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): AdapterInitiativeTemplateHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.card_iniciativa, parent, false)
            return AdapterInitiativeTemplateHolder(view)
        }

        override fun onBindViewHolder(
            holder: AdapterInitiativeTemplateHolder,
            position: Int
        ) {
            holder.bind(position)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        fun setData(list: MutableList<String>) {
            this.list = list
            notifyDataSetChanged()
        }

    }

    class AdapterInitiativeTemplateHolder(
        view: View,
    ) : RecyclerView.ViewHolder(view) {
        private val binding = CardIniciativaBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(position: Int) = with(binding) {
            num.text = (position + 1).toString()
            name.text = list[position]
        }
    }

}
