package com.example.android_easyeats_views_prototype.carouselcomponents.carouselclasses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_easyeats_views_prototype.carouselcomponents.carouseldataobjects.Food
import com.example.android_easyeats_views_prototype.databinding.CardccellviewBinding

internal class CarouselAdapter: RecyclerView.Adapter<CarouselAdapter.CardCellBinder>(){


    //List that contains the foods that will be displayed
    private var foodList = emptyList<Food>()


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



    }


    //Handle the bindings for the title and other elements
    class CardCellBinder(val itemBinding:CardccellviewBinding):RecyclerView.ViewHolder(itemBinding.root){

        //binding function for the list binder
        fun bind(foodData:Food){
            //bind NAME
            itemBinding.FoodCardTitle.text = foodData.foodName


        }
    }

    //set data function to set *NEW* food cards food cards for the list
    fun setFoodData(newFoodList:List<Food>){
        this.foodList = newFoodList

        //notify data set change so it changes the items in the list
        notifyDataSetChanged()
    }





}