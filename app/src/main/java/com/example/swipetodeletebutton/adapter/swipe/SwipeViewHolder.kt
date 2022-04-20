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

    /**
     * Return true if holder can be swiped, false otherwise
     */
    fun isScrollable(): Boolean

    /**
     * Called when user swipe view,
     * if dX > 0 than this is swiping to right
     * if dX < 0 than this is swiping to left
     * if dX == 0 than this is very firs swiping touch
     */
    fun onSwipe(dX: Float)

    /**
     * Called  when the user interaction with an element is over and it also completed its animation.
     */
    fun onSwipeComplete()

    /**
     * Reset view to initial state
     */
    fun resetView(animated: Boolean = false)

}