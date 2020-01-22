package ariel.az.devcode20.Activities;

import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
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
import ariel.az.devcode20.SharedPreferencesUser.SaveDataUser;
import ariel.az.devcode20.configurationAndRouters.Router;
import ariel.az.devcode20.configurationAndRouters.conexion;
import ariel.az.devcode20.models.ModelLogin;
import ariel.az.devcode20.models.ModelsUser;
import ariel.az.devcode20.models.PointsUsers;
import ariel.az.devcode20.models.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private Button ButtonLogin, ButtonRegister;
    private EditText email, pass;
    private SharedPreferences preferences;
    private Router router;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        if (!TextUtils.isEmpty(SaveDataUser.getToken(preferences))){
            startActivity(new Intent(this,Principal.class));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().show();

        router = conexion.getApiService();
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
                checkFields(email.getText().toString().trim(),pass.getText().toString().trim());
            }
        });
    }

    private void checkFields(final String email, final String pass){
        //TODO VALIDAR CAMPOS
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Llene campos!", Toast.LENGTH_SHORT).show();
        }else {
            getLogin(email,pass);
        }
    }



    private  void getLogin(final String email, final String pass){
        // TODO: 12/26/2019 iniciar session
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
        // TODO: 12/26/2019 obtener la informacion del usuario logueado

        Call<ModelsUser> modelsUserCall = router.setSecret(token);
        modelsUserCall.enqueue(new Callback<ModelsUser>() {
            @Override
            public void onResponse(Call<ModelsUser> call, Response<ModelsUser> response) {
                if (response.isSuccessful()){
                    Integer idUser = response.body().getIduser();
                    String roleUser = response.body().getRoleuser();
                    String emailUser = response.body().getEmailuser();
                    String photoUser = response.body().getPhotouser();
                    String nameUser = response.body().getNameuser();
                    ArrayList<PointsUsers> data = response.body().getPoints();
                    saveSharepreferences(idUser,token,roleUser,emailUser,photoUser, nameUser);
                    Intent intent = new Intent(LoginActivity.this,Principal.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<ModelsUser> call, Throwable t) {
            }
        });
    }

    private void saveSharepreferences(Integer idUser,String token,String roleUser,String emailUser, String photoUser, String nameUser){
        //TODO GUARDAR LOS DATOS EN EL SHAREPREFERENCES
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("idUser",idUser);
        editor.putString("tokenUser", token);
        editor.putString("roleUser",roleUser);
        editor.putString("emailUser",emailUser);
        editor.putString("imgUser", photoUser);
        editor.putString("nameUser", nameUser);
        editor.apply();

    }


}