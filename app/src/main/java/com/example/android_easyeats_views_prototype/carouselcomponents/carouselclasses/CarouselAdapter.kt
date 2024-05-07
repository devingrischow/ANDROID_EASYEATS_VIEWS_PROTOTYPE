package com.example.android_easyeats_views_prototype.carouselcomponents.carouselclasses

import android.animation.ObjectAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_easyeats_views_prototype.R
import com.example.android_easyeats_views_prototype.carouselcomponents.carouseldataobjects.Food
import com.example.android_easyeats_views_prototype.databinding.CardccellviewBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.withContext


internal class CarouselAdapter: RecyclerView.Adapter<CarouselAdapter.CardCellBinder>(){


    //List that contains the foods that will be displayed
    private var foodList = emptyList<Food>()

    private lateinit var foodBinding:CardccellviewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardCellBinder {
        val itemBinder = CardccellviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardCellBinder(itemBinder)
    }

    override fun getItemCount() = foodList.count()

    override fun onBindViewHolder(holder: CardCellBinder, position: Int) {
        //in the binder, bind the text



        //declare a variable for the current item the adapter is creating
        val currFood = foodList[position]


        //bind food holder
        holder.bind(currFood)

        //set the binding to the class level used binder


//            .setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//
//            Log.d("SCROLLTAG", "SCROLLED BY AMOUNTX $scrollX,Y $scrollY ")
//
//
//        }

    }

    fun animateCardData(itemPosition:Int){

        val animatedCard = ObjectAnimator.ofFloat()

        //reference to the selected foods binder
        val selectedFoodBinding = foodList[itemPosition]

    }


    //Handle the bindings for the title and other elements
    class CardCellBinder(val itemBinding:CardccellviewBinding):RecyclerView.ViewHolder(itemBinding.root){

        //binding function for the list binder
        fun bind(foodData:Food){



            //bind NAME
            itemBinding.FoodCardTitle.text = foodData.foodName

            //use context of the IMAGE VIEW
            Picasso.with(itemBinding.FoodCardImage.context)
                .load(foodData.image) //LOAD THE FOOD DATA from the objects image
                .resize(301,426)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(itemBinding.FoodCardImage) //LOAD THE IMAGE INTO the food card image

            Log.d("PICASSO", "IMG TO LOAD ${foodData.image}")


        }
    }

    //set data function to set *NEW* food cards food cards for the list
    fun setFoodData(newFoodList:List<Food>){
        this.foodList = newFoodList

        //notify data set change so it changes the items in the list
        notifyDataSetChanged()
    }







}