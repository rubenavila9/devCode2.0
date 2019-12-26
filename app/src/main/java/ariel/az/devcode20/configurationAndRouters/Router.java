package ariel.az.devcode20.configurationAndRouters;

import ariel.az.devcode20.models.ListPublications;
import ariel.az.devcode20.models.ModelLogin;
import ariel.az.devcode20.models.ModelsCreateLikes;
import ariel.az.devcode20.models.ModelsCreateMessages;
import ariel.az.devcode20.models.ModelsGetMessages;
import ariel.az.devcode20.models.ModelsMensajes;
import ariel.az.devcode20.models.ModelsPublications;
import ariel.az.devcode20.models.ModelsSendReportes;
import ariel.az.devcode20.models.ModelsListMessages;
import ariel.az.devcode20.models.ModelsRegister;
import ariel.az.devcode20.models.ModelsUser;
import ariel.az.devcode20.models.Token;
import ariel.az.devcode20.models.ModelsSendLikes;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Router {


    //    --------------------Iniciar sesion-------------

    @POST("user/login")
    Call<Token> login(@Body ModelLogin login);


    //----------------------crear cuenta de usuario----------------

    @Multipart
    @POST("user/test")
    Call<ModelsRegister> updateUser(@Part("photo") RequestBody photo,
                                    @Part("description") RequestBody description);


    /*@Multipart
    @POST("user/test")
    Call<Object> modelsRegisterCall(
            @Part("nameUser") RequestBody nameUser,
            @Part("emailUser") RequestBody emailUser,
            @Part("roleUser") RequestBody roleUser,
            @Part("passUser") RequestBody passUser);*/



   /* @Multipart
    @POST("user/create")
    Call<ModelsRegister> MODELS_REGISTER_CALL(@Part MultipartBody.Part photo,
                                              @Part ModelsRegister modelsRegister);
*/




    //---------------------obtener todas las publicaciones-------------------


    @GET("publications/home")
    Call<ListPublications> obtenerPublcaciones();


    @GET("user/{id}")
    Call<ModelsUser> setId(@Path("id") int id);

    //-----------------------obtener los mensajes con el id de la publicacion------------------------------

    @GET("messages/{idpublication}")
    Call<ModelsListMessages> obtenerMensajesPorId(@Path("idpublication")Integer id);

    //------------------------------elimininar commentarios--------------------------------------------------

    @DELETE("messages/delete/{idmessage}")
    Call<ModelsMensajes> eliminarComentarios(@Path("idmessage") Integer idmessage);
    //--------------------------------actualizar los likes en la table messages-------------------------------------------------


    @POST("messages/update")
    Call<ModelsSendLikes> updateMessagesCall(@Body ModelsSendLikes modelsCreateMessages);

    //----------------------------------obtener los likes (revision)-------------------------------------------------------------------

    @GET("extras/getLikes")
    Call<ModelsSendReportes> modelsLikesListCall();

    //-------------------------------------envio de los likes --------------------------------------------------------------------

    @POST("extras/createLikes")
    Call<ModelsMensajes> routerEnviarLikes(@Header("auto-token")String autoToken,
                                           @Body ModelsCreateLikes modelsCreateLikes);

    //--------------------------------------enviar loas denuncias-------------------------------------------------------------------------

    @POST("extras/create/createComplemeints")
    Call<ModelsMensajes> routerReportarDenuncias(@Header("auto-token")String autoToken,
                                                 @Body ModelsSendReportes modelsSendReportes);

    //--------------------------------------editar las publicaciones ------------------------------------------------------------------------

    @POST("publications/updatePublication")
    Call<ModelsMensajes> routerEditarPublicaciones(@Body ModelsPublications modelsPublications);
    //-----------------------------------------------------------------------------------------------------------------------------------






    //    -----------------------------------------------------------------------------------------------
    @GET("user/dataUser")
    Call<ModelsUser> setSecret(@Header("auto-token")String autoToken);
    @Headers({
            "Cache-Control: max-age=3600",
            "User-Agent: Android"
    })



    @POST("messages/create")
    Call<ModelsCreateMessages> createMessages (@Header("auto-token")String autoToken,
                                                  @Body ModelsCreateMessages modelsCreateMessages);

//    -----------------------------------------------------------------------------------------------------




}
