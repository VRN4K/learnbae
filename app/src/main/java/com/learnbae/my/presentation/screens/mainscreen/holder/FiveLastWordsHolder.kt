package com.learnbae.my.presentation.screens.mainscreen.holder

import com.learnbae.my.R
import com.learnbae.my.databinding.WordsListItemBinding
import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI
import com.learnbae.my.presentation.common.recycler.SimpleViewHolder

class FiveLastWordsHolder(private val binding: WordsListItemBinding) :
    SimpleViewHolder<VocabularyWordUI>(binding.root) {

    override fun bindTo(
        item: VocabularyWordUI,
        pos: Int,
        onClickCallback: ((VocabularyWordUI, Int) -> Unit)?
    ) {
        with(binding) {
            wordValue.text = root.resources.getString(
                R.string.words_list_item_pattern_bold,
                root.resources.getString(
                    R.string.words_list_item_before_10_pattern_number, (pos + 1).toString()
                ),
                item.title
            )
            wordTranslation.text = binding.root.resources.getString(
                R.string.words_list_item_pattern_regular,
                item.translation
            )
        }
    }
}