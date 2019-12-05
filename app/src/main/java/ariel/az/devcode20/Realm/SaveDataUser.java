package ariel.az.devcode20.Realm;

import android.content.SharedPreferences;

public class SaveDataUser {
    public static String getEmailPrefers(SharedPreferences preferences){
        return preferences.getString("email", "");
    }

    public static String getPassPrefers(SharedPreferences preferences){
        return preferences.getString("pass", "");
    }

    public static String getToken(SharedPreferences preferences){
        return preferences.getString("token", "");
    }

    public static String getImgUser(SharedPreferences preferences){
        return preferences.getString("imgUser", "");
    }

}
