//package amalia.dev.dicodingmade.provider;
//
//import android.content.ContentProvider;
//import android.content.ContentValues;
//import android.content.UriMatcher;
//import android.database.Cursor;
//import android.database.MatrixCursor;
//import android.net.Uri;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import java.util.Objects;
//
//import amalia.dev.dicodingmade.model.TvShowRealmObject;
//import amalia.dev.dicodingmade.repository.realm.RealmContract;
//import amalia.dev.dicodingmade.repository.realm.RealmHelper;
//import io.realm.Realm;
//import io.realm.RealmConfiguration;
//import io.realm.RealmResults;
//import static amalia.dev.dicodingmade.repository.realm.RealmContract.TvShowColumns;
//
//
//public class TvShowFavProvider extends ContentProvider {
//
//    private Realm realm;
//    private RealmHelper realmHelper;
//
//    //crate urimatcher object
//    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//    static {
//
//
//    }
//    @Override
//    public boolean onCreate() {
//        Realm.init(Objects.requireNonNull(getContext()));
//        //konfigurasi Realm
//        RealmConfiguration configuration = new RealmConfiguration.Builder()
//                .schemaVersion(0)
//                .build();
//        Realm.setDefaultConfiguration(configuration);
//        return true;
//    }
//
//    @Nullable
//    @Override
//    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
//
//        realm = Realm.getDefaultInstance();
//        realmHelper = new RealmHelper(realm);
//        if (uriMatcher.match(uri) == TVSHOW) {
//
//            }
//        } else {
//            throw new UnsupportedOperationException("Unknown uri: " + uri);
//        }
//        matrixTvshow.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(),uri);
//        return matrixTvshow;
//    }
//
//    @Nullable
//    @Override
//    public String getType(@NonNull Uri uri) {
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
//        return null;
//    }
//
//    @Override
//    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
//        int count = 0;
//        int idDeleted;
//        realm = Realm.getDefaultInstance();
//        realmHelper = new RealmHelper(realm);
//        if (uriMatcher.match(uri) == TVSHOW_ID) {
//
//        }
//
//        if(count > 0){
//            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);
//        }
//        return count;
//    }
//
//    @Override
//    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
//        int count = 0;
//        int idUpdate;
//        boolean tmpDelete;
//        realm = Realm.getDefaultInstance();
//        realmHelper = new RealmHelper(realm);
//        if (uriMatcher.match(uri) == TVSHOW_TMP_DELETE) {
//
//        }
//        if(count > 0){
//            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri,null);
//        }
//        return  count;
//
//    }
//}
