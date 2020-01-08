package amalia.dev.consumerfavorites.repository;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import amalia.dev.consumerfavorites.model.MovieRealmObject;
import amalia.dev.consumerfavorites.model.TvShowRealmObject;
import io.realm.RealmList;

import static amalia.dev.consumerfavorites.repository.RealmContract.MovieColumns;
import static amalia.dev.consumerfavorites.repository.RealmContract.TvShowColumns;


public class MappingHelper {

    public static ArrayList<MovieRealmObject> mCursorToArrayList(Cursor moviesCursor) {
        ArrayList<MovieRealmObject> movieFavList = new ArrayList<>();


        while (moviesCursor.moveToNext()) {
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
            movieFavList.add(new MovieRealmObject(id, popularity, backdropPath, posterPath, title, rating, overview, releaseDate, listGenreId));

        }

        return movieFavList;
    }

    public static ContentValues movieToContentValues(MovieRealmObject movie) {
        ContentValues contentValues = new ContentValues();

        //insert value object into contentvalues
        contentValues.put(MovieColumns._ID, movie.getId());
        contentValues.put(MovieColumns.COLUMN_NAME_POPULARITY, movie.getPopularity());
        contentValues.put(MovieColumns.COLUMN_NAME_BACKDROP_PATH, movie.getBackdropPath());
        contentValues.put(MovieColumns.COLUMN_NAME_POSTER_PATH, movie.getPosterPath());
        contentValues.put(MovieColumns.COLUMN_NAME_TITLE, movie.getTitle());
        contentValues.put(MovieColumns.COLUMN_NAME_VOTE_AVERAGE, movie.getVoteAverage());
        contentValues.put(MovieColumns.COLUMN_NAME_OVERVIEW, movie.getOverview());
        contentValues.put(MovieColumns.COLUMN_NAME_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(MovieColumns.COLUMN_NAME_GENRE_ID, movie.getGenreIds().toString());

        return contentValues;
    }


    public static ArrayList<TvShowRealmObject> tsCursorToArrayList(Cursor tvshowsCursor) {
        ArrayList<TvShowRealmObject> tvshowFavList = new ArrayList<>();

        while (tvshowsCursor.moveToNext()) {
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
            tvshowFavList.add(new TvShowRealmObject(id, popularity, backdropPath, posterPath, originalTitle, rating, overview, firstReleaseDate, listGenreId));
        }

        return tvshowFavList;
    }

    public static ContentValues tvshowToContentValues(TvShowRealmObject tvshow) {
        ContentValues contentValues = new ContentValues();

        //insert value object into content values
        contentValues.put(TvShowColumns._ID, tvshow.getId());
        contentValues.put(TvShowColumns.COLUMN_NAME_POPULARITY, tvshow.getPopularity());
        contentValues.put(TvShowColumns.COLUMN_NAME_BACKDROP_PATH, tvshow.getBackdropPath());
        contentValues.put(TvShowColumns.COLUMN_NAME_POSTER_PATH, tvshow.getPosterPath());
        contentValues.put(TvShowColumns.COLUMN_NAME_ORIGINAL_TITLE, tvshow.getOriginalName());
        contentValues.put(TvShowColumns.COLUMN_NAME_VOTE_AVERAGE, tvshow.getVoteAverage());
        contentValues.put(TvShowColumns.COLUMN_NAME_OVERVIEW, tvshow.getOverview());
        contentValues.put(TvShowColumns.COLUMN_NAME_FIRST_RELEASE_DATE, tvshow.getFirstAirDate());
        contentValues.put(TvShowColumns.COLUMN_NAME_GENRE_ID, tvshow.getGenreIds().toString());

        return contentValues;
    }


    private static RealmList<Integer> stringToRealmList(String textGenreId) {
        //convert genre id from string into integer List

        String trimResult = textGenreId.substring(14, textGenreId.length() - 1);
        String[] tmpResult = trimResult.split(",");
        RealmList<Integer> listGenreId = new RealmList<>();
        for (String s : tmpResult) {
            listGenreId.add(Integer.parseInt(s));
        }
        return listGenreId;
    }


}
