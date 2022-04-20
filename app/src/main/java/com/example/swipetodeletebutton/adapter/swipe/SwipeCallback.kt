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

class SwipeCallback : SimpleCallback(ACTION_STATE_IDLE, LEFT or RIGHT) {

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

        if (viewHolder != activeHolder) {
            activeHolder?.resetView(animated = true)
            activeHolder = viewHolder
        }

        viewHolder.onSwipe(dX)
    }

    override fun clearView(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ) {
        super.clearView(recyclerView, viewHolder)
        if (viewHolder is SwipeViewHolder) viewHolder.onSwipeComplete()
    }
}