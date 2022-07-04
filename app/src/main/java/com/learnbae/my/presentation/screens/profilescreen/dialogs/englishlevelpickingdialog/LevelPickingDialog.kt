package com.learnbae.my.presentation.screens.profilescreen.dialogs.englishlevelpickingdialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.learnbae.my.databinding.EnglishLevelPickingDialogBinding
import com.learnbae.my.databinding.LevelItemLayoutBinding
import com.learnbae.my.presentation.common.recycler.SimpleAdapter
import com.learnbae.my.presentation.screens.profilescreen.dialogs.englishlevelpickingdialog.holder.LevelItemHolder
import com.learnbae.my.presentation.screens.registrationscreen.EnglishLevelsList
import ltst.nibirualert.my.presentation.common.onDestroyNullable

class LevelPickingDialog : DialogFragment() {
    private var binding by onDestroyNullable<EnglishLevelPickingDialogBinding>()
    private var listener: PickEnglishLevelClickListener? = null

    private val levelItemsListAdapter by lazy {
        SimpleAdapter(
            LevelItemLayoutBinding::inflate,
            createViewHolder = { LevelItemHolder(it) },
            onClickCallback = { item, _ ->
                listener?.onLevelClick(item.name)
                dismiss()
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EnglishLevelPickingDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        levelItemsListAdapter.swapItems(EnglishLevelsList.values().toList())
        binding.recyclerEnglishLevels.adapter = levelItemsListAdapter
        dialog!!.window!!.attributes.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun setActionListener(listener: PickEnglishLevelClickListener) {
        this.listener = listener
    }

    interface PickEnglishLevelClickListener {
        fun onLevelClick(levelValue: String) {}
    }
}