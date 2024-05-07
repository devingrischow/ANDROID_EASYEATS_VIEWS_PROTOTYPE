package com.example.android_easyeats_views_prototype.carouselcomponents

import SwipeGesture
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.android_easyeats_views_prototype.R
import com.example.android_easyeats_views_prototype.carouselcomponents.carouselclasses.CarouselAdapter
import com.example.android_easyeats_views_prototype.carouselcomponents.carouselclasses.LinearHorizontalCardSpacingControl
import com.example.android_easyeats_views_prototype.carouselcomponents.carouselclasses.ProminentCardLayoutManager
import com.example.android_easyeats_views_prototype.carouselcomponents.carouseldataobjects.CarouselControllerViewModel
import com.example.android_easyeats_views_prototype.carouselcomponents.carouseldataobjects.Food
import com.example.android_easyeats_views_prototype.databinding.FragmentCarouselBinding


//This fragment houses the carousel fragment, to build the full home screen requires both the dashboard and carousel view
//TODO Create dashboard fragment test with custom progress Bars
class CarouselFragment : Fragment() {

    //late init bindiing for the fragment recycler view
    private lateinit var carouselRecyclerViewBinding: FragmentCarouselBinding

    //late init var for layout manager
    private lateinit var layoutManager: LinearLayoutManager

    //late init for the to be used for the carousel card adapter
    private lateinit var carouselAdapter: CarouselAdapter


    //late var for a SnaperHelper to help with making cards snap to the CENTER
    private lateinit var snapHelper: SnapHelper


    //Late Init var for holding the carousel view model for this fragment
    private lateinit var carouselViewModel: CarouselControllerViewModel

    //gesture detector variable for carousel fragment actions
    private lateinit var gDetector: GestureDetectorCompat

    //TAGS FOR SPECIFIC OUTS
    val gestureTag = "CAROUSEL_GESTURE_ACTION"

    private lateinit var currentAnimator: ObjectAnimator


    //Recycler view
    lateinit var carouselRecycler: RecyclerView

    private var totaldyDistance = 0 //cumulative scroll distance


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Instantiate the gesture detector and create a privare object that the fragment can override for gesture controls
        //**ADDING flick detecting to the carousel view fragment
//        this.gDetector = GestureDetector(this.requireContext(), object : GestureDetector.SimpleOnGestureListener(){
//            //Override the FLICK ACTION
//            override fun onFling(
//                e1: MotionEvent?,
//                e2: MotionEvent,
//                velocityX: Float,
//                velocityY: Float
//            ): Boolean {
//
//                Log.d(gestureTag, "FLING ACTING")
//
//                return true
//            }
//
//
//
//            override fun onScroll(
//                e1: MotionEvent?,
//                e2: MotionEvent,
//                distanceX: Float,
//                distanceY: Float
//            ): Boolean {
//
//                Log.d(gestureTag, "SCROLL ACTION")
//
//
//                return true
//            }
//
//
//
//
//
//
//        })
        //-----BOTTOM of the gesture detector for the view


        //On the view, set the on click listener for the view so its using the gesture detector
//
//        view?.setOnTouchListener { v, event ->
//
//            if (event.action == MotionEvent.ACTION_UP) {
//                // When the user stops touching the screen, we consider it as a click action
//                v.performClick()
//            }
//
//            gDetector.onTouchEvent(event)
//
//        }


        //Assign and Instantiate the VIEW MODEL PROVIDER
        carouselViewModel = ViewModelProvider(this).get(CarouselControllerViewModel::class.java)


