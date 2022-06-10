package com.learnbae.my.presentation.screens.mainscreen.holder

import com.learnbae.my.databinding.TranslationItemBinding
import com.learnbae.my.presentation.common.recycler.SimpleViewHolder

class WordMinicardHolder(private val binding: TranslationItemBinding) :
    SimpleViewHolder<String>(binding.root) {

    override fun bindTo(
        item: String,
        pos: Int,
        onClickCallback: ((String, Int) -> Unit)?
    ) {
        with(binding) {
            wordOfADayTranslation.text = item
        }
    }
}