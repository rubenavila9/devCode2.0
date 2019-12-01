package ariel.az.devcode20.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ariel.az.devcode20.R;
import ariel.az.devcode20.Realm.LoginRealm;
import ariel.az.devcode20.configurationAndRouters.Router;
import ariel.az.devcode20.configurationAndRouters.conexion;
import ariel.az.devcode20.models.ModelLogin;
import ariel.az.devcode20.models.Token;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button ButtonLogin, ButtonRegister;
    private EditText email, pass;
    private Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        realm = Realm.getDefaultInstance();
        ButtonLogin = findViewById(R.id.btnEntry);
        ButtonRegister = findViewById(R.id.btnRegister);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);


        ButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        ButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(email.getText().toString(),pass.getText().toString());
            }
        });
    }

    public  void login(final String email, final String pass){
        Router router = conexion.getApiService();
        ModelLogin login = new ModelLogin(email,pass);
        Call<Token> tokenCall = router.login(login);
        tokenCall.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()){
                    //obtiene el json que me devuelte el backend
                    String token = response.body().getAuthToken();
                    //inicializo realm
                    realm.beginTransaction();
                    LoginRealm loginRealm = new LoginRealm(email,token,pass);
                    realm.copyToRealm(loginRealm);
                    realm.commitTransaction();
                    startActivity(new Intent(LoginActivity.this, Principal.class));
                }
            }
            @Override
            public void onFailure(Call<Token> call, Throwable t) {

            }

        });
    }
}