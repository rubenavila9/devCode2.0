package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelsReconstrour {

    @SerializedName("allLikes")
    @Expose

    private ArrayList<ModelsReconstruir> modelsLikesLists = null;

    public ArrayList<ModelsReconstruir> getModelsLikesLists() {
        return modelsLikesLists;
    }
}