        //create a array of food objects to use for the food cards
        val placeholderFoods = arrayListOf<Food>(
            Food(
                "Quasaunt",
                "quasaunt",
                "https://firebasestorage.googleapis.com/v0/b/easyeats-43b0d.appspot.com/o/Egg_Ingredient.png?alt=media&token=bf8dd425-65d5-4cdf-b8c7-7985ace11d76"
            ),
            Food(
                "Borito",
                "borito",
                "https://firebasestorage.googleapis.com/v0/b/easyeats-43b0d.appspot.com/o/chickenAndRice.jpg?alt=media&token=f5c0a680-a5a1-4347-8a1c-6fdc59c1aa98"
            ),
            Food(
                "Cheese Burger",
                "cb",
                "https://s7d1.scene7.com/is/image/mcdonalds/Header_Cheeseburger_832x472:1-3-product-tile-desktop?wid=763&hei=472&dpr=off"
            ),
            Food(
                "Taco",
                "taco",
                "https://mojo.generalmills.com/api/public/content/rFnqScZ6Yk22DDJxjdLBpw_gmi_hi_res_jpeg.jpeg?v=8152a3c4&t=16e3ce250f244648bef28c5949fb99ff"
            ),
            Food(
                "Chicken Nugget",
                "chickenNugget",
                "https://m.media-amazon.com/images/I/714M217UvaL.jpg"
            ),
            Food(
                "Cheeseburger Mac n Cheese",
                "cheese burger mac n cheese",
                "https://bestrecipebox.com/images/Skillet-Cheeseburger-Mac-and-Cheese-recipe-1.jpg"
            ),
            Food(
                "Popcorn Chicken",
                "popcorn chicken",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5YhBt9sHoHz0oiZ8-ZhCQ9ViTTE-iuG1wDAxSfynePw&s"
            ),
            Food(
                "Pizza Pocket",
                "pizzaPocket",
                "https://firebasestorage.googleapis.com/v0/b/easyeats-43b0d.appspot.com/o/Food_Images%2FtoastedCheesePizzaPocket.webp?alt=media&token=181bfed9-d34a-481b-8e06-523312625c82"
            ),
        )

        //set the carousels data array from the view model
        carouselViewModel.setNewActiveCarouselItems(placeholderFoods)


        //inflate the recycler view binder to the carousel recycler vew binding
        carouselRecyclerViewBinding = FragmentCarouselBinding.inflate(inflater, container, false)


        //assign the root to a variable
        val viewRoot = carouselRecyclerViewBinding.root


        //assign the layout manager to a proper new LINEAR LAYOUT MANAGER
        layoutManager = ProminentCardLayoutManager(this.requireContext())


        //Assign the ADAPTER
        carouselAdapter = CarouselAdapter()


        //Observe the Active Carousel Items, and with the foods set the carousel adapters food data

        carouselViewModel.activeCarouselItemsToPresent.observe(this.requireActivity()) { carouselItems ->


            //use the set food list function and give the carousel cards to use
            if (carouselItems != null) {
                carouselAdapter.setFoodData(carouselItems)
            }


        }


        //create a variable to host the recycler view
        carouselRecycler = carouselRecyclerViewBinding.CarouselRecycler

//        swipeToGesture(carouselRecycler)

        //Assign the *Snap Helper* to a `PagerSnapHelper()`
        snapHelper = PagerSnapHelper()


        //with the recycler view, assign the layout manager and adapter
        //to the view, THEN also add the spacing and decoaration item
        with(carouselRecycler) {

            //assign layout manager
            layoutManager = this@CarouselFragment.layoutManager

            //set the adapter to the custom made carousel adapter
            adapter = this@CarouselFragment.carouselAdapter

            val cardSpacing = resources.getDimensionPixelSize(R.dimen.carousel_spacing)

            //add the item decoration, use the linear horizontal spacing controller
            addItemDecoration(LinearHorizontalCardSpacingControl(cardSpacing))

        }


        //attach the snap helper to the CAROUSEL RECYCLER
        snapHelper.attachToRecyclerView(carouselRecycler)


        //Declare a private function `initRecyclerViewPosition` to take a position integer,
//        swipeToGesture(carouselRecycler)

//        addScrollUpAbilities()

//        addScrollGestureBehavior()

        //set up the PAGNATION function
//        setUpBasicCarouselPagination(carouselRecycler) //Basic Pagenation
        setUpFirebasePagination(carouselRecycler) //Firebase Pagination


