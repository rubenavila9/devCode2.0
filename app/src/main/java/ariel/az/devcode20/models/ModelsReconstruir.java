package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelsReconstruir {

    @SerializedName("idlike")
    @Expose
    private Integer idlike;

    @SerializedName("messageIdmessage")
    @Expose
    private Integer messageIdmessage;


    @SerializedName("userIduser")
    @Expose
    private Integer userIduser;


    @SerializedName("message")
    @Expose
    private ModelsGetMessages modelsGetMessages;


    @SerializedName("user")
    @Expose
    private ModelsUser modelsUser;




    public Integer getIdlike() {
        return idlike;
    }

    public Integer getMessageIdmessage() {
        return messageIdmessage;
    }

    public Integer getUserIduser() {
        return userIduser;
    }

    public ModelsGetMessages getModelsGetMessages() {
        return modelsGetMessages;
    }

    public ModelsUser getModelsUser() {
        return modelsUser;
    }
}
