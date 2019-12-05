package ariel.az.devcode20.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ariel.az.devcode20.R;
import ariel.az.devcode20.Realm.SaveDataUser;
import ariel.az.devcode20.configurationAndRouters.Router;
import ariel.az.devcode20.configurationAndRouters.conexion;
import ariel.az.devcode20.models.ModelLogin;
import ariel.az.devcode20.models.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Button ButtonLogin, ButtonRegister;
    private EditText email, pass;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        if (!TextUtils.isEmpty(SaveDataUser.getToken(preferences))){
            startActivity(new Intent(this,Principal.class));
        }
        setContentView(R.layout.activity_login);
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
                    String imgUser = response.body().getUrlImg();
                    saveSharepreferences(token, imgUser);
                    Intent intent = new Intent(LoginActivity.this,Principal.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "tenemos problemas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveSharepreferences(String token,String imgUser){
        //guardar el token
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", token);
        editor.putString("imgUser",imgUser);
        editor.apply();

    }
}