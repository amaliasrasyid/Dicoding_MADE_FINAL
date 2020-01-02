package amalia.dev.dicodingmade.repository;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

/***
 * THIS CLASS FOR ACCESSING PROVIDER ASYNCHRONOUSLY IN BACKGROUND
 * (LOADING DATA FROM PROVIDER)
 */


public class CatalogCursorLoader extends AsyncTaskLoader<Cursor>{
    Cursor cursor = null;
    Context context;
    Uri uri;

    public CatalogCursorLoader(@NonNull Context context,Uri uri) {
        super(context);
        this.context = context;
        this.uri = uri;
    }

    @Override
    public Cursor loadInBackground() {
        //Called on a worker thread to perform the actual load and to return the result of the load operation.
        return context.getContentResolver().query(uri,null,null,null,null);
    }

    public void sendResult(Cursor data){
        super.deliverResult(data);
        this.cursor = data;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(cursor != null){
            sendResult(cursor);
        }else{
            forceLoad();
        }
    }

}
