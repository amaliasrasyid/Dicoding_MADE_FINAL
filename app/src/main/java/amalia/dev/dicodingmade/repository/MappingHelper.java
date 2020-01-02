package amalia.dev.dicodingmade.repository;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import amalia.dev.dicodingmade.model.MovieRealmObject;
import amalia.dev.dicodingmade.repository.realm.RealmContract;
import io.realm.RealmList;

import static amalia.dev.dicodingmade.repository.realm.RealmContract.MovieColumns;


public class MappingHelper {

    public static List<MovieRealmObject> mapCursorToList(Cursor moviesCursor){
        List<MovieRealmObject> movieFavList = new ArrayList<>();

        while (moviesCursor.moveToNext()){
            int id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(MovieColumns._ID));
            double popularity = moviesCursor.getDouble(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_POPULARITY));
            String backdropPath = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_BACKDROP_PATH));
            String posterPath = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_POSTER_PATH));
            String title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_TITLE));
            double rating = moviesCursor.getDouble(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_VOTE_AVERAGE));
            String overview = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_OVERVIEW));
            String releaseDate = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_RELEASE_DATE));
            //convert genre id from string into integer List
            String genreId = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_GENRE_ID));
            String trimResult = genreId.substring(30,genreId.length()-1);
            String[] tmpResult = trimResult.split(",");
            RealmList<Integer> listGenreId = new RealmList<>();
            for(int i=0;i<tmpResult.length;i++){
                listGenreId.add(Integer.parseInt(tmpResult[i]));
            }
            movieFavList.add(new MovieRealmObject(id,popularity,backdropPath,posterPath,title,rating,overview,releaseDate,listGenreId));

        }

        return movieFavList;
    }

    public static MovieRealmObject mapCursorToObject(Cursor moviesCursor){
        moviesCursor.moveToFirst();
        int id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(MovieColumns._ID));
        double popularity = moviesCursor.getDouble(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_POPULARITY));
        String backdropPath = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_BACKDROP_PATH));
        String posterPath = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_POSTER_PATH));
        String title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_TITLE));
        double rating = moviesCursor.getDouble(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_VOTE_AVERAGE));
        String overview = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_OVERVIEW));
        String releaseDate = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_RELEASE_DATE));
        //convert genre id from string into integer List
        String genreId = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_GENRE_ID));
        String trimResult = genreId.substring(30,genreId.length()-1);
        String[] tmpResult = trimResult.split(",");
        RealmList<Integer> listGenreId = new RealmList<>();
        for(int i=0;i<tmpResult.length;i++){
            listGenreId.add(Integer.parseInt(tmpResult[i]));
        }

        return new MovieRealmObject(id,popularity,backdropPath,posterPath,title,rating,overview,releaseDate,listGenreId);
    }
}
