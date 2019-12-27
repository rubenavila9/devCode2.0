package ariel.az.devcode20.Adaptadores;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import ariel.az.devcode20.R;

abstract public class SwipeToDeleteCallback  extends ItemTouchHelper.Callback {

    Context context;
    private Paint mClaerPaint;
    private ColorDrawable mBakground;
    private int backgroundColor;
    private Drawable deleteDrawable;
    private int intrinsicWidth;
    private int intrinsiHeight;


    public SwipeToDeleteCallback(Context context){
        // TODO: 12/27/2019 contructor
        this.context = context;
        this.mBakground = new ColorDrawable();
        this.backgroundColor = Color.parseColor("#b8050a");
        mClaerPaint = new Paint();
        mClaerPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        deleteDrawable = ContextCompat.getDrawable(this.context, R.drawable.ic_delete_black_24dp);
        intrinsicWidth = deleteDrawable.getIntrinsicWidth();
        intrinsiHeight = deleteDrawable.getIntrinsicHeight();
    }


    // TODO: 12/27/2019 sobreescribir estos 3 metodos primordial
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT);
    }


    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }


    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int itemHeight = itemView.getHeight();

        boolean isCancelled  = dX == 0 && !isCurrentlyActive;


        if (isCancelled){
            clearCanvas(c, itemView.getRight() + dX,(float) itemView.getTop(), (float) itemView.getRight(), (float)itemView.getBottom());
            super.onChildDraw(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive);
            return;
        }

        mBakground.setColor(backgroundColor);
        mBakground.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        mBakground.draw(c);

        int deleteIconTop = itemView.getTop() + (itemHeight - intrinsiHeight) / 2;
        int deleteIconMargin = (itemHeight - intrinsiHeight) / 2;
        int deleteIconLeft = itemView.getRight() - deleteIconMargin - intrinsicWidth;
        int deleteIconRight = itemView.getRight() - deleteIconMargin;
        int deleteIconBottom = deleteIconTop + intrinsiHeight;

        deleteDrawable.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
        deleteDrawable.draw(c);
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }


    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return 0.7f;
    }


    private void clearCanvas(Canvas c, Float left, Float top, Float right, Float bottom){
        c.drawRect(left,top,right,bottom,mClaerPaint);
    }
}
