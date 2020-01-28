package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class ModelsPublicationsList {

    @SerializedName("idpublication")
    @Expose
    private Integer idpublication;

    @SerializedName("namepublication")
    @Expose
    private String namepublication;

    @SerializedName("descriptpublication")
    @Expose
    private String descriptpublication;

    @SerializedName("levelsubject")
    @Expose
    private Integer levelSubject;

    @SerializedName("userIduser")
    @Expose
    private Integer userid;

    @SerializedName("photopublt")
    @Expose
    private String photopublt;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("user")
    @Expose
    private ModelsUser iduser;


    public Integer getIdpublication() {
        return idpublication;
    }

    public String getNamepublication() {
        return namepublication;
    }


    public String getDescriptpublication() {
        return descriptpublication;
    }

    public Integer getLevelSubject() {
        return levelSubject;
    }

    public String getPhotopublt() {
        return photopublt;
    }

    public Integer getUserid() {
        return userid;
    }


    public ModelsUser getIduser() {
        return iduser;
    }

    public String getDate() {
        return date;
    }
}

