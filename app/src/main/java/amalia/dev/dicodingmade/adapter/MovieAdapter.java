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
import amalia.dev.dicodingmade.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    //load data from MovieData
    private final ArrayList<Movie> data = new ArrayList<>();
    private final Context context; //ini diperlukan untuk mengetahui posisi awal saat Intent dilakukan dan mendapatkan getAssets()

    public MovieAdapter(Context context){
        this.context = context;
    }

    public void setData(ArrayList<Movie> items){
        data.clear();
        data.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //di sini custom layout rv dipanggil
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
//        final TextView rilis;
        final ConstraintLayout containerItem;
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            //proses binding komponen view yang ada pada custom layout untuk recyclerview item
            poster = itemView.findViewById(R.id.img_movieitem_poster);
            judul = itemView.findViewById(R.id.tv_movieitem_judul);
            sinopsis = itemView.findViewById(R.id.tv_movieitem_sinopsis);
//            rilis = itemView.findViewById(R.id.tv_movieitem_rilis);
            containerItem = itemView.findViewById(R.id.constraintlayout_rvitem_container_item);
        }

        void bind(Movie movie){
            judul.setText(movie.getName());
            sinopsis.setText(movie.getDescription());
//            rilis.setText(movie.);
            Glide.with(context)
                    .load(movie.getPosterPath())
                    .into(poster);
        }


    }
}
