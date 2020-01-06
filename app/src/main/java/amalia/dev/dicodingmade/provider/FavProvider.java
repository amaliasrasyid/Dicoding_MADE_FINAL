package amalia.dev.dicodingmade.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.Objects;

import javax.annotation.Nullable;

import amalia.dev.dicodingmade.model.GenreRealmObject;
import amalia.dev.dicodingmade.model.MovieRealmObject;
import amalia.dev.dicodingmade.model.TvShowRealmObject;
import amalia.dev.dicodingmade.repository.MappingHelper;
import amalia.dev.dicodingmade.repository.realm.RealmContract;
import amalia.dev.dicodingmade.repository.realm.RealmHelper;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

import static amalia.dev.dicodingmade.repository.realm.RealmContract.AUTHORITY;
import static amalia.dev.dicodingmade.repository.realm.RealmContract.MovieColumns;
import static amalia.dev.dicodingmade.repository.realm.RealmContract.TvShowColumns;
import static amalia.dev.dicodingmade.repository.realm.RealmContract.GenreColumns;

public class FavProvider extends ContentProvider {
    private Realm realm;
    private RealmHelper realmHelper;
    //id code for uri matcher
    private static final int MOVIE = 10;
    private static final int MOVIE_ID =11;
    private static final int MOVIE_TMP_DELETE = 12;
    private static final int TVSHOW = 20;
    private static final int TVSHOW_ID = 21;
    private static final int TVSHOW_TMP_DELETE = 22;
    private static final int GENRE = 30;
    private static final int GENRE_ID = 31;


    MatrixCursor matrixGenre = new MatrixCursor(new String[]{
            GenreColumns._ID,GenreColumns.COLUMN_NAME_NAME
    });

    // Creates a UriMatcher object.
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        //create for access to table (get all data ) & id
        //create URI content://amalia.dev.dicodingmade/movie
        uriMatcher.addURI(AUTHORITY, MovieColumns.TABLE_NAME,MOVIE);

        //create URI content://amalia.dev.dicodingmade/movie/id
        uriMatcher.addURI(AUTHORITY,MovieColumns.TABLE_NAME+"/#",MOVIE_ID);

        //create URI content://amalia.dev.dicodingmade/movie/{tmpDelete}/{id}
        uriMatcher.addURI(AUTHORITY,MovieColumns.TABLE_NAME+"/*/#",MOVIE_TMP_DELETE);

        //create URI content://amalia.dev.dicodingmade/tvshow
        uriMatcher.addURI(AUTHORITY, TvShowColumns.TABLE_NAME,TVSHOW);

        //create URI content://amalia.dev.dicodingmade/tvshow/id
        uriMatcher.addURI(AUTHORITY, TvShowColumns.TABLE_NAME+"/#",TVSHOW_ID);

