package amalia.dev.dicodingmade.repository.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import amalia.dev.dicodingmade.model.Movie;

import static amalia.dev.dicodingmade.repository.sqlite.DatabaseContract.MoviesTable.COLUMN_NAME_BACKDROP_PATH;
//import static amalia.dev.dicodingmade.repository.sqlite.DatabaseContract.MoviesTable.COLUMN_NAME_GENRE_ID;
import static amalia.dev.dicodingmade.repository.sqlite.DatabaseContract.MoviesTable.COLUMN_NAME_OVERVIEW;
import static amalia.dev.dicodingmade.repository.sqlite.DatabaseContract.MoviesTable.COLUMN_NAME_POPULARITY;
import static amalia.dev.dicodingmade.repository.sqlite.DatabaseContract.MoviesTable.COLUMN_NAME_POSTER_PATH;
import static amalia.dev.dicodingmade.repository.sqlite.DatabaseContract.MoviesTable.COLUMN_NAME_RELEASE_DATE;
import static amalia.dev.dicodingmade.repository.sqlite.DatabaseContract.MoviesTable.COLUMN_NAME_TITLE;
import static amalia.dev.dicodingmade.repository.sqlite.DatabaseContract.MoviesTable.COLUMN_NAME_VOTE_AVERAGE;
import static amalia.dev.dicodingmade.repository.sqlite.DatabaseContract.MoviesTable.TABLE_NAME;
import static amalia.dev.dicodingmade.repository.sqlite.DatabaseContract.MoviesTable._ID;

public class MovieHelper {
    private static DatabaseHelper databaseHelper;
    private static MovieHelper INSTANCE;
    private static SQLiteDatabase database;

    private MovieHelper(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    //init database
    public static MovieHelper getInstance(Context context){
        if(INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if(INSTANCE == null){
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return  INSTANCE;
    }

    public void open() throws SQLException {
        // Gets the data repository in write mode
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();
        if(database.isOpen()){
            database.close();
        }
    }

    public ArrayList<Movie> getListFavMovies(){
        Cursor cursor = database.query(TABLE_NAME,null,null,null,null,null,_ID+" ASC");
        cursor.moveToFirst();
        ArrayList<Movie> listResult =  new ArrayList<>();
        Movie movie;
        if(cursor.getCount() > 0){
            do{
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setPopularity(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_NAME_POPULARITY)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_POSTER_PATH)));
                movie.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_BACKDROP_PATH)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_TITLE)));
                movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_NAME_VOTE_AVERAGE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_OVERVIEW)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_RELEASE_DATE)));
//                movie.setGenreIds(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_GENRE_ID))); TODO: LIST IN SQLITE

                listResult.add(movie);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return  listResult;

    }

    //checking if there is a movie stored in local (by id)
    public boolean isStored(String id){
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+_ID+" = "+"'"+id+"'";
        Cursor cursor = database.rawQuery(query,null);
      if(cursor.getCount() <= 0){
          cursor.close();
          return false;
      }
      cursor.close();
        return true;
    }

    public long insertFavMovie(Movie movie){
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(_ID,movie.getId());
        values.put(COLUMN_NAME_TITLE,movie.getTitle());
        values.put(COLUMN_NAME_OVERVIEW,movie.getOverview());
        values.put(COLUMN_NAME_POPULARITY,movie.getPopularity());
        values.put(COLUMN_NAME_POSTER_PATH,movie.getPosterPath());
        values.put(COLUMN_NAME_BACKDROP_PATH,movie.getBackdropPath());
        values.put(COLUMN_NAME_VOTE_AVERAGE,movie.getVoteAverage());
        values.put(COLUMN_NAME_RELEASE_DATE,movie.getReleaseDate());
        //GENRE ID NOT YET INSERTED

        // Insert the new row, returning the primary key value of the new row
        long newRow = database.insert(TABLE_NAME,null,values);
        return  newRow;
    }

    public int deleteFavMovie(String id){
        return database.delete(TABLE_NAME,_ID+" = ?",new String[]{id});
    }
}
