package amalia.dev.dicodingmade;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    //load data from MovieData
    private final ArrayList<Movie> data;
    private final Context context; //ini diperlukan untuk mengetahui posisi awal saat Intent dilakukan dan mendapatkan getAssets()

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
        holder.judul.setText(movie.getJudul());
        holder.rilis.setText(movie.getTglRilis());
        holder.sinopsis.setText(movie.getSinopsis());
        loadImageFromAssets(movie.getPoster(),holder.poster);
    }

    //method for loading image for assets folder
    private void loadImageFromAssets(String namaFile, ImageView img){
        try {
            //get input stream
            InputStream input = context.getAssets().open(namaFile);
            //load image as drawable
            Drawable drawable = Drawable.createFromStream(input,null);
            //set image to ImageView
            img.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
