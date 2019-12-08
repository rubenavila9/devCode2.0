package ariel.az.devcode20.SharedPreferencesUser;

import android.content.SharedPreferences;

public class SaveDataUser {
    public static String getEmailPrefers(SharedPreferences preferences){
        return preferences.getString("email", "");
    }

    public static String getPassPrefers(SharedPreferences preferences){
        return preferences.getString("pass", "");
    }

    public static String getToken(SharedPreferences preferences){
        return preferences.getString("tokenUser", "");
    }

    public static String getRoleUser(SharedPreferences preferences){
        return preferences.getString("roleUser", "");
    }

}
