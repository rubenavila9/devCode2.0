package ariel.az.devcode20.configurationAndRouters;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class myApplication extends Application {

    private Realm realm;
    @Override
    public void onCreate() {
        super.onCreate();
        realm.init(this);
        realm = Realm.getDefaultInstance();
        configurationApplication();
        realm.close();

    }


    private void configurationApplication(){
        RealmConfiguration config = new RealmConfiguration.Builder().name("default.realm")
                .build();
        Realm.setDefaultConfiguration(config);
    }


}
