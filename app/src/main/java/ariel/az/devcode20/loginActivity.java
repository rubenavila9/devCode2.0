package ariel.az.devcode20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class loginActivity extends AppCompatActivity {

    Button ButtonLogin;
    Button ButtonRegister;
    EditText Editext, EditText2;


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


    }
}