package amalia.dev.consumerfavorites.repository;

import android.net.Uri;
import android.provider.BaseColumns;

public class RealmContract {
    private static final String AUTHORITY = "amalia.dev.dicodingmade";
    private static final String SCHEMA = "content";

    public static final class MovieColumns implements BaseColumns{
       static final String TABLE_NAME = "movie";
       static final String _ID = "id";
       static final String COLUMN_NAME_POPULARITY = "popularity";
       static final String COLUMN_NAME_BACKDROP_PATH = "backdrop_path";
       static final String COLUMN_NAME_POSTER_PATH = "poster_path";
       static final String COLUMN_NAME_TITLE = "title";
       static final String COLUMN_NAME_VOTE_AVERAGE = "vote_average";
       static final String COLUMN_NAME_OVERVIEW = "overview";
       static final String COLUMN_NAME_RELEASE_DATE = "release_date";
       static  final String COLUMN_NAME_GENRE_ID = "genre_id";

       //create URI content://amalia.dev.dicodingmade/movie
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEMA)
               .authority(AUTHORITY)
               .appendPath(TABLE_NAME)
               .build();
    }


    public static final class TvShowColumns implements BaseColumns{
        static final String TABLE_NAME = "tvshow";
        static final String _ID = "id";
        static final String COLUMN_NAME_POPULARITY = "popularity";
        static final String COLUMN_NAME_BACKDROP_PATH = "backdrop_path";
        static final String COLUMN_NAME_POSTER_PATH = "poster_path";
        static final String COLUMN_NAME_ORIGINAL_TITLE = "original title";
        static final String COLUMN_NAME_VOTE_AVERAGE = "vote_average";
        static final String COLUMN_NAME_OVERVIEW = "overview";
        static final String COLUMN_NAME_FIRST_RELEASE_DATE = "first release_date";
        static  String COLUMN_NAME_GENRE_ID = "genre_id";


        //create URI content://amalia.dev.dicodingmade/tvshow
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEMA)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

    public static  final class GenreColumns implements BaseColumns{
        static final String TABLE_NAME = "genre";
        public static final String _ID = "id";
        public static final String COLUMN_NAME_GENRE_NAME = "name_genre";

        //create URI content://amalia.dev.dicodingmade/genre
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEMA)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
