package com.example.android_easyeats_views_prototype.carouselcomponents.carouselclasses

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_easyeats_views_prototype.R
import kotlin.math.abs


//Internal class for how to handle the main prominent card layout.
//this class determines how to handle different swipes across each screen,
//ALSO handles how to sacle EACH card per swipe as well
internal class ProminentCardLayoutManager(
    context: Context,

    //values from context
    /**
     * This value determines where items reach the final (minimum) scale:
     * - 1f is when their center is at the start/end of the RecyclerView
     * - <1f is before their center reaches the start/end of the RecyclerView
     * - >1f is outside the bounds of the RecyclerView
     * */
    //

    //value for the minimum scale distance factor
    private val minScaleDistanceFactor: Float = 1.5f,

    //Final Minimum scale factor for non in view items/non prominent items
    private val scaleDownBy:Float = 0.5f
):LinearLayoutManager(context, HORIZONTAL, false){

    //set a class value for the prominent card threshold
    private val prominentCardThreshold = context.resources.getDimensionPixelSize(R.dimen.prominent_threshold)

    //OVERRIDE the on layout completed,
    //how to handle the layout
    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        //ALSO add the scale children function to scale the appropriate card
            .also {
                scaleChildren()
            }
    }

    private fun scaleChildren() {

        //assign a variable to the center of the card/container
        val cardCenter = width / 2f

        //any cards that are above the threshold will be scaled down,
        //this is the threshold
        val scaleDistanceThreshold = minScaleDistanceFactor * cardCenter
        Log.d(R.string.CardSizeCalcTag.toString(), "scale distance thresh: $scaleDistanceThreshold")


        var translationXForwardDrag = 0f

        //For Every item, UNTIL the child count of the children amount, do the following...
        //Childcount from LAYOUT MANAGER
        for (i in 0 until childCount){
            //declare a variable for the child view being changed
            val child = getChildAt(i)!!

            //get the CENTER of the child
            val childCENTER = (child.left + child.right) / 2f
            Log.d(R.string.CardSizeCalcTag.toString(), "Child Center Value for card $i: $childCENTER")

            //calculate the distance to the center from
            val distanceToViewCenter = abs(childCENTER - childCENTER)
            Log.d(R.string.CardSizeCalcTag.toString(), "Child view distance to view center for $i: $distanceToViewCenter")

            //set the child to activated if the distance if the distance to center is less than prominent threshold
            child.isActivated = distanceToViewCenter < prominentCardThreshold
            Log.d(R.string.CardSizeCalcTag.toString(), "Activation State for Card #$i: ${distanceToViewCenter < prominentCardThreshold}")

            //calculate the amount to scale down the CARD by
            val scaleDownAmount = (distanceToViewCenter / scaleDistanceThreshold)
            Log.d(R.string.CardSizeCalcTag.toString(), "Scale Down Amount for card $i: $scaleDownAmount")

            //directly calculate the scale amount to use for the view
            val scale = 1f - scaleDownBy * scaleDownAmount
            Log.d(R.string.CardSizeCalcTag.toString(), "Scale Amount for card $i: $scale")

            //set scale by values for X AND Y sides
            child.scaleX = scale
            child.scaleY = scale

            //SET TRANSLATION VALUES FOR THE CADS

            //these values determine the animations between the different cards, and how to handle animations/TRANSLATIONS











        }


    }


}

