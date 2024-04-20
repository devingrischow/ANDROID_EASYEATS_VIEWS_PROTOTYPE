package com.example.android_easyeats_views_prototype.carouselcomponents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_easyeats_views_prototype.databinding.FragmentCarouselBinding


//This fragment houses the carousel fragment, to build the full home screen requires both the dashboard and carousel view
//TODO Create dashboard fragment test with custom progress Bars
class CarouselFragment : Fragment() {

    //late init bindiing for the fragment recycler view
    private lateinit var carouselRecyclerViewBinding:FragmentCarouselBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //inflate the recycler view binder to the carousel recycler vew binding
        carouselRecyclerViewBinding = FragmentCarouselBinding.inflate(inflater, container, false)

        //assign the root to a variable
        val viewRoot = carouselRecyclerViewBinding.root


        //adapt the 




        // return the view root ON CREATION
        return viewRoot
    }


}