package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelsUser {

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
}
