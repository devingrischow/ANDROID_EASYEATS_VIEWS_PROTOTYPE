import android.R
import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


abstract class SwipeGesture(contextt: Context):ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.UP){


    private val background_gradient = com.example.android_easyeats_views_prototype.R.drawable.secondarybackgroundgradient

    //button under slider icon
    private val reviewRecipeButton  = com.example.android_easyeats_views_prototype.R.drawable.foodcard_image

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }


    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {




        RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            .addBackgroundColor(com.example.android_easyeats_views_prototype.R.color.black)
            .addActionIcon(reviewRecipeButton)
            .create()
            .decorate()


//        val background = ContextCompat.getDrawable(recyclerView.context, com.example.android_easyeats_views_prototype.R.drawable.secondarybackgroundgradient)
//        background!!.setBounds(
//            viewHolder.itemView.right + dX.toInt(),
//            viewHolder.itemView.top,
//            viewHolder.itemView.right,
//            viewHolder.itemView.bottom
//        )
//
//        background!!.draw(c)






        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }


}