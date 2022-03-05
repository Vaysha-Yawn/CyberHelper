package com.example.test.activity_and_fragments.character_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data_base.GroupParam
import com.example.test.data_base.TemplateCharacter
import com.example.test.helpers.GroupAdapterRV
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.GameDAO

class CharacterList_Stuff : Fragment() {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mGameVM: GameDAO by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.character_list__stuff, container, false)
        val groupRv = view.findViewById<RecyclerView>(R.id.group_rv)
        val adapterGroup = GroupAdapterRV()
        groupRv.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        groupRv.adapter = adapterGroup
        val characterId = mCharacterVM.characterId
        val gameId = mCharacterVM.gameId
        val groupList = mutableListOf<GroupParam>()
        mCharacterVM.characterList.observe(viewLifecycleOwner) { list ->
            list.forEach { character ->
                if (character.id == characterId) {
                    val listNameGroup = TemplateCharacter().characterLisToGroupParam[4]!!
                    listNameGroup.forEach { nameGroup ->
                        character.attributes.forEach { gp ->
                            if (gp.title == nameGroup) {
                                groupList.add(gp)
                            }
                        }
                    }
                }
            }
        }
        val r = view.context.getSharedPreferences("id", 0).getString("newGameId", "0")!!.toInt()
        val newOrPres = gameId == r
        adapterGroup.setData(groupList, newOrPres)
        return view
    }
}