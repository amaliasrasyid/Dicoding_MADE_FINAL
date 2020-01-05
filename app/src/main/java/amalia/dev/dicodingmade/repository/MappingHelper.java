package amalia.dev.dicodingmade.repository;

import android.database.Cursor;

import java.util.ArrayList;

import amalia.dev.dicodingmade.model.MovieRealmObject;
import amalia.dev.dicodingmade.model.TvShowRealmObject;
import amalia.dev.dicodingmade.repository.realm.RealmContract;
import io.realm.RealmList;

import static amalia.dev.dicodingmade.repository.realm.RealmContract.MovieColumns;
import static amalia.dev.dicodingmade.repository.realm.RealmContract.TvShowColumns;


public class MappingHelper {

    public static ArrayList<MovieRealmObject> mCursorToArrayList(Cursor moviesCursor){
        ArrayList<MovieRealmObject> movieFavList = new ArrayList<>();


        while (moviesCursor.moveToNext()){
            int id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(MovieColumns._ID));
            double popularity = moviesCursor.getDouble(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_POPULARITY));
            String backdropPath = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_BACKDROP_PATH));
            String posterPath = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_POSTER_PATH));
            String title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_TITLE));
            double rating = moviesCursor.getDouble(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_VOTE_AVERAGE));
            String overview = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_OVERVIEW));
            String releaseDate = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_RELEASE_DATE));
            String genreId = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(MovieColumns.COLUMN_NAME_GENRE_ID));
            RealmList<Integer> listGenreId = stringToRealmList(genreId);
            movieFavList.add(new MovieRealmObject(id,popularity,backdropPath,posterPath,title,rating,overview,releaseDate,listGenreId));

        }

        return movieFavList;
    }

    public static ArrayList<TvShowRealmObject> tsCursorToArrayList(Cursor tvshowsCursor){
        ArrayList<TvShowRealmObject> tvshowFavList = new ArrayList<>();

        while (tvshowsCursor.moveToNext()){
        int id = tvshowsCursor.getInt(tvshowsCursor.getColumnIndexOrThrow(TvShowColumns._ID));
        double popularity = tvshowsCursor.getDouble(tvshowsCursor.getColumnIndexOrThrow(TvShowColumns.COLUMN_NAME_POPULARITY));
        String backdropPath = tvshowsCursor.getString(tvshowsCursor.getColumnIndexOrThrow(TvShowColumns.COLUMN_NAME_BACKDROP_PATH));
        String posterPath = tvshowsCursor.getString(tvshowsCursor.getColumnIndexOrThrow(TvShowColumns.COLUMN_NAME_POSTER_PATH));
        String originalTitle = tvshowsCursor.getString(tvshowsCursor.getColumnIndexOrThrow(TvShowColumns.COLUMN_NAME_ORIGINAL_TITLE));
        double rating = tvshowsCursor.getDouble(tvshowsCursor.getColumnIndexOrThrow(TvShowColumns.COLUMN_NAME_VOTE_AVERAGE));
        String overview = tvshowsCursor.getString(tvshowsCursor.getColumnIndexOrThrow(TvShowColumns.COLUMN_NAME_OVERVIEW));
        String firstReleaseDate = tvshowsCursor.getString(tvshowsCursor.getColumnIndexOrThrow(TvShowColumns.COLUMN_NAME_FIRST_RELEASE_DATE));
        String genreId = tvshowsCursor.getString(tvshowsCursor.getColumnIndexOrThrow(TvShowColumns.COLUMN_NAME_GENRE_ID));
        RealmList<Integer> listGenreId = stringToRealmList(genreId);
        tvshowFavList.add(new TvShowRealmObject(id,popularity,backdropPath,posterPath,originalTitle,rating,overview,firstReleaseDate,listGenreId));
        }

        return tvshowFavList;
    }

    private static  RealmList<Integer> stringToRealmList(String textGenreId){
        //convert genre id from string into integer List

        String trimResult = textGenreId.substring(30,textGenreId.length()-1);
        String[] tmpResult = trimResult.split(",");
        RealmList<Integer> listGenreId = new RealmList<>();
        for (String s : tmpResult) {
            listGenreId.add(Integer.parseInt(s));
        }
        return  listGenreId;
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
