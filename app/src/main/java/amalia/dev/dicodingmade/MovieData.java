package amalia.dev.dicodingmade;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;

class MovieData {

    static ArrayList<Movie>getListData(Context ctx){
        ArrayList<Movie> data = new ArrayList<>();


        //get all strings for title movie,release date,and sinopsis
        Resources res =ctx.getResources() ;
        String[] judulMovie = res.getStringArray(R.array.judul_film);
        String[] sinopsisMovie = res.getStringArray(R.array.sinopsis_film);
        String[] releaseDate = res.getStringArray(R.array.release_date);
        int[] posterName = {R.drawable.poster_mortalengine,R.drawable.poster_venom,R.drawable.poster_hunterkiller
                            ,R.drawable.poster_birdbox,R.drawable.poster_dragon,R.drawable.poster_dragonball
                            ,R.drawable.poster_robinhood,R.drawable.poster_spiderman,R.drawable.poster_thegirl,R.drawable.poster_themule};
       double[] rating = {7.7,7.7,6.8,6.5,8.1,7.7,7.7,6.8,6.5,8.1};

        //add data in form Movie object
        for(int i =0; i<=judulMovie.length-1;i++){
            data.add(new Movie(
                    judulMovie[i]
                    ,sinopsisMovie[i]
                    ,posterName[i]
                    ,releaseDate[i]
                    ,rating[i]
            ));
        }
        

       
        return data;
    }


}
