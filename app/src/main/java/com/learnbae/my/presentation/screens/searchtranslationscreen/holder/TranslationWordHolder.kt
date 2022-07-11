package com.learnbae.my.presentation.screens.searchtranslationscreen.holder

import com.learnbae.my.R
import com.learnbae.my.databinding.WordTranslationItemBinding
import com.learnbae.my.presentation.common.recycler.SimpleViewHolder

class TranslationWordHolder(private val binding: WordTranslationItemBinding) :
    SimpleViewHolder<String>(binding.root) {

    override fun bindTo(
        item: String,
        pos: Int,
        onClickCallback: ((String, Int) -> Unit)?
    ) {
        with(binding) {
            wordValue.text = root.resources.getString(
                R.string.words_list_item_pattern_bold,
                if (pos < 9) {
                    root.resources.getString(
                        R.string.words_list_item_before_10_pattern_number,
                        (pos + 1).toString()
                    )
                } else {
                    root.resources.getString(
                        R.string.words_list_item_after_10_pattern_number,
                        (pos + 1).toString()
                    )
                },
                item
            )
        }
    }
}