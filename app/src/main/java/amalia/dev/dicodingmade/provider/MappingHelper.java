package amalia.dev.dicodingmade.provider;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import amalia.dev.dicodingmade.model.MovieRealmObject;
import amalia.dev.dicodingmade.repository.realm.RealmContract;
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
            /*genre id belum*/
            movieFavList.add(new MovieRealmObject(id,popularity,backdropPath,posterPath,title,rating,overview,releaseDate));
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

        return new MovieRealmObject(id,popularity,backdropPath,posterPath,title,rating,overview,releaseDate);
    }
}
