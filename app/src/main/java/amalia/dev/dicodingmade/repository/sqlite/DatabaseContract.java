package amalia.dev.dicodingmade.repository.sqlite;

import android.provider.BaseColumns;

class DatabaseContract {



    static final class MoviesTable implements BaseColumns{
        static final String TABLE_NAME = "Movies";
        static final String _ID = "id";
        static final String COLUMN_NAME_POPULARITY = "popularity";
        static final String COLUMN_NAME_BACKDROP_PATH = "backdrop_path";
        static final String COLUMN_NAME_POSTER_PATH = "poster_path";
        static final String COLUMN_NAME_TITLE = "title";
        static final String COLUMN_NAME_VOTE_AVERAGE = "vote_average";
        static final String COLUMN_NAME_OVERVIEW = "overview";
        static final String COLUMN_NAME_RELEASE_DATE = "release_date";
//        static  String COLUMN_NAME_GENRE_ID = "genre_id";




    }
}
