package ariel.az.devcode20.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PointsUsers {
    @SerializedName("userIduser")
    @Expose
    private Integer userIduser;

    @SerializedName("pointlimit")
    @Expose
    private Integer pointlimit;

    @SerializedName("cantpoint")
    @Expose
    private Integer cantpoint;

    public Integer getUserIduser() {
        return userIduser;
    }

    public Integer getPointlimit() {
        return pointlimit;
    }

    public Integer getCantpoint() {
        return cantpoint;
    }
}
