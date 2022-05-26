package com.example.test.activity_and_fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.adapters.DropDownAdapterRV
import com.example.test.data_base.DTemplateParamStr
import com.example.test.databinding.DialogChooseAddModificationBinding
import com.example.test.databinding.DialogChooseEndMoveIniciativeBinding
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.GameDAO
import com.example.test.viewModels.InitiativeFightVM
import com.example.test.viewModels.SkillTestVM
import com.example.test.views.HeaderView
import com.example.test.widgets.ModDialogFragment


class CharacterMenu : Fragment(), HeaderView.HeaderBack, ChooseFightDialogFragment.EndMove {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mGameVM: GameDAO by activityViewModels()
    private val mSkillVM: SkillTestVM by activityViewModels()
    private val mInitiativeFightVM: InitiativeFightVM by activityViewModels()
    private val arr = mutableListOf<String>()
    private var characterId = 0

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.character_menu, container, false)

        characterId = this.requireArguments().getInt("characterId", 0)
        mCharacterVM.initCharacterId(characterId)

        val tvName = view.findViewById<TextView>(R.id.CharacterMenu_NameCharacter)

        mCharacterVM.characterList.observe(viewLifecycleOwner) { list ->
            list.forEach {
                if (it.id == characterId) {
                    tvName.text =
                        DTemplateParamStr().readParamStr(it, "Базовые параметры", "Имя персонажа")
                }
            }
        }

        view.findViewById<Button>(R.id.CharacterMenu_OpenCharacterList).setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("characterId", characterId)
            view.findNavController()
                .navigate(R.id.action_characterMenu_to_characterList, bundle)
        }

        view.findViewById<ImageButton>(R.id.CharacterMenu_UseItem).setOnClickListener { }
        view.findViewById<ImageButton>(R.id.CharacterMenu_EndMove).setOnClickListener {
            val map = mInitiativeFightVM.findEndMoveByCharacterId(characterId)
            val dialogFragment = ChooseFightDialogFragment(this, map)
            dialogFragment.show(childFragmentManager, "chooseMod")
        }

        view.findViewById<HeaderView>(R.id.header).setBack(true, this, requireActivity(), viewLifecycleOwner)

        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

    override fun endMove(iniciativeId: Int) {
        mInitiativeFightVM.endMove(characterId, iniciativeId)
    }

}

class ChooseFightDialogFragment(private val endMove:EndMove, private val mapIniciativaFight:Map<Int,String>) : DialogFragment(), DropDownAdapterRV.TemplateHolder.WhenValueTo {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.dialog_choose_endMove_iniciative, container, false)

        val binding = DialogChooseEndMoveIniciativeBinding.bind(view)
        fun bind() = with(binding) {
            DD.setDDArrayAndListener(
                mapIniciativaFight.values.toList(), this@ChooseFightDialogFragment, null
            )
        }
        bind()

        return view
    }

    interface EndMove{
        fun endMove(iniciativeId:Int)
    }

    override fun whenValueTo(position: Int) {
        endMove.endMove(mapIniciativaFight.keys.toList()[position])
        dismiss()
    }
}
