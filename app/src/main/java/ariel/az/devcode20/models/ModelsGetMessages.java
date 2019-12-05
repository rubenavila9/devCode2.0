package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelsGetMessages {

    @SerializedName("messageuser")
    @Expose
    private String messageuser;

    @SerializedName("userid")
    @Expose
    private Integer userid;

    public String getMessageuser() {
        return messageuser;
    }

    public Integer getUserid() {
        return userid;
    }
}
