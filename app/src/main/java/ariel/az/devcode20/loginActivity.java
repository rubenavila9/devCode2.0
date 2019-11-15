package ariel.az.devcode20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ariel.az.devcode20.connect.Router;
import ariel.az.devcode20.connect.conexion;
import ariel.az.devcode20.models.Login;
import ariel.az.devcode20.models.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loginActivity extends AppCompatActivity {

    Button ButtonLogin;
    Button ButtonRegister;
    EditText Editext, EditText2;
    Router router;
    conexion conexi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButtonLogin = findViewById(R.id.btnEntry);
        ButtonRegister = findViewById(R.id.btnRegister);
        Editext = findViewById(R.id.email);
        EditText2 = findViewById(R.id.pass);


        ButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   if(true){
                // Log.i("onClick","if");
                // }else {
                //   Log.i("onClick","else");

                //  }
                //Llamar a la nueva pantalla
                Intent intent = new Intent(loginActivity.this,
                        registerActivity.class);
                startActivity(intent);
            }
        });

        ButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });
    }


    private void connect(){
        Login login;
        router = conexi.getApiService();
        login = new Login("joa","ddd");
        Call<Token> tokenCall = router.login(login);

        tokenCall.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.isSuccessful()){
                    String token = response.body().getAuthToken();
                    Log.i("token", token);
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {

            }
        });




    }



}