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

interface SwipeViewHolder {

    val offsetLimit: Float

    val swipeBouncingWidth: Float

    fun isScrollable(): Boolean

    fun getContentOffset(): Float

    fun getButtonsOffset(): Float {
        return getContentOffset()
    }

    fun setButtonsOffset(offset: Float)

    fun setContentOffset(offset: Float)

    fun animateButtonsOffset(offset: Float)

    fun animateContentOffset(offset: Float)

    fun resetOffsets(animated: Boolean = false) {
        if (animated) {
            animateContentOffset(0f)
            animateButtonsOffset(0f)
        } else {
            setContentOffset(0f)
            setButtonsOffset(0f)
        }
    }

}