package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArrayListDenuncias {

    @SerializedName("idmessage")
    @Expose
    private Integer idmessage;

    @SerializedName("messageuser")
    @Expose
    private String messageuser;

    @SerializedName("userIduser")
    @Expose
    private Integer userIduser;

    @SerializedName("likepublication")
    @Expose
    private Integer likepublications;

    @SerializedName("complemeints")
    @Expose
    private Integer complemeints;

    @SerializedName("publicationid")
    @Expose
    private Integer publicationsid;

    @SerializedName("user")
    @Expose
    private ModelsUser iduser;

    public Integer getIdmessage() {
        return idmessage;
    }

    public String getMessageuser() {
        return messageuser;
    }

    public Integer getUserIduser() {
        return userIduser;
    }

    public Integer getLikepublications() {
        return likepublications;
    }

    public Integer getComplemeints() {
        return complemeints;
    }

    public Integer getPublicationsid() {
        return publicationsid;
    }

    public ModelsUser getIduser() {
        return iduser;
    }
}
