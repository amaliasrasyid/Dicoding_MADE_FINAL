package amalia.dev.dicodingmade.view.tvshow;


import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import javax.annotation.Nullable;
import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.adapter.TvShowFavAdapter;
import amalia.dev.dicodingmade.adapter.TvShowFavTouchHelper;
import amalia.dev.dicodingmade.model.TvShowRealmObject;
import amalia.dev.dicodingmade.repository.realm.RealmHelper;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavFragment extends Fragment implements TvShowFavTouchHelper.RecylerItemTouchHelperListener {
    private RecyclerView rv;
    private RealmResults<TvShowRealmObject> dataLocal;
    private ConstraintLayout constraintLayout;
    private Realm realm;
    private RealmHelper realmHelper;
    private TvShowFavAdapter adapter;
    private RealmChangeListener<Realm> realmChangeListener;


    public TvShowFavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show_fav, container, false);
        rv = view.findViewById(R.id.recyclerview_tvshowfav);
        constraintLayout = view.findViewById(R.id.constraintLayout_tv_show_fragment_container);
        TextView tvNoFav = view.findViewById(R.id.text_tvshowfav_nofavorites);
        ImageView imgNoFav = view.findViewById(R.id.image_tvshowfav_nofavorites);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()),DividerItemDecoration.VERTICAL));

        //adding item touch listener
        ItemTouchHelper.SimpleCallback listener = new TvShowFavTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(listener).attachToRecyclerView(rv);

        //configuration Realm and load data fav tvshow
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelper(realm);
        dataLocal = realmHelper.getListFavoriteTvShows();
        adapter = new TvShowFavAdapter(getActivity(),dataLocal);
        //set adapter into rv
        rv.setAdapter(adapter);
        refresh();

        if(dataLocal.size() == 0){
            tvNoFav.setVisibility(View.VISIBLE);
            imgNoFav.setVisibility(View.VISIBLE);
        }

        return  view;
    }
    //when there's change on data, do refresh
    private void refresh(){
        realmChangeListener = new RealmChangeListener<Realm>() {
            @Override
            public void onChange(@Nullable Realm realm) {
                adapter = new TvShowFavAdapter(getActivity(), realmHelper.getListFavoriteTvShows());
                rv.setAdapter(adapter);
            }
        };
        realm.addChangeListener(realmChangeListener);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        //pastikan viewholder-nya miliki MovieFavAdapter
        if (viewHolder instanceof TvShowFavAdapter.ViewHolder) {
            //get title movie to show in snacbar when removing
            String name = Objects.requireNonNull(dataLocal.get(viewHolder.getAdapterPosition())).getOriginalName();
            final TvShowRealmObject deletedTvShow = dataLocal.get(position);
            //remove favorite movie temporary by set true val tmpDelete
            if (deletedTvShow != null) {
                realmHelper.updateTmpDeleteTS(deletedTvShow.getId(), true);
            }


            //showing snackbar with undo option for restoring deleted movie fav
            Snackbar snackbar = Snackbar.make(constraintLayout, name + " deleted from favorites!", Snackbar.LENGTH_SHORT);
            snackbar.setAction("RESTORE", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //restore deleted movie by changing value askedDeletion back to false
                    if (deletedTvShow != null) {
                        realmHelper.updateTmpDeleteTS(deletedTvShow.getId(), false);
                    }

                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.addCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar transientBottomBar, int event) {
                    super.onDismissed(transientBottomBar, event);
                    //when snackbar closed, delete permanently
                    if (deletedTvShow != null && deletedTvShow.isTmpDelete()) {
                        realmHelper.deleteFavTvShow(deletedTvShow.getId());
                    }
                }
            });
            snackbar.show();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        realm.removeChangeListener(realmChangeListener);
        realm.close();
    }
}
