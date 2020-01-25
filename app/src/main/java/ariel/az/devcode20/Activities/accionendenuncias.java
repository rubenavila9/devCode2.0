package ariel.az.devcode20.Activities;

import android.app.Dialog;
import android.os.Handler;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import ariel.az.devcode20.R;
import ariel.az.devcode20.configurationAndRouters.Router;
import ariel.az.devcode20.configurationAndRouters.conexion;
import ariel.az.devcode20.models.ModelsMensajes;
import com.bumptech.glide.Glide;
import com.tiper.MaterialSpinner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class accionendenuncias extends AppCompatActivity implements View.OnClickListener {

    private TextView nameUser, emailUserDen, roleUserDen;
    private ImageView imgUser;
    private int iduser;
    private String nameDenuncia;

    private Dialog dialog;

    private ModelsPermiss modelsPermiss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_accionendenuncias);
        getSupportActionBar().show();


        nameUser = findViewById(R.id.nameusuario);
        emailUserDen = findViewById(R.id.emailUser);
        imgUser = findViewById(R.id.photoUser);
        roleUserDen = findViewById(R.id.rolUserDen);

        getDataActivityAdmin();
    }

    private void getDataActivityAdmin() {
        iduser = getIntent().getExtras().getInt("idUserDen");
        nameDenuncia = getIntent().getExtras().getString("nameUseDen");
        nameUser.setText(nameDenuncia);
        emailUserDen.setText(getIntent().getExtras().getString("emailUserDen"));
        roleUserDen.setText(getIntent().getExtras().getString("rolUseDen"));
        Glide.with(accionendenuncias.this).load(getIntent().getExtras().getString("imgUserDen")).into(imgUser);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menubloqueo,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.bloqueo:
                dialogBloque();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void dialogBloque(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_bloqueo);
        RadioButton bloquearButton = dialog.findViewById(R.id.bloquear);
        RadioButton desbloquearButton = dialog.findViewById(R.id.desbloquear);
        bloquearButton.setOnClickListener(this);
        desbloquearButton.setOnClickListener(this);
        dialog.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bloquear:
                setLock(false);
                break;
            case R.id.desbloquear:
                setLock(true);
                break;
        }
    }


    private void setLock(Boolean permiss){
        modelsPermiss = new ModelsPermiss(iduser,permiss);
        Router router;
        router = conexion.getApiService();
        Call<ModelsMensajes> getMensajeBloqueo = router.setBloqueoCuenta(modelsPermiss);
        getMensajeBloqueo.enqueue(new Callback<ModelsMensajes>() {
            @Override
            public void onResponse(Call<ModelsMensajes> call, Response<ModelsMensajes> response) {
                if (response.isSuccessful()){
                    dialog.dismiss();
                    Toast.makeText(accionendenuncias.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelsMensajes> call, Throwable t) {

            }
        });

    }

    public class ModelsPermiss{
        Integer iduser;
        Boolean permiss;

        public ModelsPermiss(Integer iduser, Boolean permiss) {
            this.iduser = iduser;
            this.permiss = permiss;
        }

    }
}
