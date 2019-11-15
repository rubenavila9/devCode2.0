package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Users {

    @SerializedName("users")
    @Expose
    private ArrayList<ModelsUsers> modelsProducts = null;

    public ArrayList<ModelsUsers> getModelsProducts() {
        return modelsProducts;
    }

}

