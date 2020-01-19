package ariel.az.devcode20.SharedPreferencesUser;

import android.content.SharedPreferences;

public class SaveDataUser {


    public static Integer getIdUser(SharedPreferences preferences){
        return preferences.getInt("idUser",0);
    }

    public static String getEmailUser(SharedPreferences preferences){
        return preferences.getString("emailUser", "");
    }


    public static String getToken(SharedPreferences preferences){
        return preferences.getString("tokenUser", "");
    }

    public static String getRoleUser(SharedPreferences preferences){
        return preferences.getString("roleUser", "");
    }

    public static String getImgUser(SharedPreferences preferences){
        return preferences.getString("imgUser", "");
    }

    public static String getNameUser(SharedPreferences preferences){
        return preferences.getString("nameUser","");
    }

}
