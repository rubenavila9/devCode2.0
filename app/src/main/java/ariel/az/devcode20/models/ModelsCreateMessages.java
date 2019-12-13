package ariel.az.devcode20.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelsCreateMessages {


    private String messageuser;

    private Integer messageid;


    public ModelsCreateMessages(String messageuser, Integer messageid) {
        this.messageuser = messageuser;
        this.messageid = messageid;
    }


}
