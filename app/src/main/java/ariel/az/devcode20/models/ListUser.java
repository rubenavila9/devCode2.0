package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListUser {
    @SerializedName("dataUser")
    @Expose
    private ArrayList<ModelsUser> modelsUser = null;
    public ArrayList<ModelsUser> getModelsUser() {
        return modelsUser;
    }
}
