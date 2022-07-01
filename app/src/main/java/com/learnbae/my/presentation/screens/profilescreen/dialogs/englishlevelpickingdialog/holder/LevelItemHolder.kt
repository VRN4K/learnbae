package com.learnbae.my.presentation.screens.profilescreen.dialogs.englishlevelpickingdialog.holder

import com.learnbae.my.databinding.LevelItemLayoutBinding
import com.learnbae.my.presentation.common.recycler.SimpleViewHolder
import com.learnbae.my.presentation.screens.registrationscreen.EnglishLevelsList

class LevelItemHolder(private val binding: LevelItemLayoutBinding) :
    SimpleViewHolder<EnglishLevelsList>(binding.root) {

    override fun bindTo(
        item: EnglishLevelsList,
        pos: Int,
        onClickCallback: ((EnglishLevelsList, Int) -> Unit)?
    ) {
        with(binding) {
            englishLevelValue.text = item.name
            englishLevelDescription.text = root.resources.getString(item.description)
            this.englishLevelValue.setOnClickListener {
                itemLayout.isSelected = true
                onClickCallback?.invoke(item, pos)
            }
        }
    }
}