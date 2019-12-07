package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelsListMessages {

    @SerializedName("messages")
    @Expose
    private ArrayList<ModelsGetMessages> modelsGetMessages = null;

    public ArrayList<ModelsGetMessages> getModelsGetMessages() {
        return modelsGetMessages;
    }
}
