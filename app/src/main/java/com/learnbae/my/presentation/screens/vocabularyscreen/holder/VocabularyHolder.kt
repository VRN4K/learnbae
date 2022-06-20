package com.learnbae.my.presentation.screens.vocabularyscreen.holder

import com.learnbae.my.databinding.VocabularyItemBinding
import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI
import com.learnbae.my.presentation.common.recycler.SimpleViewHolder

class VocabularyHolder(private val binding: VocabularyItemBinding) :
    SimpleViewHolder<VocabularyWordUI>(binding.root) {

    override fun bindTo(
        item: VocabularyWordUI,
        pos: Int,
        onClickCallback: ((VocabularyWordUI, Int) -> Unit)?
    ) {
        binding.apply {
            wordValue.text = item.title
            wordTranslation.text = item.translation
            swipeDelete.setOnClickListener { onClickCallback?.invoke(item, pos) }
        }
    }
}