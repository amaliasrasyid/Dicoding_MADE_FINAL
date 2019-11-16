package amalia.dev.dicodingmade.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.model.TvShow;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {
    private ArrayList<TvShow> data = new ArrayList<>();
    Context context;
    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w154";

    public TvShowAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<TvShow> items){
        data.clear();
        data.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate custom layout for recylerview item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //binding data (objek TvShow) with view (objek ViewHolder)
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView poster;
        final TextView judul;
        final TextView sinopsis;
        final TextView popularity;
        final ConstraintLayout containerItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //proses binding komponen view yang ada pada custom layout untuk recyclerview item
            poster = itemView.findViewById(R.id.img_movieitem_poster);
            judul = itemView.findViewById(R.id.tv_movieitem_judul);
            sinopsis = itemView.findViewById(R.id.tv_movieitem_sinopsis);
            popularity = itemView.findViewById(R.id.tv_movieitem_rilis);
            containerItem = itemView.findViewById(R.id.constraintlayout_rvitem_container_item);
        }

        public void bind(TvShow tvShow) {
            judul.setText(tvShow.getOriginalName());
            sinopsis.setText(tvShow.getOverview());
            popularity.setText(String.valueOf(tvShow.getPopularity()));
            Glide.with(context)
                    .load(BASE_URL_IMG+tvShow.getPosterPath())
                    .into(poster);
        }
    }
}
