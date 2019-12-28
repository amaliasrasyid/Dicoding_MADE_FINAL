package amalia.dev.dicodingmade.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.model.Movie;
import amalia.dev.dicodingmade.view.MovieDetailActivity;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class MovieFavAdapter extends RealmRecyclerViewAdapter<Movie, MovieFavAdapter.ViewHolder> {
    private final Activity activity; //ini diperlukan untuk mengetahui posisi awal saat Intent dilakukan
    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w154";
    OrderedRealmCollection<Movie> data;



    public MovieFavAdapter(Activity activity, @Nullable OrderedRealmCollection<Movie> data) {
        super(data,true);
//        this.context = context;
        this.activity = activity;
        this.data = data;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout for item recylerview
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //binding antara view & data
        holder.bind(data.get(position));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView poster;
        final TextView title;
        final TextView overview;
        final TextView rating;
        final TextView popularity;
        final ConstraintLayout containerItem;
        final ConstraintLayout background;
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            //proses binding komponen view yang ada pada custom layout untuk recyclerview item
            poster = itemView.findViewById(R.id.img_item_poster);
            title = itemView.findViewById(R.id.rv_item_title);
            overview = itemView.findViewById(R.id.rv_item_overview);
            popularity = itemView.findViewById(R.id.tv_item_popularity);
            rating = itemView.findViewById(R.id.rv_item_rating);
            containerItem = itemView.findViewById(R.id.constraintlayout_rvitem_viewforeground);
            background = itemView.findViewById(R.id.constraintLayout_rvitem_background);



            //set listener
            containerItem.setOnClickListener(this);
        }

        void bind(Movie movie){
            title.setText(movie.getTitle());
            overview.setText(movie.getOverview());
            popularity.setText(String.valueOf(movie.getPopularity()));
            rating.setText(String.valueOf(movie.getVoteAverage()));
            Glide.with(activity)
                    .load(BASE_URL_IMG+movie.getPosterPath())
                    .transform(new CenterCrop(),new RoundedCorners(15))
                    .into(poster);
        }


        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.constraintlayout_rvitem_viewforeground){
                Intent intent = new Intent(activity, MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE,data.get(getAdapterPosition()));
                v.getContext().startActivity(intent);
            }
        }


        public void notifyMessage(String msg){
            Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show();
        }

    }
}