        //create URI content://amalia.dev.dicodingmade/tvshow/{tmpDelete}/{id}
        uriMatcher.addURI(AUTHORITY, TvShowColumns.TABLE_NAME+"/*/#",TVSHOW_TMP_DELETE);
    }
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return  null;
    }//not used

    public FavProvider() {  }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        int count = 0;
        int idUpdate;
        boolean tmpDelete;
        realm =Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);
        switch (uriMatcher.match(uri)) {
            case MOVIE_TMP_DELETE:
                idUpdate = Integer.parseInt(uri.getPathSegments().get(2));//id movie
                tmpDelete = Boolean.parseBoolean(uri.getPathSegments().get(1));//false-true value
                realmHelper.updateTmpDeleteM(idUpdate, tmpDelete);
                count++;
            break;
            case TVSHOW_TMP_DELETE:
                idUpdate = Integer.parseInt(uri.getPathSegments().get(2));//id tvshow
                tmpDelete = Boolean.parseBoolean(uri.getPathSegments().get(1));//false-true value
                realmHelper.updateTmpDeleteTS(idUpdate, tmpDelete);
                count++;
            break;

        }
        if(count > 0){
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);
        }

        return  count; //return number updated rows
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int count = 0;
        int idDeleted;
        //instance realm
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);
        switch (uriMatcher.match(uri)) {
            case MOVIE_ID:
                idDeleted = Integer.parseInt(uri.getPathSegments().get(1));
                realmHelper.deleteFavMovies(idDeleted);
            count++;
            break;
            case TVSHOW_ID:
                idDeleted = Integer.parseInt(uri.getPathSegments().get(1));
                realmHelper.deleteFavTvShow(idDeleted);
                count++;
            break;
            default:
            throw new UnsupportedOperationException("Not yet implemented");
        }
        if(count > 0){
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);
        }

        return  count;//return number deleted rows
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        /**1.first create RealmObject. the data from ContentValues (parameter)
            2. execute insert with the object that have been created
            3. return in uri form
         * */
        // TODO: Implement this to handle requests to insert a new row.
        Uri mUri;
        int id;
        RealmList<Integer> listGenreId;
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);
        switch (uriMatcher.match(uri)){
            case MOVIE:
                id =(Integer)values.get(MovieColumns._ID);
                MovieRealmObject movie = new MovieRealmObject();
                //set value object
                movie.setId(id);
                movie.setTitle(values.getAsString(MovieColumns.COLUMN_NAME_TITLE));
                movie.setBackdropPath(values.getAsString(MovieColumns.COLUMN_NAME_BACKDROP_PATH));
                movie.setPosterPath(values.get(MovieColumns.COLUMN_NAME_POSTER_PATH).toString());
                movie.setPopularity(values.getAsDouble(MovieColumns.COLUMN_NAME_POPULARITY));
                movie.setOverview(values.getAsString(MovieColumns.COLUMN_NAME_OVERVIEW));
                movie.setVoteAverage(values.getAsDouble(MovieColumns.COLUMN_NAME_VOTE_AVERAGE));
                movie.setReleaseDate(values.getAsString(MovieColumns.COLUMN_NAME_RELEASE_DATE));
                //convert string list id value into realmlist<Integer>
                listGenreId = stringToRealmList(values.getAsString(MovieColumns.COLUMN_NAME_GENRE_ID));
                movie.setGenreIds(listGenreId);
                //execute insert db
                realmHelper.insertMovie(movie);
                mUri = ContentUris.withAppendedId(MovieColumns.CONTENT_URI,id);
                break;
            case TVSHOW:
                id = values.getAsInteger(TvShowColumns._ID);
                TvShowRealmObject tvshow = realm.createObject(TvShowRealmObject.class,id);
                //set value object
                tvshow.setPopularity(values.getAsDouble(TvShowColumns.COLUMN_NAME_POPULARITY));
                tvshow.setBackdropPath(values.getAsString(TvShowColumns.COLUMN_NAME_BACKDROP_PATH));
                tvshow.setPosterPath(values.getAsString(TvShowColumns.COLUMN_NAME_POSTER_PATH));
                tvshow.setOriginalName(values.getAsString(TvShowColumns.COLUMN_NAME_ORIGINAL_TITLE));
                tvshow.setVoteAverage(values.getAsDouble(TvShowColumns.COLUMN_NAME_VOTE_AVERAGE));
                tvshow.setOverview(values.getAsString(TvShowColumns.COLUMN_NAME_OVERVIEW));
                tvshow.setFirstAirDate(values.getAsString(TvShowColumns.COLUMN_NAME_FIRST_RELEASE_DATE));
                //convert string list id value into realmlist<Integer>
                 listGenreId= stringToRealmList(values.getAsString(MovieColumns.COLUMN_NAME_GENRE_ID));
                tvshow.setGenreIds(listGenreId);

                //execute insert db
                realmHelper.insertTvShow(tvshow);
                mUri = ContentUris.withAppendedId(TvShowColumns.CONTENT_URI,id);

                break;
            case GENRE:
                id = values.getAsInteger(GenreColumns._ID);
                GenreRealmObject genre = realm.createObject(GenreRealmObject.class,id);
                //set value object
                genre.setId(id);
                genre.setName(values.getAsString(GenreColumns.COLUMN_NAME_NAME));

                //execute insert db
                realmHelper.insertGenre(genre);
                mUri = ContentUris.withAppendedId(GenreColumns.CONTENT_URI,id);
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);
        return mUri;
    }

    @Override
    public boolean onCreate() {
        Realm.init(Objects.requireNonNull(getContext()));
        //konfigurasi Realm
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(configuration);

        return true;
    }

    //get or read data
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        MatrixCursor matrixMovie = new MatrixCursor(new String[]{
                MovieColumns._ID,MovieColumns.COLUMN_NAME_OVERVIEW,MovieColumns.COLUMN_NAME_TITLE,
                MovieColumns.COLUMN_NAME_BACKDROP_PATH,MovieColumns.COLUMN_NAME_GENRE_ID,
                MovieColumns.COLUMN_NAME_POPULARITY,MovieColumns.COLUMN_NAME_VOTE_AVERAGE,
                MovieColumns.COLUMN_NAME_POSTER_PATH,MovieColumns.COLUMN_NAME_RELEASE_DATE,MovieColumns.COLUMN_NAME_TMP_DELETE
        });
        MatrixCursor matrixTvshow=  new MatrixCursor(new String[]{
                RealmContract.TvShowColumns._ID, RealmContract.TvShowColumns.COLUMN_NAME_BACKDROP_PATH, RealmContract.TvShowColumns.COLUMN_NAME_FIRST_RELEASE_DATE,
                RealmContract.TvShowColumns.COLUMN_NAME_GENRE_ID, RealmContract.TvShowColumns.COLUMN_NAME_ORIGINAL_TITLE, RealmContract.TvShowColumns.COLUMN_NAME_OVERVIEW,
                RealmContract.TvShowColumns.COLUMN_NAME_POPULARITY, RealmContract.TvShowColumns.COLUMN_NAME_POSTER_PATH, RealmContract.TvShowColumns.COLUMN_NAME_VOTE_AVERAGE, RealmContract.TvShowColumns.COLUMN_NAME_TMP_DELETE
        });
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);
        switch (uriMatcher.match(uri)){
            case MOVIE:
                RealmResults<MovieRealmObject> movieResults = realmHelper.getListFavoriteMovies();
                //convert data realmresult
                for(MovieRealmObject movie : movieResults){
                    Object[] rowData = new Object[]{movie.getId(),movie.getOverview(),movie.getTitle(),movie.getBackdropPath(),
                            movie.getGenreIds(),movie.getPopularity(),movie.getVoteAverage(),movie.getPosterPath(),movie.getReleaseDate(),movie.isTmpDelete()};
                    //insert into cursor
                    matrixMovie.addRow(rowData);
                }
                break;
            case MOVIE_ID:

                break;
            case TVSHOW:
                RealmResults<TvShowRealmObject> tvshowResults = realmHelper.getListFavoriteTvShows();
                for (TvShowRealmObject tvshow : tvshowResults) {
                    Object[] rowData = new Object[]{tvshow.getId(), tvshow.getBackdropPath(), tvshow.getFirstAirDate(),
                            tvshow.getGenreIds(), tvshow.getOriginalName(), tvshow.getOverview(), tvshow.getPopularity(), tvshow.getPosterPath(),
                            tvshow.getVoteAverage(), tvshow.isTmpDelete()};
                    //insert into cursor
                    matrixTvshow.addRow(rowData);
                }
                break;
//
//            case GENRE_ID:
//                matrixCursor = matrixGenre;
//                int id = Integer.parseInt(uri.getPathSegments().get(1));
//                GenreRealmObject genreResult = realmHelper.getGenre(id);
//                matrixCursor.addRow(new Object[]{
//                        genreResult.getId(),genreResult.getName()
//                });
//                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        //This line will let CursorLoader know about any data change on "uri" , So that data will be reloaded to CursorLoader(or at least that was said)
        if(matrixMovie.getCount() != 0){
            matrixMovie.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(),uri);
            return  matrixMovie;
        }else{
            matrixTvshow.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(),uri);
            return matrixTvshow;
        }

    }

     private static  RealmList<Integer> stringToRealmList(String textGenreId){
        //convert genre id from string into integer List

        String trimResult = textGenreId.substring(14,textGenreId.length()-1);
        String[] tmpResult = trimResult.split(",");
        RealmList<Integer> listGenreId = new RealmList<>();
        for (String s : tmpResult) {
            listGenreId.add(Integer.parseInt(s));
        }
        return  listGenreId;
    }

}
