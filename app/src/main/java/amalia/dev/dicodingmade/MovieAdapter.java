package amalia.dev.dicodingmade;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    //load data from MovieData
    private ArrayList<Movie> data;
    private Context context; //ini diperlukan untuk mengetahui posisi awal saat Intent dilakukan

    MovieAdapter(Context context){
        this.context = context;
        data = MovieData.getListData(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //di sini custom layout rv dipanggil
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_main,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //proses binding antara data dengan komponen view (imageview,textview,etc)
        Movie movie = data.get(position); // panggil objek Movie yang ingin diambil datanya

        //set data ke view
        holder.poster.setImageResource(movie.getPoster());
        holder.judul.setText(movie.getJudul());
        holder.rilis.setText(movie.getTglRilis());
        holder.sinopsis.setText(movie.getSinopsis());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView poster;
        TextView judul,sinopsis,rilis;
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            //proses binding komponen view yang ada pada custom layout untuk recyclerview item
            poster = itemView.findViewById(R.id.img_movieitem_poster);
            judul = itemView.findViewById(R.id.tv_movieitem_judul);
            sinopsis = itemView.findViewById(R.id.tv_movieitem_sinopsis);
            rilis = itemView.findViewById(R.id.tv_movieitem_rilis);

            //set listener
            itemView.setOnClickListener(this);
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
