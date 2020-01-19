package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelsPoints {

    @SerializedName("points")
    @Expose
    private PointsUsers pointsUsers = null;

    public PointsUsers getPointsUsers() {
        return pointsUsers;
    }
}
