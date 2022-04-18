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

package com.example.swipetodeletebutton.adapter.swipe

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class SwipeCallback : SimpleCallback(ACTION_STATE_IDLE, LEFT or RIGHT) {

    private var currentOffset = 0f

    private var activeHolder: SwipeViewHolder? = null

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int = when {
        viewHolder !is SwipeViewHolder -> ACTION_STATE_IDLE
        viewHolder.isScrollable() -> super.getMovementFlags(recyclerView, viewHolder)
        else -> ACTION_STATE_IDLE
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 1f // prevent item swiped
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return Float.MAX_VALUE
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        // onSwipe allowed only for active ACTION_STATE_SWIPE
        if (actionState != ACTION_STATE_SWIPE || !isCurrentlyActive) return

        // onSwipe allowed only for SwipeViewHolder
        if (viewHolder !is SwipeViewHolder) return

        onSwipe(viewHolder, dX)
    }

    override fun clearView(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ) {
        super.clearView(recyclerView, viewHolder)
        if (viewHolder is SwipeViewHolder) finalizeOffsets(viewHolder)
    }

    private fun onSwipe(viewHolder: SwipeViewHolder, dX: Float) {
        if (viewHolder != activeHolder) {
            activeHolder?.resetOffsets(animated = true)
            activeHolder = viewHolder
        }

        if (dX == 0f) {
            currentOffset = viewHolder.getContentOffset()
        }

        val minOffset = viewHolder.offsetLimit
        val offset = getOffset(
            offset = currentOffset + dX,
            minOffset = minOffset
        )

        if (dX > 0) {
            if (viewHolder.getContentOffset() < minOffset) {
                viewHolder.setContentOffset(min(offset, minOffset))
            } else {
                viewHolder.setButtonsOffset(offset)
                viewHolder.setContentOffset(offset)
            }
        } else {
            val bouncingOffset = getBouncingOffset(
                offset = currentOffset + dX,
                minOffset = minOffset,
                bouncingWidth = viewHolder.swipeBouncingWidth
            )
            viewHolder.setButtonsOffset(offset)
            viewHolder.setContentOffset(bouncingOffset)
        }
    }

    private fun finalizeOffsets(holder: SwipeViewHolder) {
        val minOffset = holder.offsetLimit

        // autoscroll if reached 30% of min offset
        val autoscrollOffset = 0.3f * minOffset

        val contentOffset = holder.getContentOffset()
        val offset = if (contentOffset + autoscrollOffset < minOffset) minOffset else 0f
        holder.animateContentOffset(offset)
        holder.animateButtonsOffset(offset)
    }

    private fun getBouncingOffset(minOffset: Float, bouncingWidth: Float, offset: Float): Float {
        val minBouncingOffset = minOffset - bouncingWidth
        return when {
            offset > 0 -> 0f
            offset < minBouncingOffset -> minBouncingOffset

            offset < minOffset -> {
                val diff = offset - minOffset
                val decreaseFactor = interpolate(abs(diff) / bouncingWidth)
                max(minBouncingOffset, minOffset + diff * decreaseFactor)
            }

            else -> offset
        }
    }

    private fun getOffset(minOffset: Float, offset: Float): Float = when {
        offset < minOffset -> minOffset
        offset > 0f -> 0f
        else -> offset
    }

    private fun interpolate(input: Float): Float = (1.0f - (1.0f - input) * (1.0f - input))

}