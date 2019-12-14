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

    @SerializedName("userIduser")
    @Expose
    private Integer userid;

    @SerializedName("likepublication")
    @Expose
    private Integer likepublication;

    @SerializedName("complemeints")
    @Expose
    private Integer complemeints;



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



    public Integer getLikepublication() {
        return likepublication;
    }

    public void setLikepublication(Integer likepublication) {
        this.likepublication = likepublication;
    }

    public Integer getComplemeints() {
        return complemeints;
    }

    public ModelsUser getUser() {
        return user;
    }



    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public void like(Integer likeByUser){
        if (this.like <= likepublication){
            this.like+=likeByUser;
        }
    }

}
