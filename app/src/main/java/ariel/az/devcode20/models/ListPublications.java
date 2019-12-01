package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListPublications {

    @SerializedName("publications")
    @Expose
    private ArrayList<ModelsPublicationsList> modelsProducts = null;

    public ArrayList<ModelsPublicationsList> getModelsProducts() {
        return modelsProducts;
    }

}

