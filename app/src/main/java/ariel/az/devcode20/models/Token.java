package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("pass")
    @Expose
    private boolean pass;

    @SerializedName("authToken")
    @Expose
    private String authToken;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("photo")
    @Expose
    private String urlImg;


    public boolean isPass() {
        return pass;
    }


    public String getAuthToken() {
        return authToken;
    }

    public String getMessage() {
        return message;
    }

    public String getUrlImg() {
        return urlImg;
    }
}