        // return the view root ON CREATION
        return viewRoot
    }


    //Video Pagnation was started from, https://www.youtube.com/watch?v=Ck1EeSMXq3c
    //Rest of code is custom carousel card card carousel, VIDEO IN JAVA
    //**Create a function to handle Pagenation for NEW CARDS

    //This function handles implementation of BASIC local pagination implementation, no external connections for this one
    private fun setUpBasicCarouselPagination(carouselRecyclerView: RecyclerView) {

        //add a Add t he On Scroll Listener to listen for when the user Scrolls cards
        carouselRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dx > 0) { //if the amount of horizontal scroll is greater than 0, do the following

                    //if true, set some variables for the visible items count, the total items, AND the past items that were visible

                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                    //Check if the visible item count *PLUS* the past vivible items is greater than the total item count,  then say your at the last item
                    //and add more cards to display
                    if ((visibleItemCount!! + pastVisibleItems!!) >= totalItemCount!!) {

                        //Present a TOAST message notifiying at or near the END of the items
                        Toast.makeText(requireContext(), "PAGINATION!", Toast.LENGTH_SHORT).show()


                        //TODO: PAGNATE HERE AND ADD MORE CARDS TO THE FOOD ARRAY LIST
                        //add the ready to go items to the main carousel array
                        carouselViewModel.activeCarouselItemsToPresent.observe(requireActivity()) { activeCarouselList ->

                            carouselViewModel.readyToPresentFoods.shuffle()
                            //Add the items from the view models ready to go array
                            if (activeCarouselList != null) {
                                activeCarouselList.addAll(carouselViewModel.readyToPresentFoods)
                            }


                        }

                        //Notify the adapter the data set has changed
                        carouselAdapter.notifyDataSetChanged()


                    }


                }
            }

        })


    }
    //Bottom of Basic Pagination Function


    //This is another pagination function, this one is a FIREBASE POWERED function, to retrieve documents from firestore and populate the carousel with said data
    private fun setUpFirebasePagination(carouselRecyclerView: RecyclerView) {

        carouselRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            //Override the on scrolled behavior
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
                if (dx > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                    //Check Item Counts and availability bevore allowing pagination
                    if ((visibleItemCount!! + pastVisibleItems!!) >= totalItemCount!!) {

                        //Present a TOAST message notifiying at or near the END of the items
                        Toast.makeText(requireContext(), "FIREBASE PAGINATION!", Toast.LENGTH_SHORT)
                            .show()


                        //TODO: HANDLE FIREBASE PAGINATION AND ADDING NEW CARDS HERE!
                        //Query to firebase and get a collection of documents to set

                        carouselViewModel.queryFromFirebase()

                        //add the foods to the recycler


                        carouselAdapter.notifyDataSetChanged()


                    }


                }
            }
        })
    }
    //Bottom of firebase pagination function


    //Private Function to handle what to do on a swipe gesture


    private fun swipeToGesture(itemRv: RecyclerView?) {

        val swipeGesture = object : SwipeGesture(requireContext()) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.position
                try {
                    when (direction) {
                        ItemTouchHelper.UP -> {

                            //get swiping view amount


                            Log.d("bing", "SCROLLING UP ON $direction")
                        }
                    }
                } catch (e: Exception) {
                    Log.e("bing", "error on swipee")
                }
            }


        }

        val touchHelper = ItemTouchHelper(swipeGesture)

        touchHelper.attachToRecyclerView(itemRv)


    }


    private fun addScrollUpAbilities() {
        val verticalThreshold = 1   // Minimum pixels to consider as vertical scroll
        val horizontalThreshold = 100 // Minimum pixels to consider as horizontal scroll

        val gestureTAG = "BING_GEST"

        //adapt the carousel recycler and all children views with their subsiquent food datas.
        carouselRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                Log.d("BING", "ZING")

                val adder = dy
                totaldyDistance += adder


                val centerSnappedView = snapHelper.findSnapView(recyclerView.layoutManager)

                centerSnappedView?.let {

                        val toAnimatePart =
                            it.rootView.findViewById<RelativeLayout>(R.id.FoodCardMainBody)

                        toAnimatePart.animate()
                            .translationY(-200f)
                            .start()

                        Log.d(
                            gestureTAG,
                            "GESTURE AMOUNT $dy, $dx, to animate part ${toAnimatePart.id}"
                        )



                }




//                if (Math.abs(totaldyDistance) >= verticalThreshold) {
//                    Log.d(gestureTAG, "SHOULD ANIMATEEEE")
//
//
//                    //loop through all the children in the recycler view
////                    for (cardcellindex in 0 until recyclerView.childCount) {
////                        var child = recyclerView.getChildAt(cardcellindex)
////                        var childHolder =
////                            recyclerView.getChildViewHolder(child) as CarouselAdapter.CardCellBinder
////
////
//////                        childHolder.itemBinding.FoodCardMainBody.animate()
//////                            .translationY(-200f)
//////                            .setDuration(10)
//////                            .start()
////
////
////
////
////
////
////
////                    }
//
//
//                    totaldyDistance = 0
//                }


            }


        })
    }





    private fun addScrollGestureBehavior(){

        var APPTAg = "THING_BING"
        // attach a swipe gesture to the recycer view
        val gesture = GestureDetector(
            activity,
            object : SimpleOnGestureListener() {
                override fun onDown(e: MotionEvent): Boolean {
                    return true
                }


                override fun onScroll(
                    e1: MotionEvent?,
                    e2: MotionEvent,
                    distanceX: Float,
                    distanceY: Float
                ): Boolean {
//                    Log.d(APPTAg, "SCROLL, $distanceY")







                    val centerSnappedView = snapHelper.findSnapView(carouselRecycler.layoutManager)


                    val toAnimatePart =
                        centerSnappedView?.rootView?.findViewById<RelativeLayout>(R.id.FoodCardMainBody)



                    // Thresholds for detecting scrolls
                    val verticalThreshold = 65   // Minimum pixels to consider as vertical scroll
                    val horizontalThreshold = 100 // Minimum pixels to consider as horizontal scroll


                    //**Calculate scrolling up, then calculate left to right movement

                    e1?.let { startEvent ->
                        e2?.let { endEvent ->
                            //FIRST calculate deltaX and deltaY
                            val deltaX = endEvent.x - startEvent.x
                            val deltaY = endEvent.y - startEvent.y

//                            currentAnimator =
//                                ObjectAnimator.ofFloat(toAnimatePart, View.TRANSLATION_Y, -200f)
//                            currentAnimator.setAutoCancel(true)


                            if (Math.abs(deltaY) > Math.abs(deltaX)) { // More vertical movement
                                if (Math.abs(deltaY) > verticalThreshold) {  // Check if movement is above the threshold

                                    if (deltaY > 0) {
                                        //ON SCROLLING UPP, do the following
                                        Log.d(APPTAg, "SCROLLIDGE UP DOWN!")
//                                        animateSelectedCard
//                                        currentAnimator.end()

                                    } else {
                                        Log.d(APPTAg, "SCROLLIDGE DOWN UP!")
//                                        currentAnimator.start()
                                        toAnimatePart!!.animate().translationY(-200f)
                                    }

                                }
                            } else { //for horizontal movement confirmation

                                if (Math.abs(deltaX) > horizontalThreshold) {
                                    if (deltaX > 0) {
                                        //SWIPE LEFT TO RIGHT
//                                        animateSelectedCard.repeatMode = ValueAnimator.REVERSE //Then reverse the animation

                                        Log.d(APPTAg, "SCROLLIDGE LEFT TO RIGHT!")
//                                        animateSelectedCard.
//                                        currentAnimator.end()

                                    } else {
                                        //SWIPE RIGHT TO LEFT
                                        Log.d(APPTAg, "SCROLLIDGE RIGHT TO LEFT!")
//                                        animateSelectedCard.repeatMode = ValueAnimator.REVERSE //Then reverse the animation
//                                        currentAnimator.end()

//                                        animateSelectedCard


                                    }
                                }

                            }


                        }
                    }




                    return super.onScroll(e1, e2, distanceX, distanceY)
                }


                override fun onFling(
                    e1: MotionEvent?, e2: MotionEvent, velocityX: Float,
                    velocityY: Float
                ): Boolean {
                    //Create a animator object to animate the shape


//                        layoutManager.findViewByPosition(1)
                    var curr_carousel_view = snapHelper.findSnapView(carouselRecycler.layoutManager)

                    var curr_carousel_pos = curr_carousel_view?.let {
                        carouselRecycler.layoutManager?.getPosition(
                            it
                        )
                    }

                    var currMainView =
                        curr_carousel_view?.rootView?.findViewById<RelativeLayout>(R.id.FoodCardMainBody)


                    val SWIPE_MIN_DISTANCE = 120
                    val SWIPE_MAX_OFF_PATH = 250
                    val SWIPE_THRESHOLD_VELOCITY = 200
                    try {
                        if (Math.abs(e1!!.y - e2.y) > SWIPE_MAX_OFF_PATH) return false
                        if (e1!!.x - e2.x > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY
                        ) {
                            Log.i(APPTAg, "Right to Left")
                            val animateSelectedCard =
                                ObjectAnimator.ofFloat(currMainView, View.TRANSLATION_Y, 200f)
                            animateSelectedCard.start()
                        } else if (e2.x - e1!!.x > SWIPE_MIN_DISTANCE
                            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY
                        ) {
                            Log.i(APPTAg, "Left to Right")
                        }
                    } catch (e: Exception) {
                        // nothing
                    }
                    return super.onFling(e1, e2, velocityX, velocityY)
                }
            })



        carouselRecycler.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                // When the user stops touching the screen, we consider it as a click action
                v.performClick()
            }
            gesture.onTouchEvent(event)

        }

    }





}