package amalia.dev.dicodingmade;

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

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    //load data from MovieData
    private final ArrayList<Movie> data;
    private final Context context; //ini diperlukan untuk mengetahui posisi awal saat Intent dilakukan dan mendapatkan getAssets()

    MovieAdapter(Context context,ArrayList<Movie> data){
        this.context = context;
        this.data = data;
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
        //proses binding antara data dengan komponen view (imageview,textview,etc)
        Movie movie = data.get(position); // panggil objek Movie yang ingin diambil datanya

        //set data ke view
        holder.judul.setText(movie.getJudul());
        holder.rilis.setText(movie.getTglRilis());
        holder.sinopsis.setText(movie.getSinopsis());
        holder.poster.setImageResource(movie.getPoster());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView poster;
        final TextView judul;
        final TextView sinopsis;
        final TextView rilis;
        final ConstraintLayout containerItem;
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            //proses binding komponen view yang ada pada custom layout untuk recyclerview item
            poster = itemView.findViewById(R.id.img_movieitem_poster);
            judul = itemView.findViewById(R.id.tv_movieitem_judul);
            sinopsis = itemView.findViewById(R.id.tv_movieitem_sinopsis);
            rilis = itemView.findViewById(R.id.tv_movieitem_rilis);
            containerItem = itemView.findViewById(R.id.constraintlayout_rvitem_container_item);

            //set listener
            containerItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //akan pindah ke MovieDetail activity dan mengirim data objek item yg diklik
            Intent intent = new Intent(context,MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE,data.get(getAdapterPosition()));
            v.getContext().startActivity(intent);
        }
    }
}
