package ariel.az.devcode20.connect;

import ariel.az.devcode20.models.Login;
import ariel.az.devcode20.models.ModelsPublications;
import ariel.az.devcode20.models.Token;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Router {


    //    --------------------------------------------------------------------------------------------------------
    @POST("user/login")
    Call<Token> login(@Body Login login);

    @GET("user/publications")
    Call<ResponseBody> setSecret(@Header("auto-token")String autoToken);


    //    -----------------------------------------------------------------------------------------------
    @Headers({
            "Cache-Control: max-age=3600",
            "User-Agent: Android"
    })
    @POST("publications/create")
    Call<ModelsPublications> createPublications(@Header("auto-token")String autoToken,
                                                @Body ModelsPublications modelsPublications);
//    -----------------------------------------------------------------------------------------------------



}
