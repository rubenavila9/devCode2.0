package ariel.az.devcode20.configurationAndRouters;

import ariel.az.devcode20.models.ListPublications;
import ariel.az.devcode20.models.ModelLogin;
import ariel.az.devcode20.models.ModelsCreateMessages;
import ariel.az.devcode20.models.ModelsPublications;
import ariel.az.devcode20.models.ModelsUser;
import ariel.az.devcode20.models.Token;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Router {


    //    --------------------Iniciar sesion-------------

    @POST("user/login")
    Call<Token> login(@Body ModelLogin login);

    //---------------------obtener todas las publicaciones-------------------


    @GET("publications/home")
    Call<ListPublications> LIST_PUBLICATIONS_CALL();


    @GET("user/{id}")
    Call<ModelsUser> setId(@Path("id") int id);

    //--------------------------------------------------------------------------







    //    -----------------------------------------------------------------------------------------------
    @GET("user/publications")
    Call<ResponseBody> setSecret(@Header("auto-token")String autoToken);
    @Headers({
            "Cache-Control: max-age=3600",
            "User-Agent: Android"
    })
    @POST("messages/create")
    Call<ModelsCreateMessages> createMessages (@Header("auto-token")String autoToken,
                                                  @Body ModelsCreateMessages modelsCreateMessages);
//    -----------------------------------------------------------------------------------------------------




}
