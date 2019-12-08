package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import okhttp3.RequestBody;

public class ModelsRegister {



    private String nameUser;



    private String emailUser;


    public ModelsRegister(String nameUser, String emailUser) {
        this.nameUser = nameUser;
        this.emailUser = emailUser;
    }
}
