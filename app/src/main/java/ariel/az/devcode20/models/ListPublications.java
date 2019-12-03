package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ListPublications {

    @SerializedName("publications")
    @Expose
    private ArrayList<ModelsPublicationsList> modelsPublicationsLists = null;

    public ArrayList<ModelsPublicationsList> getModelsPublicationsLists() {
        return modelsPublicationsLists;
    }


}

