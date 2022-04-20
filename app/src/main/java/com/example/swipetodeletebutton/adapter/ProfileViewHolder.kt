/*
 * Copyright 2022 AlexK Lab
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.swipetodeletebutton.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.ImageViewCompat
import com.example.swipetodeletebutton.adapter.swipe.RightButtonSwipeViewHolder
import com.example.swipetodeletebutton.adapter.swipe.SwipeViewHolder
import com.example.swipetodeletebutton.data.Profile
import com.example.swipetodeletebutton.databinding.ProfileItemViewBinding

class ProfileViewHolder(
    parent: ViewGroup,
    private val onItemRemoveClicked: (ProfileViewHolder) -> Unit,
    private val binding: ProfileItemViewBinding = ProfileItemViewBinding
        .inflate(LayoutInflater.from(parent.context), parent, false),

    private val swipeViewHolderDelegate: SwipeViewHolder = RightButtonSwipeViewHolder(
        contentView = binding.cardView,
        buttonView = binding.removeButton,
        buttonMargin = binding.root.dpTtoPx(16f),
        swipeBouncingWidth = 24.toPx()
    )
) : SwipeViewHolder by swipeViewHolderDelegate, BaseViewHolder<Profile>(binding.root) {

    private var lastItemUid: Int? = null

    override fun onBind(item: Profile) = with(binding) {
        resetView(animated = item.uid == lastItemUid)
        lastItemUid = item.uid

        nameTextView.text = item.name
        descriptionTextView.text = item.description

        iconView.setImageResource(item.icon)
        ImageViewCompat.setImageTintList(iconView, ColorStateList.valueOf(item.iconTintColor))
        itemView.setBackgroundColor(item.backgroundColor)
        removeButton.isVisible = item.isRemovable
        removeButton.setOnClickListener { onItemRemoveClicked.invoke(this@ProfileViewHolder) }

    }


}