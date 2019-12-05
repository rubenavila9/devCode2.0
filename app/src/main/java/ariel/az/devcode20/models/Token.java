package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("authToken")
    @Expose
    private String authToken;
    public String getAuthToken() {
        return authToken;
    }


    @SerializedName("photo")
    @Expose
    private String urlImg;
    public String getUrlImg() {
        return urlImg;
    }
}
