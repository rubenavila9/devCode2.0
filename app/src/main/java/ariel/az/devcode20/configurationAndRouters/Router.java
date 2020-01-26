package ariel.az.devcode20.configurationAndRouters;

import ariel.az.devcode20.Activities.accionendenuncias;
import ariel.az.devcode20.models.*;
import okhttp3.MultipartBody;
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
    @POST("user/create")
    Call<ModelsMensajes> routerCrearCuenta( @Part MultipartBody.Part photo,
                                            @Part("nameUser") RequestBody  nameUser,
                                            @Part("emailUser") RequestBody emailUser,
                                            @Part("passUser") RequestBody passUser,
                                            @Part("roleUser") RequestBody roleUser);



    //---------------------------------crear publications ---------------------------------
    @Multipart
    @POST("publications/create")
    Call<ModelsMensajes> routerCrearPublications(@Part MultipartBody.Part photo,
                                                 @Part("namePublication") RequestBody namePublication,
                                                 @Part("descriptPublication") RequestBody descriptPublication,
                                                 @Part("levelSubject") RequestBody levelSubject,
                                                 @Part("iduser") RequestBody iduser);



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
    Call<ModelsMensajes> routerActualizarComentario(@Body ModelsCreateMessages modelsCreateMessages);

    //----------------------------------obtener los likes (revision)-------------------------------------------------------------------

    @GET("extras/getLikes")
    Call<ModelsSendReportes> modelsLikesListCall();

    //-------------------------------------envio de los likes ------------------------------------------
    @POST("extras/createLikes")
    Call<ModelsMensajes> routerEnviarLikes(@Header("auto-token")String autoToken,
                                           @Body ModelsCreateLikes modelsCreateLikes);

    //--------------------------------------enviar loas denuncias---------------------------------------

    @POST("extras/create/createComplemeints")
    Call<ModelsMensajes> routerReportarDenuncias(@Header("auto-token")String autoToken,
                                                 @Body ModelsSendReportes modelsSendReportes);

    //--------------------------------------editar las publicaciones -------------------------------------

    @POST("publications/updatePublication")
    Call<ModelsMensajes> routerEditarPublicaciones(@Body ModelsPublications modelsPublications);

    //-----------------------------------------------obtener las denuncias--------------------------------

    @GET("extras/getAllComplemeint")
    Call<ListDenuncias> routerDenuncias();

    //-------------------------------------------------------buscar publicaciones con el id---------------------------------------
    @GET("publications/{levelsubject}")
    Call<ListPublications> obtenerPublicacionesId(@Path("levelsubject") Integer levelsubject);




    //    -----------------------------------------------------------------------------------------------
    @GET("user/dataUser")
    Call<ModelsUser> setSecret(@Header("auto-token")String autoToken);
    @Headers({
            "Cache-Control: max-age=3600",
            "User-Agent: Android"
    })

    // TODO: 12/27/2019 ruta para la creacion del mensaje
    @POST("messages/create")
    Call<ModelsCreateMessages> createMessages (@Header("auto-token")String autoToken,
                                                  @Body ModelsCreateMessages modelsCreateMessages);




    //TODO: 1/18/2020 OBTENER LOS LIKES DE CADA USUARIO
    @GET("user/getPoints")
    Call<ModelsPoints> setPoints(@Header("auto-token") String autoToken);



    // TODO: 1/25/2020 -----------------------------enviar el bloqueo o desbloqueo de la cuenta del usuario

    @POST("extras/lock")
    Call<ModelsMensajes> setBloqueoCuenta(@Body accionendenuncias.ModelsPermiss modelsPermiss);


}
