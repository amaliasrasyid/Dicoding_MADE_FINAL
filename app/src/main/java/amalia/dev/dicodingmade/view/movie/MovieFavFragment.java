package amalia.dev.dicodingmade.view.movie;


import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Objects;

import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.adapter.MovieAdapter;
import amalia.dev.dicodingmade.adapter.MovieFavTouchHelper;
import amalia.dev.dicodingmade.model.MovieRealmObject;
import amalia.dev.dicodingmade.repository.CatalogCursorLoader;
import amalia.dev.dicodingmade.repository.realm.RealmContract;
import amalia.dev.dicodingmade.repository.realm.RealmHelper;
import io.realm.Realm;
import io.realm.RealmChangeListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,MovieFavTouchHelper.RecylerItemTouchHelperListener {
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private static final int LOADER_ID = 7;
    private ArrayList<MovieRealmObject> dataLocal = new ArrayList<>();
    private MovieAdapter adapter;

    private ConstraintLayout constraintLayout;
    private RecyclerView rv;
    private ImageView imgNoFav;
    private TextView tvNoFav;

    private Realm realm;
    private RealmHelper realmHelper;
    private RealmChangeListener<Realm> realmChangeListener;

    private LoaderManager loaderManager;
    private LoaderManager.LoaderCallbacks<Cursor> callback;
    private Loader<Cursor> asyncMovieFavLoader;




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
        rv = view.findViewById(R.id.recyclerview_moviefav);
        adapter = new MovieAdapter(getActivity());


        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));

        //adding item touch listener
        ItemTouchHelper.SimpleCallback listener = new MovieFavTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(listener).attachToRecyclerView(rv);

        //load content from provider using CursorLoad (running async in background)
        callback = this;

        loaderManager = LoaderManager.getInstance(this);
        asyncMovieFavLoader = loaderManager.getLoader(LOADER_ID);
        if(asyncMovieFavLoader == null){
            adapter.swapCursor(null);
            loaderManager.initLoader(LOADER_ID,new Bundle(),callback);
        }else{
            adapter.swapCursor(null);
            loaderManager.restartLoader(LOADER_ID,new Bundle(),callback);
        }

        rv.setAdapter(adapter);
//        refresh();
        return view;
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putParcelableArrayList(EXTRA_STATE,adapter.getData());
//    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        CatalogCursorLoader cursorLoader =  new CatalogCursorLoader(Objects.requireNonNull(getActivity()), RealmContract.MovieColumns.CONTENT_URI);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        // The swapCursor() method assigns the new Cursor to the adapter
        //passing result load data provider into adapter
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
//    // Clear the Cursor we were using with another call to the swapCursor()
//        adapter.swapCursor(null);
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, final int position) {
        //pastikan viewholder-nya miliki MovieFavAdapter
        if (viewHolder instanceof MovieAdapter.ViewHolder) {
            //get title movie to show in snacbar when removing

            String name = Objects.requireNonNull(dataLocal.get(viewHolder.getAdapterPosition())).getTitle();
            final MovieRealmObject deletedMovie = dataLocal.get(position);
            //remove favorite movie temporary by set true val tmpDelete
            if (deletedMovie != null) {
                realmHelper.updateTmpDeleteM(deletedMovie.getId(), true);
            }


            //showing snackbar with undo option for restoring deleted movie fav
            Snackbar snackbar = Snackbar.make(constraintLayout, name + " deleted from favorites!", Snackbar.LENGTH_SHORT);
            snackbar.setAction("RESTORE", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //restore deleted movie by changing value askedDeletion back to false
                    if (deletedMovie != null) {
                        realmHelper.updateTmpDeleteM(deletedMovie.getId(), false);
                    }

                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.addCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar transientBottomBar, int event) {
                    super.onDismissed(transientBottomBar, event);
                    //when snackbar closed, delete permanently
                    if (deletedMovie != null && deletedMovie.isTmpDelete()) {
                        realmHelper.deleteFavMovies(deletedMovie.getId());
                    }
                }
            });
            snackbar.show();
        }
    }

//    //when there's change on data, do refresh
//    private void refresh() {
//        realmChangeListener= new RealmChangeListener<Realm>() {
//            @Override
//            public void onChange(@NonNull Realm realm) {
//                // ... do something with the updates (UI, etc.) ...
//                adapter = new MovieAdapter(getActivity());
//                adapter.setData(dataLocal);
//                rv.setAdapter(adapter);
//            }
//        };
//        realm.addChangeListener(realmChangeListener);
//    }
//
//
//
//    @Override
//    public void preExecute() {
//
//    }
//
//    @Override
//    public void postExecute(List<MovieRealmObject> movie) {
//        if(movie.size() > 0){
//            dataLocal.addAll(movie);
//            adapter.setData(dataLocal);
//        }else{
//            notifyMessage("Data tidak ada");
//            tvNoFav.setVisibility(View.VISIBLE);
//            imgNoFav.setVisibility(View.VISIBLE);
//        }
//    }

//    private static class LoadMovieFavAsync extends AsyncTask<Void,Void,List<MovieRealmObject>>{
//        private final WeakReference<Context> weakContext;
//        private final WeakReference<LoadMovieFavCallback> weakCallback;
//
//
//        LoadMovieFavAsync(Context context, LoadMovieFavCallback callback) {
//            this.weakContext = new WeakReference<>(context);
//            this.weakCallback = new WeakReference<>(callback);
//        }
//
//        @Override
//        protected List<MovieRealmObject> doInBackground(Void... voids) {
//            Context context = weakContext.get();
//            Cursor dataCursor = context.getContentResolver().query(MovieColumns.CONTENT_URI,null,null,null,null);
//            return MappingHelper.mapCursorToList(dataCursor);
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            weakCallback.get().preExecute();
//        }
//
//        @Override
//        protected void onPostExecute(List<MovieRealmObject> movieRealmObjects) {
//            super.onPostExecute(movieRealmObjects);
//            weakCallback.get().postExecute(movieRealmObjects);
//        }
//    }
//
//    public static class DataObserver extends ContentObserver{
//        final Context context;
//
//        public DataObserver(Handler handler,Context context) {
//            super(handler);
//            this.context = context;
//        }
//
//        @Override
//        public void onChange(boolean selfChange) {
//            super.onChange(selfChange);
//            new LoadMovieFavAsync(context, (LoadMovieFavCallback) context).execute();
//        }
//    }

    public void notifyMessage(String msg){
        Toast.makeText(getActivity(),msg, Toast.LENGTH_SHORT).show();
    }
}

