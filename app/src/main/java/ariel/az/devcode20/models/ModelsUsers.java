package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelsUsers {
    @SerializedName("iduser")
    @Expose
    private Integer iduser;

    @SerializedName("nameuser")
    @Expose
    private String nameuser;

    @SerializedName("emailuser")
    @Expose
    private String emailuser;

    @SerializedName("passuser")
    @Expose
    private String passuser;

    @SerializedName("photouser")
    @Expose


    private String photouser;

    public String getNameuser() {
        return nameuser;
    }

    public Integer getIduser() {
        return iduser;
    }

    public String getEmailuser() {
        return emailuser;
    }

    public String getPassuser() {
        return passuser;
    }

    public String getPhotouser() {
        return photouser;
    }
}

