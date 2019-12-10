package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class ModelsGetMessages {

    @SerializedName("idmessage")
    @Expose
    private Integer idmessage;

    @SerializedName("messageuser")
    @Expose
    private String messageuser;

    @SerializedName("userid")
    @Expose
    private Integer userid;

    @SerializedName("user")
    @Expose
    private ModelsUser user;

    private Integer like = 0;

    //VALORES DE LOS LIKE POR COMENTARIOS
    public Integer likeByCommentary= 0;


    public Integer getIdmessage() {
        return idmessage;
    }

    public String getMessageuser() {
        return messageuser;
    }

    public Integer getUserid() {
        return userid;
    }

    public ModelsUser getUser() {
        return user;
    }


    public Integer getLike() {
        return like;
    }

    public void like(Integer likeByUser){
        if (this.like <= likeByCommentary){
            this.like+=likeByUser;
        }
    }

}
