package amalia.dev.dicodingmade.adapter;

import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class TvShowFavTouchHelper extends ItemTouchHelper.SimpleCallback {
    private static final String LOG_TAG_TOUCHHELPER_TV = "tv touch helper";

    private final RecylerItemTouchHelperListener listener;

    public TvShowFavTouchHelper(int dragDirs, int swipeDirs, RecylerItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }
    // this used for drag-drp upon receiving callback, you should move the item from the old to the new position.
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    //Called when the ViewHolder swiped or dragged by the ItemTouchHelper is changed.
    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            final View foregroundView = ((TvShowAdapter.ViewHolder) viewHolder).foreground;
            getDefaultUIUtil().onSelected(foregroundView);
            Log.d(LOG_TAG_TOUCHHELPER_TV,"onSelectedChange");
        }
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        Log.d(LOG_TAG_TOUCHHELPER_TV,"convert To Absolute Direction");
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    //adding a view
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((TvShowAdapter.ViewHolder) viewHolder).foreground;
        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
        Log.d(LOG_TAG_TOUCHHELPER_TV," on Child Draw");
    }

    // the x-position of the foreground view is changed while user is swiping the view.
    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((TvShowAdapter.ViewHolder) viewHolder).foreground;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
        Log.d(LOG_TAG_TOUCHHELPER_TV,"on Child Draw Over");
    }

    //called after or on running onSwiped listener on TvshowFavFragment.java
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
        Log.d(LOG_TAG_TOUCHHELPER_TV,"on Swiped");
    }

    //Called by the ItemTouchHelper when the user interaction with an element is over and it also completed its animation.
    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
       final  View foregroundView = ((TvShowAdapter.ViewHolder) viewHolder).foreground;
        getDefaultUIUtil().clearView(foregroundView);
        Log.d(LOG_TAG_TOUCHHELPER_TV,"clear view");
    }

    public interface RecylerItemTouchHelperListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
