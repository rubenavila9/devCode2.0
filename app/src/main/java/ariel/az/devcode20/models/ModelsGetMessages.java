package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;

public class ModelsGetMessages {

    @SerializedName("messageuser")
    @Expose
    private String messageuser;

    @SerializedName("userid")
    @Expose
    private Integer userid;

    @SerializedName("user")
    @Expose
    private ModelsUser user;

    public String getMessageuser() {
        return messageuser;
    }

    public Integer getUserid() {
        return userid;
    }

    public ModelsUser getUser() {
        return user;
    }
}
