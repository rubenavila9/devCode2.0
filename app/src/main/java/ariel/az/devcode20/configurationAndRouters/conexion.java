package ariel.az.devcode20.configurationAndRouters;

import ariel.az.devcode20.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class conexion {
    //confiracion de conecion no tocar!!
    private static String BASE_URL = "http://18.191.30.52:1000/dev/";


    private static HttpLoggingInterceptor.Level LEVEL_LOG = HttpLoggingInterceptor.Level.BODY;


    private static Retrofit getRetrofitInstance(){
        return new Retrofit.Builder().baseUrl(BASE_URL).client(getClient()).
                addConverterFactory(GsonConverterFactory.create()).build();
    }

    private static OkHttpClient getClient() {
        OkHttpClient.Builder builderClientHttp = new OkHttpClient().newBuilder();
        // Show HTTPS logs in dev mode
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(LEVEL_LOG);
            builderClientHttp.addInterceptor(interceptor);
        }
        return builderClientHttp.build();
    }

    public static Router getApiService(){
        return getRetrofitInstance().create(Router.class);
    }

}


