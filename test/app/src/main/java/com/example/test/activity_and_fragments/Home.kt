package com.example.test.activity_and_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.activity_and_fragments.hosts.PresentHost
import com.example.test.adapters.CharacterAdapter
import com.example.test.databinding.CardInitiativeFightBinding
import com.example.test.databinding.HomeBinding
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.GameDAO
import com.example.test.viewModels.InitiativeFightVM
import com.example.test.views.HeaderView
import kotlin.properties.Delegates

class Home : Fragment(), HeaderView.HeaderBack {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mGameVM:GameDAO by activityViewModels()
    private val mInitiativeFightVM: InitiativeFightVM by activityViewModels()
    private var gameId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameId = mCharacterVM.gameId
        mInitiativeFightVM.loadList(gameId)
        mGameVM.initGameName(gameId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home, container, false)
        try {

            val binding = HomeBinding.bind(view)
            with(binding) {
                mGameVM.gameName.observe(viewLifecycleOwner) { name ->
                    HomeNameGame.text = name
                }

                editGameName.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("paramName", "Название игры")
                    bundle.putString("value", HomeNameGame.text.toString())
                    bundle.putString("groupTitle", "Название игры")
                    bundle.putInt("indexItem", -1)
                    bundle.putInt("indexParam", -1)
                    bundle.putInt("mod", 1)
                    view.findNavController().navigate(R.id.action_home2_to_edit_string2, bundle)
                }

                HomeNewCharacter.setOnClickListener {
                    view.findNavController().navigate(R.id.action_home2_to_choiceTemplate)
                }

                HomeButton.setOnClickListener {
                    (activity as PresentHost).openIniciativa(mCharacterVM.characterId)
                }

                header.setBack(true, this@Home, requireActivity(), viewLifecycleOwner)
            }

            //CharacterAdapter ищем, подключаем
            val hGridView: GridView = view.findViewById(R.id.Home_Grid)
            val adapter = CharacterAdapter()
            hGridView.adapter = adapter

            // Устанавливаем данные
            mCharacterVM.characterList.observe(viewLifecycleOwner) {
                adapter.setCharacterList(it, true)
            }

        } catch (e: Exception) {
            Toast.makeText(view.context, "$e", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun back() {
        (activity as PresentHost).backToMain()
    }

    class AdapterInitiativeFight :
        RecyclerView.Adapter<AdapterInitiativeFightTemplateHolder>() {
        var count = 1
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): AdapterInitiativeFightTemplateHolder {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_initiative_fight, parent, false)
            return AdapterInitiativeFightTemplateHolder(view)
        }

        override fun onBindViewHolder(
            holder: AdapterInitiativeFightTemplateHolder,
            position: Int
        ) {
            holder.bind(position)
        }

        override fun getItemCount(): Int {
            return count
        }

        fun setData(count: Int) {
            this.count = count
            notifyDataSetChanged()
        }

    }

    class AdapterInitiativeFightTemplateHolder(
        view: View,
    ) : RecyclerView.ViewHolder(view) {
        private val binding = CardInitiativeFightBinding.bind(view)
        fun bind(position: Int) = with(binding) {
            more.setOnClickListener {
                if (gridCharacter.visibility == View.VISIBLE) {
                    gridCharacter.visibility = View.GONE
                    more.background = it.context.resources.getDrawable(R.drawable.more)
                } else {
                    gridCharacter.visibility = View.VISIBLE
                    more.background = it.context.resources.getDrawable(R.drawable.less)
                }
            }
            delete.setOnClickListener {
                // todo: показывает диалог фрагмент с подтверждением удаления
            }
            //title.text =
            val adapter = CharacterAdapter()
            gridCharacter.adapter = adapter
            //adapter.setCharacterList(it, true)

        }

    }
    // todo подумать о связи между листом персонажей и initiativeFight. нам нужно отобразить список персонажей, который является выборкой из общего листа песонажей
    //
}
