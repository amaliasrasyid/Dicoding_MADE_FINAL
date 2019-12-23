package amalia.dev.dicodingmade.repository.local;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**CONFIGURATION REALM DB
 * this class for purpose init database
 * and that is done just for once
 * */
public class DbRealm extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Realm
        Realm.init(this);
        //konfigurasi Realm
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("made.db")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}
