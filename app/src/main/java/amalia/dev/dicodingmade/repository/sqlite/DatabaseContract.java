package amalia.dev.dicodingmade.repository.sqlite;

import android.provider.BaseColumns;

public class DatabaseContract {



    static final class MoviesTable implements BaseColumns{
        static String TABLE_NAME = "Movies";
        static  String _ID = "id";
        static  String COLUMN_NAME_POPULARITY = "popularity";
        static  String COLUMN_NAME_BACKDROP_PATH = "backdrop_path";
        static  String COLUMN_NAME_POSTER_PATH = "poster_path";
        static  String COLUMN_NAME_TITLE = "title";
        static  String COLUMN_NAME_VOTE_AVERAGE = "vote_average";
        static  String COLUMN_NAME_OVERVIEW = "overview";
        static  String COLUMN_NAME_RELEASE_DATE = "release_date";
//        static  String COLUMN_NAME_GENRE_ID = "genre_id";




    }
}
