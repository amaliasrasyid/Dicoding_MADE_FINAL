package amalia.dev.dicodingmade.repository.realm;

import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URI;

public class RealmContract {
    public static final String AUTHORITY = "amalia.dev.dicodingmade";
    private static final String SCHEMA = "content";

    public static final class MovieColumns implements BaseColumns{
       public static final String TABLE_NAME = "movie";
       public static final String _ID = "id";
       public static final String COLUMN_NAME_POPULARITY = "popularity";
       public static final String COLUMN_NAME_BACKDROP_PATH = "backdrop_path";
       public static final String COLUMN_NAME_POSTER_PATH = "poster_path";
       public static final String COLUMN_NAME_TITLE = "title";
       public static final String COLUMN_NAME_VOTE_AVERAGE = "vote_average";
       public static final String COLUMN_NAME_OVERVIEW = "overview";
       public static final String COLUMN_NAME_RELEASE_DATE = "release_date";
       public static  final String COLUMN_NAME_GENRE_ID = "genre_id";
        public static  final String COLUMN_NAME_TMP_DELETE= "tmp_delete";

       //create URI content://amalia.dev.dicodingmade/movie
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEMA)
               .authority(AUTHORITY)
               .appendPath(TABLE_NAME)
               .build();
    }


    public static final class TvShowColumns implements BaseColumns{
        public static final String TABLE_NAME = "tvshow";
        public static final String _ID = "id";
        public static final String COLUMN_NAME_POPULARITY = "popularity";
        public static final String COLUMN_NAME_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_NAME_POSTER_PATH = "poster_path";
        public static final String COLUMN_NAME_ORIGINAL_TITLE = "original title";
        public static final String COLUMN_NAME_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_NAME_OVERVIEW = "overview";
        public static final String COLUMN_NAME_FIRST_RELEASE_DATE = "first release_date";
        public static  String COLUMN_NAME_GENRE_ID = "genre_id";
        public static  final String COLUMN_NAME_TMP_DELETE= "tmp_delete";

        //create URI content://amalia.dev.dicodingmade/tvshow
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEMA)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

    public static  final class GenreColumns implements BaseColumns{
        public static final String TABLE_NAME = "genre";
        public static final String _ID = "id";
        public static final String COLUMN_NAME_GENRE_NAME = "name_genre";

        //create URI content://amalia.dev.dicodingmade/genre
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEMA)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
