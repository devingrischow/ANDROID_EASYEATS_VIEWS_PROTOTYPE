package com.example.android_easyeats_views_prototype.carouselcomponents.carouselclasses

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView


//This and some carousel parts are based from: https://medium.com/holler-developers/paging-image-gallery-with-recyclerview-f059d035b7e7
//Project is for me (at current date aprl 22, 2024) broken on open, but I can confirm works from recent confirmations on git.
//task is to get old code working in usable state AND be able to accept data points


//Based from Linear Horizontal Spacing Decoration
//Decoration i assume is the modifiers being added for spacing and controlling it, but decoration is a strange word choice
//so better one 👍




//Item decoration is the name of what the recycler view uses, however this will "control" the decoration

//@PX is used to indicate the value should be interpreted as pixels,
//this way the value given to this function can space items out in PIXELS and not any other spacing (ex: dp, sp, etc)
class LinearHorizontalCardSpacingControl(@Px private val innerSpacing:Int): RecyclerView.ItemDecoration() {

    //override the get item offsets function,
    //the offsets that control the cards spacing
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        //Declare a value for the items position, based on the views adapter position
        //get the value for the current card index being created
        val cardIndex = parent.getChildAdapterPosition(view)

        //change the values for the right and left spacings of the outer rectangle

        //TODO: Change paddings for starting card so only 1 card appears in the middle
        //if its the first item, dont have any spacing on the *LEFT side, otherwise for all normal cards take the provided inner spacing / 2 (half for each side)
        outRect.left = if (cardIndex == 0) 0 else innerSpacing / 2

        //Follow for the same thing for the RIGHT, except this time test for if its the FINAL card
        outRect.right = if (cardIndex == state.itemCount - 1) 0 else innerSpacing / 2
    }




}