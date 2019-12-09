package ariel.az.devcode20.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ariel.az.devcode20.R;
import ariel.az.devcode20.SharedPreferencesUser.SaveDataUser;
import ariel.az.devcode20.configurationAndRouters.Router;
import ariel.az.devcode20.configurationAndRouters.conexion;
import ariel.az.devcode20.models.ModelLogin;
import ariel.az.devcode20.models.ModelsUser;
import ariel.az.devcode20.models.Token;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Button ButtonLogin, ButtonRegister;
    private EditText email, pass;
    private SharedPreferences preferences;
    private Router router;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        if (!TextUtils.isEmpty(SaveDataUser.getToken(preferences))){
            startActivity(new Intent(this,Principal.class));
        }
        router = conexion.getApiService();
        setContentView(R.layout.activity_login);
        ButtonLogin = findViewById(R.id.btnEntry);
        ButtonRegister = findViewById(R.id.btnRegister);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        ButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
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

        ModelLogin login = new ModelLogin(email,pass);
        Call<Token> tokenCall = router.login(login);
        tokenCall.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()){
                    //obtiene el json que me devuelte el backend
                    String token = response.body().getAuthToken();
                    getPersonalData(token);

                }
            }
            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "tenemos problemas", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getPersonalData(final String token){
        Call<ModelsUser> modelsUserCall = router.setSecret(token);
        modelsUserCall.enqueue(new Callback<ModelsUser>() {
            @Override
            public void onResponse(Call<ModelsUser> call, Response<ModelsUser> response) {
                if (response.isSuccessful()){
                    String roleUser = response.body().getRoleuser();
                    String emailUser = response.body().getEmailuser();
                    saveSharepreferences(token,roleUser,emailUser);
                    Intent intent = new Intent(LoginActivity.this,Principal.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<ModelsUser> call, Throwable t) {
            }
        });
    }

    private void saveSharepreferences(String token,String roleUser,String emailUser){
        //guardar el token
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("tokenUser", token);
        editor.putString("roleUser",roleUser);
        editor.putString("emailUser",emailUser);
        editor.apply();

    }


}