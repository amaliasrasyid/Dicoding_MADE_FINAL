package amalia.dev.dicodingmade.view.movie;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.adapter.MovieAdapter;
import amalia.dev.dicodingmade.adapter.MovieFavAdapter;
import amalia.dev.dicodingmade.adapter.MovieFavTouchHelper;
import amalia.dev.dicodingmade.adapter.MovieFavTouchHelper.RecylerItemTouchHelperListener;
import amalia.dev.dicodingmade.model.MovieRealmObject;
import amalia.dev.dicodingmade.provider.MappingHelper;
import amalia.dev.dicodingmade.repository.realm.RealmContract;
import static amalia.dev.dicodingmade.repository.realm.RealmContract.MovieColumns;
import amalia.dev.dicodingmade.repository.realm.RealmHelper;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavFragment extends Fragment implements MovieFavTouchHelper.RecylerItemTouchHelperListener,LoadMovieFavCallback {
    private RecyclerView rv;
    private ArrayList<MovieRealmObject> dataLocal = new ArrayList<>();
    private Realm realm;
    private RealmHelper realmHelper;
    private MovieAdapter adapter;
    private ConstraintLayout constraintLayout;
    private ImageView imgNoFav;
    private TextView tvNoFav;
    private RealmChangeListener<Realm> realmChangeListener;
    private static final String EXTRA_STATE = "EXTRA_STATE";


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

        //get data from local db (provider)
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver dataObserver = new DataObserver(handler,getActivity());
        getActivity().getContentResolver().registerContentObserver(MovieColumns.CONTENT_URI,true,dataObserver);
        if(savedInstanceState == null){
            new LoadMovieFavAsync(getActivity(),this).execute();
        }else{
            List<MovieRealmObject> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if(list != null){
                dataLocal.addAll(list);
            }
        }
        rv.setAdapter(adapter);
//        refresh();
        if(dataLocal.size() == 0){
            tvNoFav.setVisibility(View.VISIBLE);
            imgNoFav.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE,adapter.getData());
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

    //when there's change on data, do refresh
    private void refresh() {
        realmChangeListener= new RealmChangeListener<Realm>() {
            @Override
            public void onChange(@NonNull Realm realm) {
                // ... do something with the updates (UI, etc.) ...
                adapter = new MovieAdapter(getActivity());
                adapter.setData(dataLocal);
                rv.setAdapter(adapter);
            }
        };
        realm.addChangeListener(realmChangeListener);
    }

    //don't forget realm must close after using it
    @Override
    public void onDestroy() {
        super.onDestroy();
//        realm.removeChangeListener(realmChangeListener);
//        realm.close();

    }

    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(List<MovieRealmObject> movie) {
        if(movie.size() > 0){
            ArrayList<MovieRealmObject> listMovie = new ArrayList<>();
            listMovie.addAll(movie);
            adapter.setData(listMovie);
        }else{
            notifyMessage("Data tidak ada");
        }
    }

    private static class LoadMovieFavAsync extends AsyncTask<Void,Void,List<MovieRealmObject>>{
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMovieFavCallback> weakCallback;


        public LoadMovieFavAsync(Context context,LoadMovieFavCallback callback) {
            this.weakContext = new WeakReference<>(context);
            this.weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected List<MovieRealmObject> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor dataCursor = context.getContentResolver().query(MovieColumns.CONTENT_URI,null,null,null,null);
            return MappingHelper.mapCursorToList(dataCursor);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected void onPostExecute(List<MovieRealmObject> movieRealmObjects) {
            super.onPostExecute(movieRealmObjects);
            weakCallback.get().postExecute(movieRealmObjects);
        }
    }

    public static class DataObserver extends ContentObserver{
        final Context context;

        public DataObserver(Handler handler,Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadMovieFavAsync(context, (LoadMovieFavCallback) context).execute();
        }
    }

            public void notifyMessage(String msg){
            Toast.makeText(getActivity(),msg, Toast.LENGTH_SHORT).show();
        }

}
interface LoadMovieFavCallback{
    void preExecute();
    void postExecute(List<MovieRealmObject> movie);
}
