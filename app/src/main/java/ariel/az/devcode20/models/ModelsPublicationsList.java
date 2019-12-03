package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;

public class ModelsPublicationsList {


    @SerializedName("namepublication")
    @Expose
    private String namepublication;

    @SerializedName("descriptpublication")
    @Expose
    private String descriptpublication;

    @SerializedName("userid")
    @Expose
    private Integer userid;


    public String getNamepublication() {
        return namepublication;
    }


    public String getDescriptpublication() {
        return descriptpublication;
    }


    public Integer getUserid() {
        return userid;
    }
}

