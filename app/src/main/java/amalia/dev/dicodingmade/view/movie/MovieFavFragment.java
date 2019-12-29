package amalia.dev.dicodingmade.view.movie;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.adapter.MovieFavAdapter;
import amalia.dev.dicodingmade.adapter.MovieFavTouchHelper;
import amalia.dev.dicodingmade.model.Movie;
import amalia.dev.dicodingmade.repository.realm.RealmHelper;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavFragment extends Fragment implements MovieFavTouchHelper.RecylerItemTouchHelperListener {
    private RecyclerView rv;
    private RealmResults<Movie> dataLocal;
    private Realm realm;
    private RealmHelper realmHelper;
    private MovieFavAdapter adapter;
    private FrameLayout frameLayout;
    private RealmChangeListener<Realm> realmChangeListener;


    public MovieFavFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_fav, container, false);
        frameLayout = view.findViewById(R.id.frameLayout_movie_fav_fragment_container);


        rv = view.findViewById(R.id.rv_movie_fav_fragment);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));

        //adding item touch listener
        ItemTouchHelper.SimpleCallback listener = new MovieFavTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(listener).attachToRecyclerView(rv);

        //get data from local db
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(realmConfiguration);
        realmHelper = new RealmHelper(realm);
        dataLocal = realmHelper.getListFavoriteMovies();
        adapter = new MovieFavAdapter(getActivity(), dataLocal);

        //set adapter into rv
        rv.setAdapter(adapter);

        refresh();
        return view;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, final int position) {
        //pastikan viewholder-nya miliki MovieFavAdapter
        if (viewHolder instanceof MovieFavAdapter.ViewHolder) {
            //get title movie to show in snacbar when removing

            String name = Objects.requireNonNull(dataLocal.get(viewHolder.getAdapterPosition())).getTitle();
            final Movie deletedMovie = dataLocal.get(position);
            //remove favorite movie temporary by set true val tmpDelete
            if (deletedMovie != null) {
                realmHelper.updateTmpDeleteM(deletedMovie.getId(), true);
            }


            //showing snackbar with undo option for restoring deleted movie fav
            Snackbar snackbar = Snackbar.make(frameLayout, name + " deleted from favorites!", Snackbar.LENGTH_SHORT);
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
                adapter = new MovieFavAdapter(getActivity(),realmHelper.getListFavoriteMovies());
                rv.setAdapter(adapter);
            }
        };
        realm.addChangeListener(realmChangeListener);
    }

    //don't forget realm must close after using it
    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.removeChangeListener(realmChangeListener);
        realm.close();

    }


}