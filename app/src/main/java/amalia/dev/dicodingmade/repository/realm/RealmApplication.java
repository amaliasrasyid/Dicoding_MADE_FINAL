package amalia.dev.dicodingmade.repository.realm;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Realm
        Realm.init(this);
        //konfigurasi Realm
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}