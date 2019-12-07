package ariel.az.devcode20.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ariel.az.devcode20.R;
import ariel.az.devcode20.configurationAndRouters.Router;
import ariel.az.devcode20.configurationAndRouters.conexion;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailRegister, passRegister;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailRegister = findViewById(R.id.emailRegister);
        passRegister = findViewById(R.id.passRegister);
        btnRegister = findViewById(R.id.btnRegister);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        //metodo para registar usuarios
        Router router = conexion.getApiService();

    }
}
