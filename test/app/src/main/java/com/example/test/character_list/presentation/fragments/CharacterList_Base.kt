package com.example.test.character_list.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.character_list.presentation.adapters.GroupAdapterRV
import com.example.test.data_base.realm.other_realm_object.GroupParam
import com.example.test.data_base.realm.character.CharacterVM
import com.example.test.data_base.realm.game.GameDAO
import com.example.test.data_base.realm.game_system.GameSystemDAO


class CharacterList_Base : Fragment() {

    private val KEY = "groupNumber"

    private val mCharacterVM: CharacterVM by activityViewModels()
    private val mGameVM: GameDAO by activityViewModels()
    private val mGameSystemVM: GameSystemDAO by activityViewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.character_list__item, container, false)

        val groupNumber = arguments?.getInt(KEY) ?: 0

        val groupRv = view.findViewById<RecyclerView>(R.id.group_rv)
        val adapterGroup = GroupAdapterRV()
        groupRv.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        groupRv.adapter = adapterGroup
        val characterId = mCharacterVM.characterId
        val gameId = mCharacterVM.gameId
        //
        val groupList = mutableListOf<GroupParam>()
        mCharacterVM.characterList.observe(viewLifecycleOwner) { list ->
            val character = list.singleOrNull { character ->
                character.id == characterId
            }

            val listNameGroup = mGameSystemVM.currentGameSystem!!.tabsToGroup[groupNumber]!!
            for (nameGroup in listNameGroup.key) {
                for (gp in character?.attributes!!)
                    if (gp.title == nameGroup) {
                        groupList.add(gp)
                    }
            }
        }

        val r = view.context.getSharedPreferences("id", 0).getString("newGameId", "0")!!.toInt()
        val newOrPres = gameId == r
        adapterGroup.setData(groupList, newOrPres)
        return view
    }

    fun getFragment(groupNumber: Int): CharacterList_Base {
        val bundle = Bundle()
        bundle.putInt(KEY, groupNumber)
        return CharacterList_Base().apply {
            arguments = bundle
        }
    }
}