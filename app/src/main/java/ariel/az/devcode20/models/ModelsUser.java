package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelsUser {

    @SerializedName("iduser")
    @Expose
    private Integer iduser;

    @SerializedName("nameuser")
    @Expose
    private String nameuser;

    @SerializedName("emailuser")
    @Expose
    private String emailuser;

    @SerializedName("roleuser")
    @Expose
    private String roleuser;

    @SerializedName("photouser")
    @Expose
    private String photouser;


    @SerializedName("points")
    @Expose
    private ArrayList<PointsUsers> points  = null;


    public Integer getIduser() {
        return iduser;
    }

    public String getNameuser() {
        return nameuser;
    }

    public String getEmailuser() {
        return emailuser;
    }

    public String getRoleuser() {
        return roleuser;
    }

    public String getPhotouser() {
        return photouser;
    }

    public ArrayList<PointsUsers> getPoints() {
        return points;
    }
}
