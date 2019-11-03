package amalia.dev.dicodingmade;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;

public class MovieData {

    public static ArrayList<Movie>getListData(Context ctx){
        ArrayList<Movie> data = new ArrayList<>();


        //get all string for title movie & sinopsis
        Resources res =ctx.getResources() ;
        String[] judulMovie = res.getStringArray(R.array.judul_film);
        String[] sinopsisMovie = res.getStringArray(R.array.sinopsis_film);


        Pemeran pemeran1[] ={new Pemeran("Hera Hilmar","Hester Shaw"),new Pemeran("Robert Sheehan","Tom Natsworthy"),new Pemeran("Hugo Weaving","Thaddeus Valentine"),new Pemeran("Jihae","Anna Fang"),new Pemeran("Ronan Raftery","Bevis Pod")};
        data.add(new Movie(
                judulMovie[0]
                ,sinopsisMovie[0]
                ,R.drawable.poster_mortalengine
                ,6.6
                , pemeran1
                ,"October 5,2018"
                ,"Released"
        ));

        data.add(new Movie(
                judulMovie[1]
                ,sinopsisMovie[1]
                ,R.drawable.poster_venom
                ,6.6
                , pemeran1
                ,"October 5,2018"
                ,"Released"
        ));


        return data;
    }
}
