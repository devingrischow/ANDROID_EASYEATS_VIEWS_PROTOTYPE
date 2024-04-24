package com.example.android_easyeats_views_prototype.carouselcomponents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.android_easyeats_views_prototype.R
import com.example.android_easyeats_views_prototype.carouselcomponents.carouselclasses.CarouselAdapter
import com.example.android_easyeats_views_prototype.carouselcomponents.carouselclasses.LinearHorizontalCardSpacingControl
import com.example.android_easyeats_views_prototype.carouselcomponents.carouselclasses.ProminentCardLayoutManager
import com.example.android_easyeats_views_prototype.carouselcomponents.carouseldataobjects.Food
import com.example.android_easyeats_views_prototype.databinding.FragmentCarouselBinding


//This fragment houses the carousel fragment, to build the full home screen requires both the dashboard and carousel view
//TODO Create dashboard fragment test with custom progress Bars
class CarouselFragment : Fragment() {

    //late init bindiing for the fragment recycler view
    private lateinit var carouselRecyclerViewBinding:FragmentCarouselBinding

    //late init var for layout manager
    private lateinit var layoutManager: LinearLayoutManager

    //late init for the to be used for the carousel card adapter
    private lateinit var carouselAdapter: CarouselAdapter


    //late var for a SnaperHelper to help with making cards snap to the CENTER
    private lateinit var snapHelper: SnapHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //create a array of food objects to use for the food cards
        val placeholderFoods = arrayListOf<Food>(
            Food("Quasaunt", "quasaunt", "https://firebasestorage.googleapis.com/v0/b/easyeats-43b0d.appspot.com/o/Egg_Ingredient.png?alt=media&token=bf8dd425-65d5-4cdf-b8c7-7985ace11d76"),
            Food("Borito", "borito", "https://firebasestorage.googleapis.com/v0/b/easyeats-43b0d.appspot.com/o/chickenAndRice.jpg?alt=media&token=f5c0a680-a5a1-4347-8a1c-6fdc59c1aa98"),
            Food("Cheese Burger", "cb", "https://s7d1.scene7.com/is/image/mcdonalds/Header_Cheeseburger_832x472:1-3-product-tile-desktop?wid=763&hei=472&dpr=off"),
            Food("Taco", "taco", "https://mojo.generalmills.com/api/public/content/rFnqScZ6Yk22DDJxjdLBpw_gmi_hi_res_jpeg.jpeg?v=8152a3c4&t=16e3ce250f244648bef28c5949fb99ff"),
            Food("Chicken Nugget", "chickenNugget", "https://m.media-amazon.com/images/I/714M217UvaL.jpg"),
            Food("Cheeseburger Mac n Cheese", "cheese burger mac n cheese", "https://bestrecipebox.com/images/Skillet-Cheeseburger-Mac-and-Cheese-recipe-1.jpg"),
            Food("Popcorn Chicken", "popcorn chicken", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5YhBt9sHoHz0oiZ8-ZhCQ9ViTTE-iuG1wDAxSfynePw&s")
            )

        //inflate the recycler view binder to the carousel recycler vew binding
        carouselRecyclerViewBinding = FragmentCarouselBinding.inflate(inflater, container, false)

        //assign the root to a variable
        val viewRoot = carouselRecyclerViewBinding.root


        //assign the layout manager to a proper new LINEAR LAYOUT MANAGER
        layoutManager = ProminentCardLayoutManager(this.requireContext())

        //Assign the ADAPTER
        carouselAdapter = CarouselAdapter()
        //use the set food list function and give the carousel cards to use
        carouselAdapter.setFoodData(placeholderFoods)



        //create a variable to host the recycler view
        val carouselRecycler = carouselRecyclerViewBinding.CarouselRecycler



        //Assign the *Snap Helper* to a `PagerSnapHelper()`
        snapHelper = PagerSnapHelper()


        //with the recycler view, assign the layout manager and adapter
        //to the view, THEN also add the spacing and decoaration item
        with(carouselRecycler){

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






        //instantiate the CARD ADAPTER to the




        // return the view root ON CREATION
        return viewRoot
    }


}