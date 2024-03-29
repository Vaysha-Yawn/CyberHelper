package com.example.test.present_game.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.present_game.presentation.activity.PresentHost
import com.example.test.characters_grid.CharacterAdapter
import com.example.test.data_base.realm.character.Character
import com.example.test.data_base.realm.iniciative_fight.InitiativeFight
import com.example.test.databinding.CardInitiativeFightBinding
import com.example.test.databinding.HomeBinding
import com.example.test.data_base.realm.character.CharacterVM
import com.example.test.data_base.realm.game.GameDAO
import com.example.test.iniciative.presentation.view_model.InitiativeFightVM
import com.example.test.components.views.HeaderView

class Home : Fragment(), HeaderView.HeaderBack,
    AdapterInitiativeFightTemplateHolder.DeleteInitiativeFight {

    private val mCharacterVM: CharacterVM by activityViewModels()
    private val mGameVM: GameDAO by activityViewModels()
    private val mInitiativeFightVM: InitiativeFightVM by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home, container, false)
        val adapterFight = AdapterInitiativeFight(this)

        val gameId = mCharacterVM.gameId
        mInitiativeFightVM.loadList(gameId)
        mGameVM.initGameName(gameId)

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
                    view.findNavController().navigate(
                        R.id.action_home2_to_choiceTemplate,
                        bundleOf("newOrPres" to false)
                    )
                }

                HomeButton.setOnClickListener {
                    (activity as PresentHost).openIniciativa(mCharacterVM.characterId)
                }

                header.setBack(true, this@Home, requireActivity(), viewLifecycleOwner)

                InitiativeFightsRV.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                InitiativeFightsRV.adapter = adapterFight
            }

            //CharacterAdapter ищем, подключаем
            val hGridView: GridView = view.findViewById(R.id.Home_Grid)
            val adapter = CharacterAdapter()
            hGridView.adapter = adapter

            // Устанавливаем данные
            mCharacterVM.characterList.observe(viewLifecycleOwner) { listCharacter ->
                adapter.setCharacterList(listCharacter, true, null)
                mInitiativeFightVM.fightList.observe(viewLifecycleOwner) { listFight ->
                    adapterFight.setData(
                        listFight, mInitiativeFightVM.findFightCharacter(listFight, listCharacter)
                    )
                }
            }

        } catch (e: Exception) {
            Toast.makeText(view.context, "$e", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun back() {
        (activity as PresentHost).backToMain()
    }

    override fun deleteInitiativeFight(position:Int, name:String) {
        val bundle = Bundle()
        val id = mInitiativeFightVM.fightList.value?.get(position)?.id!!
        bundle.putInt("id", id)
        bundle.putInt("gameId", mCharacterVM.gameId)
        bundle.putString("param", "InitiativeFight")
        bundle.putString("key", "бой $name")
        view?.findNavController()
            ?.navigate(R.id.action_home2_to_pres_delete, bundle)
    }


}
class AdapterInitiativeFight(private val del: AdapterInitiativeFightTemplateHolder.DeleteInitiativeFight) :
    RecyclerView.Adapter<AdapterInitiativeFightTemplateHolder>() {

    var list = mutableListOf<InitiativeFight>()
    var map = mutableMapOf<Int, MutableList<Character>>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterInitiativeFightTemplateHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_initiative_fight, parent, false)
        return AdapterInitiativeFightTemplateHolder(view, del)
    }

    override fun onBindViewHolder(
        holder: AdapterInitiativeFightTemplateHolder,
        position: Int
    ) {
        holder.bind(list[position], map.values.toList()[position])
    }

    override fun getItemCount(): Int {
        return map.size
    }

    fun setData(list: MutableList<InitiativeFight>, map:MutableMap<Int, MutableList<Character>>) {
        this.map = map
        this.list = list
        notifyDataSetChanged()
    }

}

class AdapterInitiativeFightTemplateHolder(
    view: View, private val del: DeleteInitiativeFight
) : RecyclerView.ViewHolder(view) {
    private val binding = CardInitiativeFightBinding.bind(view)
    fun bind(InitiativeFight: InitiativeFight, listCharacter:List<Character>) = with(binding) {
        more.setOnClickListener {
            if (linLay.visibility == View.VISIBLE) {
                linLay.visibility = View.GONE
                val draw = it.context.resources.getDrawable(R.drawable.more)
                more.background = draw
            } else {
                linLay.visibility = View.VISIBLE
                more.background = it.context.resources.getDrawable(R.drawable.less)
            }
        }
        delete.setOnClickListener {
            del.deleteInitiativeFight(adapterPosition, InitiativeFight.nameFight)
        }
        title.text = InitiativeFight.nameFight
        val adapter = CharacterAdapter()
        gridCharacter.adapter = adapter

        adapter.setCharacterList(listCharacter, true, InitiativeFight.id)
    }
    interface DeleteInitiativeFight{
        fun deleteInitiativeFight(position: Int, name:String)
    }
}
