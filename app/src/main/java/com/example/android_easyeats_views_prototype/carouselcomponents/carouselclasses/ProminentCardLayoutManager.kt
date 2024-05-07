package com.example.android_easyeats_views_prototype.carouselcomponents.carouselclasses

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_easyeats_views_prototype.R
import kotlin.math.abs
import kotlin.math.log
import kotlin.math.roundToInt


//Internal class for how to handle the main prominent card layout.
//this class determines how to handle different swipes across each screen,
//ALSO handles how to sacle EACH card per swipe as well
internal class ProminentCardLayoutManager(
    private val context: Context,

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


    var disableUpScroll:Boolean = true

    var disableSideScroll:Boolean = true

    var scroll_dx:Int = 0

    var scroll_dy:Int = 0


    lateinit var chosenCard:View
    //OVERRIDE the on layout completed,
    //how to handle the layout
    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        //ALSO add the scale children function to scale the appropriate card
            .also {
                scaleChildren()
                canScrollVertically()
            }
    }

//    override fun canScrollVertically(): Boolean {
//        Log.d("GESTURE_DETECT", "SCROLL UP")
//        return super.canScrollVertically()
//    }

    override fun canScrollVertically(): Boolean {
        return disableUpScroll
    }

    override fun canScrollHorizontally(): Boolean {
        return disableSideScroll
    }
    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int  = super.scrollVerticallyBy(dy, recycler, state).also{

    Log.d("GESTURE_DETECTT", "SCROLL UP AMOUNT $dy")

        scroll_dy = dy

        addUpScrollToCenterCard()






    //binding view for the recycler



    }



    //OVERRIDE the SCROLL HORIZONTAL VALUE
    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int = super.scrollHorizontallyBy(dx, recycler, state).also {
        Log.d("SCROL_SIDE", "SCROLL BY AMOUNT $dx")
        scroll_dx = dx
        scaleChildren()
    }


    private fun addUpScrollToCenterCard(){
        val cardCenter = width / 2f
        Log.d("GESTURE_DETECT_Y", "SCROLLIDGE")




        //GET CURRENT DRAGGING CARD
        for (i in 0 until childCount){
            //to get the center card, everytime the cards MUST be recalculated
            //the current child being iterated on
            val lastNum:Int = childCount - 1
            val i_child:View = if (i == 0){
                getChildAt(i)!!
            }else if (i != lastNum){
                getChildAt(i)!!
            }else{
                getChildAt(i)!!
            }

            //calculate the center card based on the left of the card and the right of the card divided by 2
            val center_calc_card = (i_child.left + i_child.right) / 2f
//            Log.d("SCROLL_UP", "SCROLL CENTER CARD")


            //calculate the cards distance from the CENTER
            val distanceToViewCenter = abs(center_calc_card - cardCenter)

            //set whether the card is activated
            i_child.isActivated = distanceToViewCenter < prominentCardThreshold


            if (i_child.isActivated){
                //if the card is true, then drag up the animated card
                disableUpScroll = false

                //first seperate the main layer
                val mainFoodBody = i_child.rootView.findViewById<RelativeLayout>(R.id.FoodCardMainBody)
                //ANIMATE THE CARD
                mainFoodBody.animate().translationY(-200f)

            }



        }



    }



    private fun scaleChildren() {



        //assign a variable to the center of the card/container
        val cardCenter = width / 2f

        //any cards that are above the threshold will be scaled down,
        //this is the threshold
        val scaleDistanceThreshold = minScaleDistanceFactor * cardCenter
        Log.d(R.string.CardSizeCalcTag.toString(), "scale distance thresh: $scaleDistanceThreshold")

        var translationXForwardValue = 0f

        //For Every item, UNTIL the child count of the children amount, do the following...
        //Childcount from LAYOUT MANAGER
        for (i in 0 until childCount){
            //declare a variable for the child view being changed
            val child = getChildAt(i)!!

            //get the CENTER of the child
            val childCENTER = (child.left + child.right) / 2f
            Log.d(context.getString(R.string.CardSizeCalcTag), "Child Center Value for card $i: $childCENTER")

            //calculate the distance to the center from
            val distanceToViewCenter = abs(childCENTER - cardCenter)
            Log.d(context.getString(R.string.CardSizeCalcTag), "Child view distance to view center for $i: $distanceToViewCenter")

            //set the child to activated if the distance if the distance to center is less than prominent threshold
            child.isActivated = distanceToViewCenter < prominentCardThreshold
            Log.d(context.getString(R.string.CardSizeCalcTag), "Activation State for Card #$i: ${distanceToViewCenter < prominentCardThreshold}")

            if (scroll_dx >= 1 && child.isActivated){
                Log.d("SCROLLIDGE", "BIBBLO")

                disableUpScroll = false
                child.isActivated = false
            }else if (child.isActivated){
                Log.d("reset", "RESET")
                disableUpScroll = true
            }

            //calculate the amount to scale down the CARD by
            val scaleDownAmount = (distanceToViewCenter / scaleDistanceThreshold).coerceAtMost(1f)
            Log.d(context.getString(R.string.CardSizeCalcTag), "Scale Down Amount for card $i: $scaleDownAmount")

            //directly calculate the scale amount to use for the view
            val scale = 1f - scaleDownBy * scaleDownAmount
            Log.d(context.getString(R.string.CardSizeCalcTag), "Scale Amount for card $i: $scale")

            //set scale by values for X AND Y sides
            child.scaleX = scale
            child.scaleY = scale

            //SET TRANSLATION VALUES FOR THE CADS

            //these values determine the animations between the different cards, and how to handle animations/TRANSLATIONS
            val translationDirection = if (childCENTER > cardCenter) - 1 else 1
            Log.d(context.getString(R.string.CardTranslationTag), "translation direction value for card $i: $translationDirection")

            //Translation for X from SCALE value
            val translationXFromScale = translationDirection * child.width * (1 - scale) / 2f
            Log.d(context.getString(R.string.CardTranslationTag), "translation for X From Scale value for card $i: $translationXFromScale")

            //apply the translation for X from the scale, plus how much to move the scale forward
            child.translationX = translationXFromScale + translationXForwardValue

            //Reset the translaton X Forward value to 0 AFTER the animation
            translationXForwardValue = 0f


            //Check If the translationXFrom scale is greater than *0*
            // AND the view index is greater than or equal to 1
            if (translationXFromScale > 0 && i >= 1 ) {

                //edit the last child and move it forward by 2 *Times*, times the translation X FROM SCALE
                getChildAt(i - 1)!!.translationX += 2 * translationXFromScale
                Log.d(context.getString(R.string.CardTranslationTag), "translation test values is greater than 0 and index is greater than 1 for $i")

            }
            //now checxk if the translationXfromScale is LESS THAN 0,
            //if it IS,
            //set the forward translation to 2 * the translationX from SCALE
            else if (translationXFromScale < 0) {

                //calculate the translation
                translationXForwardValue = 2 * translationXFromScale
                Log.d(context.getString(R.string.CardTranslationTag), "translation test values is less than 0 $i: $translationXForwardValue")

            }













        }


    }







    //OVERRIDE the EXTRA LAYOUT SPACE,
    // helps handle how to to layout cards offscreen,
    //the more sclaed down, the more space is needed
    override fun getExtraLayoutSpace(state: RecyclerView.State?): Int {
        return (width / (1 - scaleDownBy)).roundToInt()
    }





}

