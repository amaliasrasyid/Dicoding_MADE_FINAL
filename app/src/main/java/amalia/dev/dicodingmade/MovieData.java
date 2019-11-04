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
        String[] posterName = res.getStringArray(R.array.nama_poster);

        //add data in form Movie object
        data.add(new Movie(
                judulMovie[0]
                ,sinopsisMovie[0]
                ,posterName[0]
                ,releaseDate[0]
        ));

        data.add(new Movie(
                judulMovie[1]
                ,sinopsisMovie[1]
                ,posterName[1]
                ,releaseDate[1]
        ));

        data.add(new Movie(
                judulMovie[2]
                ,sinopsisMovie[2]
                ,posterName[2]
                ,releaseDate[2]
        ));

        data.add(new Movie(
                judulMovie[3]
                ,sinopsisMovie[3]
                ,posterName[3]
                ,releaseDate[3]
        ));

        data.add(new Movie(
                judulMovie[4]
                ,sinopsisMovie[4]
                ,posterName[4]
                ,releaseDate[4]
        ));

        data.add(new Movie(
                judulMovie[5]
                ,sinopsisMovie[5]
                ,posterName[5]
                ,releaseDate[5]
        ));

        data.add(new Movie(
                judulMovie[6]
                ,sinopsisMovie[6]
                ,posterName[6]
                ,releaseDate[6]
        ));

        data.add(new Movie(
                judulMovie[7]
                ,sinopsisMovie[7]
                ,posterName[7]
                ,releaseDate[7]
        ));

        data.add(new Movie(
                judulMovie[8]
                ,sinopsisMovie[8]
                ,posterName[8]
                ,releaseDate[8]
        ));

        data.add(new Movie(
                judulMovie[9]
                ,sinopsisMovie[9]
                ,posterName[9]
                ,releaseDate[9]
        ));



        return data;
    }


}
