package amalia.dev.dicodingmade.repository.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "favorites_db";
    private static final int DATABASE_VERSION = 1;

    //create table movie favorites
    //note:spasi,koma harus diperhatikan
    //todo:genre list belum dimasukkan
    private static final String SQL_CREATE_TABLE_MOVIES = String.format(
      "CREATE TABLE %s"+"(%s INTEGER PRIMARY KEY,"+
              "%s DOUBLE NOT NULL,"+
              "%s TEXT NOT NULL,"+
              "%s TEXT NOT NULL,"+
              "%s TEXT NOT NULL,"+
              "%s DOUBLE NOT NULL,"+
              "%s TEXT NOT NULL,"+
              "%s TEXT NOT NULL)",
            DatabaseContract.MoviesTable.TABLE_NAME,
            DatabaseContract.MoviesTable._ID,
            DatabaseContract.MoviesTable.COLUMN_NAME_POPULARITY,
            DatabaseContract.MoviesTable.COLUMN_NAME_BACKDROP_PATH,
            DatabaseContract.MoviesTable.COLUMN_NAME_POSTER_PATH,
            DatabaseContract.MoviesTable.COLUMN_NAME_TITLE,
            DatabaseContract.MoviesTable.COLUMN_NAME_VOTE_AVERAGE,
            DatabaseContract.MoviesTable.COLUMN_NAME_OVERVIEW,
            DatabaseContract.MoviesTable.COLUMN_NAME_RELEASE_DATE
//            DatabaseContract.MoviesTable.COLUMN_NAME_GENRE_ID
    );

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //eksekusi query create tables
        db.execSQL(SQL_CREATE_TABLE_MOVIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DatabaseContract.MoviesTable.TABLE_NAME);
        onCreate(db);
    }
}
