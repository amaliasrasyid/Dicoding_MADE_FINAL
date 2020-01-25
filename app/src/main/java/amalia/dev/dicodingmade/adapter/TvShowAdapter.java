package amalia.dev.dicodingmade.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;

import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.model.TvShowRealmObject;
import amalia.dev.dicodingmade.view.tvshow.TvShowDetailActivity;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {
    private final ArrayList<TvShowRealmObject> data = new ArrayList<>();
    private final Context context;
    private static final String BASE_URL_POSTER = "https://image.tmdb.org/t/p/w154";

    public TvShowAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<TvShowRealmObject> items){
        data.clear();
        data.addAll(items);
        notifyDataSetChanged();
    }

    public  ArrayList<TvShowRealmObject> getData(){
        return data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate custom layout for recylerview item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //binding data (objek TvShowRealmObject) with view (objek ViewHolder)
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView poster;
        final TextView title;
        final TextView rating;
        final TextView overview;
        final TextView popularity;
        final ConstraintLayout foreground;
        final ConstraintLayout background;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            //proses binding komponen view yang ada pada custom layout untuk recyclerview item
            poster = itemView.findViewById(R.id.img_item_poster);
            title = itemView.findViewById(R.id.rv_item_title);
            overview = itemView.findViewById(R.id.rv_item_overview);
            popularity = itemView.findViewById(R.id.tv_item_popularity);
            rating = itemView.findViewById(R.id.rv_item_rating);
            foreground = itemView.findViewById(R.id.constraintlayout_rvitem_viewforeground);
            background = itemView.findViewById(R.id.constraintLayout_rvitem_background);


            //set listener
            foreground.setOnClickListener(this);
        }

        void bind(TvShowRealmObject tvShow) {
            title.setText(tvShow.getOriginalName());
            overview.setText(tvShow.getOverview());
            popularity.setText(String.valueOf(tvShow.getPopularity()));
            rating.setText(String.valueOf(tvShow.getVoteAverage()));
            Glide.with(context)
                    .load(BASE_URL_POSTER +tvShow.getPosterPath())
                    .transform(new CenterCrop(),new RoundedCorners(15))
                    .into(poster);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.constraintlayout_rvitem_viewforeground){
                Intent intent = new Intent(context, TvShowDetailActivity.class);
                intent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW,data.get(getAdapterPosition()));
                v.getContext().startActivity(intent);
            }
        }
//
//        public void notifyMessage(String msg){
//            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
//        }
    }
}
