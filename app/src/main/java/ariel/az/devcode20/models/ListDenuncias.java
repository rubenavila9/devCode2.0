package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListDenuncias {

    @SerializedName("complemeints")
    @Expose
    private ArrayList<ArrayListDenuncias>arrayListDenuncias = null;

    public ArrayList<ArrayListDenuncias> getArrayListDenuncias() {
        return arrayListDenuncias;
    }
}
