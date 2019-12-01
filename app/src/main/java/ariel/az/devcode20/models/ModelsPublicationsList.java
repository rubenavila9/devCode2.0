package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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


    public Integer getIdpublication() {
        return idpublication;
    }

    public void setIdpublication(Integer idpublication) {
        this.idpublication = idpublication;
    }

    public String getNamepublication() {
        return namepublication;
    }

    public void setNamepublication(String namepublication) {
        this.namepublication = namepublication;
    }

    public String getDescriptpublication() {
        return descriptpublication;
    }

    public void setDescriptpublication(String descriptpublication) {
        this.descriptpublication = descriptpublication;
    }
}

