package amalia.dev.dicodingmade.widget.movieFav_widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.model.MovieRealmObject;
import amalia.dev.dicodingmade.repository.MappingHelper;
import amalia.dev.dicodingmade.repository.realm.RealmContract;

//THIS CLASS WORK LIKE ADAPTER FOR LISTVIEW/RECYCLERVIEW
public class MovieFavRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w185";
    private ArrayList<MovieRealmObject> widgetItem = new ArrayList<>();
    private final Context mContext;

    MovieFavRemoteViewsFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {
        // In onCreate() you setup any connections / cursors to your data source. Heavy lifting,
        // for example downloading or creating content etc, should be deferred to onDataSetChanged()
        // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.
    }

    @Override
    public void onDataSetChanged() {
        //based the guide on developer.android.com, this method always called after onCreate()
        //if proccess take more than 20s than ANR(Application Not Responding) will happening
        //load data from content provider
        final long identityToken = Binder.clearCallingIdentity();
        Cursor mCursor = mContext.getContentResolver().query(RealmContract.MovieColumns.CONTENT_URI, null, null, null, null);
        if (mCursor != null) {
            widgetItem = MappingHelper.mCursorToArrayList(mCursor);
        }
        Binder.restoreCallingIdentity(identityToken);

    }

    @Override
    public RemoteViews getViewAt(int position) {
        // Construct a remote views item based on the app widget item XML file,
        // and set the image based on the position.
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);//layout item widget
        //load image fav movie using Glide
        try {
            Bitmap bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(BASE_URL_IMG+widgetItem.get(position).getPosterPath())
                    .submit(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                    .get();

            remoteViews.setImageViewBitmap(R.id.img_widgetitem,bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bundle extras = new Bundle();
        extras.putInt(MovieFavWidget.EXTRA_POSITION_ITEM_MOVIE,position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.img_widgetitem,fillInIntent);

        return remoteViews;
    }


    @Override
    public int getCount() {
        return widgetItem.size();
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void onDestroy() {

    }
}
