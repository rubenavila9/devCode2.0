package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelsSendLikes {


    private Integer idmessage;

    private Integer likePublication;


    public ModelsSendLikes(Integer idmessage, Integer likePublication) {
        this.idmessage = idmessage;
        this.likePublication = likePublication;
    }
}
