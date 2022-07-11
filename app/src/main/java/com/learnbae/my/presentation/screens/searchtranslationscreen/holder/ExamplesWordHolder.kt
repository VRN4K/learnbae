package com.learnbae.my.presentation.screens.searchtranslationscreen.holder

import com.learnbae.my.databinding.ExampleTranslationItemBinding
import com.learnbae.my.databinding.ExamplesItemBinding
import com.learnbae.my.presentation.common.recycler.SimpleAdapter
import com.learnbae.my.presentation.common.recycler.SimpleViewHolder

class ExamplesWordHolder(private val binding: ExamplesItemBinding) :
    SimpleViewHolder<Pair<String, List<String>>>(binding.root) {

    private val exampleTranslationAdapter by lazy {
        SimpleAdapter(
            ExampleTranslationItemBinding::inflate,
            createViewHolder = { ExampleTranslationHolder(it) },
            onClickCallback = { _, _ -> }
        )
    }

    override fun bindTo(
        item: Pair<String, List<String>>,
        pos: Int,
        onClickCallback: ((Pair<String, List<String>>, Int) -> Unit)?
    ) {
        with(binding) {
            exampleValue.text = item.first
            examplesItemsRecycler.adapter = exampleTranslationAdapter
            exampleTranslationAdapter.swapItems(item.second)
        }
    }
}