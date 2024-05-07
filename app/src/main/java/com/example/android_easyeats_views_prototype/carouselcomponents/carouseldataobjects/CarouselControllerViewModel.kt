package com.example.android_easyeats_views_prototype.carouselcomponents.carouseldataobjects

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class
CarouselControllerViewModel(application: Application):AndroidViewModel(application) {

    //Variables related to LIVE Observable data for the carousel fragment

    //Active Carousel Array Component Variables

    //These variables contain an array of items that the current active viewing carousel is using
    // to populate the carousel
    private val changeableActiveCarouselItems = MutableLiveData<ArrayList<Food>?>()
    //Live Data To Present Variable
    val activeCarouselItemsToPresent: MutableLiveData<ArrayList<Food>?> get() = changeableActiveCarouselItems
//    val activeCarouselItemsToPresent:LiveData<ArrayList<Food>> get() = changeableActiveCarouselItems


    //Internal Variables:
    //Variables used for things like pagenation, internal calculations, etc

    //Ready To Present Foods
    var readyToPresentFoods:ArrayList<Food> = arrayListOf(
        Food("Sandwhich","s","https://firebasestorage.googleapis.com/v0/b/easyeats-43b0d.appspot.com/o/Food_Images%2FHam%20Turkey%20And%20Cheese%20Spicy%20Mayo%20Sandwhich.png?alt=media&token=9eb6afdb-6280-45e0-8925-a135b577d5ce"),
        Food("Salad","sal","https://firebasestorage.googleapis.com/v0/b/easyeats-43b0d.appspot.com/o/Food_Images%2FeasyChickenCaesarSalad.webp?alt=media&token=7667bb45-48d1-4259-8159-1b4d344daf31"),
        Food("Bannana Smoothie","bms","https://firebasestorage.googleapis.com/v0/b/easyeats-43b0d.appspot.com/o/Food_Images%2FbananaSmoothie.webp?alt=media&token=fea4effa-a6fd-4cf4-8a45-be20b44d8fe6"),
        Food("Easy Egg Ramen","eggramen","https://firebasestorage.googleapis.com/v0/b/easyeats-43b0d.appspot.com/o/Food_Images%2FeasyEggRamen.webp?alt=media&token=7f031a98-b129-4b85-b7ec-90942b3c552a"),
        Food("Pan Made French Toast","pmft","https://firebasestorage.googleapis.com/v0/b/easyeats-43b0d.appspot.com/o/Food_Images%2FpanMadeFrenchToast.webp?alt=media&token=c89a8a8b-a19d-4429-ace1-c90eb86d226f"),
        Food("Scrambled Eggs","se","https://firebasestorage.googleapis.com/v0/b/easyeats-43b0d.appspot.com/o/Food_Images%2FscrambledEggs.webp?alt=media&token=954d5412-9f27-4d21-8aa4-f12ca43f9e81"),
        Food("Peanut Butter Toast","pbt","https://firebasestorage.googleapis.com/v0/b/easyeats-43b0d.appspot.com/o/Food_Images%2FpeanutButterToast.webp?alt=media&token=3a1a8d33-dd04-4ec4-9fb5-a8162c8003f5"),
    )








    //Set the active carousel items array to a new array of food items
    fun setNewActiveCarouselItems(newItems: ArrayList<Food>){
        //Change the value of the changeable active carousel items to be the items the user has now given

        changeableActiveCarouselItems.value = newItems
    }


    //A function to query everything from firestore (limit to 10)
    //the function returns a ArrayList of foods
    fun queryFromFirebase(){

        //create a variable for firestore
        val fs =  Firebase.firestore



        //launch a view model scope to ensure the following firebase operations occur on the UI thread
        viewModelScope.launch(Dispatchers.IO){


        fs.collection("Foods").limit(10) //set a limit to *10*
            .get() //preform a GET document operation

            //Add a listener for successfuly retrival of a document
            //(not LISTENENING to document, seperate operation* remember for future requre functions)
            .addOnSuccessListener { resultSnap ->

                //set updated foods to the value of the active carousel items value at the time of RUN (value)
                var updatedFoods = changeableActiveCarouselItems.value
                //start a co rotutine*


                    //on success, loop throguh all the documents and convert them to firebase objects
                    for (document in resultSnap){
                        //take the document and convert it TO A OBJECT
                        //with the *BUILT IN METHOD*

                        if (document != null){
                            val newFood = document.toObject<Food>()
                            Log.d("FS_FOOD_RETRIEVAL", "RETRIEVED FOOD $newFood")
                            //add the new food to the active carousel array


                            //before doing anything, check if the new foods to add list is null,
                            //if its not, add the new food to the list of new foods
                            if (updatedFoods != null) {
                                updatedFoods.add(newFood)

                                changeableActiveCarouselItems.postValue(updatedFoods)

                            }


                        }




                    }




            }

            //listen for any errors during the get operation and retrival operation,
            //this listens for when the document encounters a error during running
            .addOnFailureListener{ error ->

                Log.e("FS_FOOD_RETRIEVAL", "${error.message}")

            }



        }//bottom of coroutine











    }







}