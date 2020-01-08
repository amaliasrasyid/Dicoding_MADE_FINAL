package amalia.dev.dicodingmade.view.movie;


import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.adapter.MovieAdapter;
import amalia.dev.dicodingmade.adapter.MovieFavTouchHelper;
import amalia.dev.dicodingmade.model.MovieRealmObject;
import amalia.dev.dicodingmade.repository.MappingHelper;
import amalia.dev.dicodingmade.repository.realm.RealmContract;

import static amalia.dev.dicodingmade.repository.realm.RealmContract.MovieColumns;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavFragment extends Fragment implements MovieFavTouchHelper.RecylerItemTouchHelperListener, LoadMovieFavCallback {
    private MovieAdapter adapter;
    private ConstraintLayout constraintLayout;
    private ImageView imgNoFav;
    private TextView tvNoFav;
    private ProgressBar progressBar;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private boolean isTmpDeleteFalse;
    private DataObserver dataObserver;


    public MovieFavFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_fav, container, false);
        constraintLayout = view.findViewById(R.id.constraintLayout_movie_fav_fragment_container);
        imgNoFav = view.findViewById(R.id.image_moviefav_nofavorites);
        tvNoFav = view.findViewById(R.id.text_moviefav_nofavorites);
        progressBar = view.findViewById(R.id.progress_circular_favorites);
        RecyclerView rv = view.findViewById(R.id.recyclerview_moviefav);
        adapter = new MovieAdapter(getActivity());

        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));

        //adding item touch listener
        ItemTouchHelper.SimpleCallback listener = new MovieFavTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(listener).attachToRecyclerView(rv);

        //get data from local db (provider)
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        dataObserver = new DataObserver(handler, getActivity(), this);
        getActivity().getContentResolver().registerContentObserver(RealmContract.MovieColumns.CONTENT_URI, true, dataObserver);

        //pass value that saved when rotated (ensure data persisted when device rotating)
        if (savedInstanceState == null) {
            new LoadMovieFavAsync(getActivity(), this).execute();
        } else {
            ArrayList<MovieRealmObject> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setData(list);
            }
        }

        return view;
    }

    //making data persisted when device rotated
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getData());
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, final int position) {
        //pastikan viewholder-nya miliki MovieFavAdapter
        final ContentValues cv = new ContentValues();
        if (viewHolder instanceof MovieAdapter.ViewHolder) {
            //get title movie to show in snacbar when removing
            String name = adapter.getData().get(position).getTitle();
            final int idDeletedMovie = adapter.getData().get(position).getId();
            //remove favorite movie temporary by set true val tmpDelete(content provider)
            Uri uriUpdate = Uri.parse(MovieColumns.CONTENT_URI + "/" + idDeletedMovie);
            cv.put(MovieColumns.COLUMN_NAME_TMP_DELETE,true);
           final int rowUpdated = Objects.requireNonNull(getActivity()).getContentResolver().update(uriUpdate, cv, null, null);


            //showing snackbar with undo option for restoring deleted movie fav
            if(rowUpdated > 0){
                isTmpDeleteFalse = true;
                Snackbar snackbar = Snackbar.make(constraintLayout, name + " deleted from favorites!", Snackbar.LENGTH_SHORT);
                snackbar.setAction("RESTORE", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(MovieColumns.CONTENT_URI + "/" + idDeletedMovie);
                        cv.put(MovieColumns.COLUMN_NAME_TMP_DELETE,false);
                        //restore deleted movie by changing value askedDeletion back to false
                        Objects.requireNonNull(getActivity()).getContentResolver().update(uri, cv, null, null);
                        isTmpDeleteFalse = false;
                    }
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        //when snackbar closed, delete permanently
                        if (isTmpDeleteFalse) {//because the change will caught by Observer, i use another variable for determining value of tmpDelete
                            Uri uri = Uri.parse(MovieColumns.CONTENT_URI + "/"+idDeletedMovie);
                            Objects.requireNonNull(getActivity()).getContentResolver().delete(uri,null,null);
                        }
                    }
                });
                snackbar.show();
            }else{
                notifyMessage("gagal update tmpDelete");
            }


        }
    }

    @Override
    public void preExecute() {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<MovieRealmObject> movie) {
        progressBar.setVisibility(View.INVISIBLE);
        if(movie.size() > 0){
            tvNoFav.setVisibility(View.INVISIBLE);
            imgNoFav.setVisibility(View.INVISIBLE);
            adapter.setData(movie);
        }else{
            adapter.setData(new ArrayList<MovieRealmObject>());
            tvNoFav.setVisibility(View.VISIBLE);
            imgNoFav.setVisibility(View.VISIBLE);
        }
    }

    static class LoadMovieFavAsync extends AsyncTask<Void, Void, ArrayList<MovieRealmObject>> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMovieFavCallback> weakCallback;

        LoadMovieFavAsync(Context context, LoadMovieFavCallback callback) {
            this.weakContext = new WeakReference<>(context);
            this.weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<MovieRealmObject> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor dataCursor = context.getContentResolver().query(RealmContract.MovieColumns.CONTENT_URI, null, null, null, null);
            if (dataCursor != null) {
                return MappingHelper.mCursorToArrayList(dataCursor);
            } else {
                return new ArrayList<MovieRealmObject>();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<MovieRealmObject> movieRealmObjects) {
            super.onPostExecute(movieRealmObjects);
            weakCallback.get().postExecute(movieRealmObjects);
        }
    }

   public static class DataObserver extends ContentObserver {
        final Context context;
        LoadMovieFavCallback callback;

        DataObserver(Handler handler, Context context, LoadMovieFavCallback callback) {
            super(handler);
            this.context = context;
            this.callback = callback;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadMovieFavAsync(context, callback).execute();
        }
    }

    private void notifyMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        //avoiding memory leak
        Objects.requireNonNull(getActivity()).getContentResolver().unregisterContentObserver(dataObserver);
        super.onPause();
    }
}


interface LoadMovieFavCallback {
    void preExecute();
    void postExecute(ArrayList<MovieRealmObject> movie);
}
