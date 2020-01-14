package ariel.az.devcode20.Activities;

import android.content.pm.PackageManager;
import android.util.Patterns;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import ariel.az.devcode20.R;
import ariel.az.devcode20.configurationAndRouters.Router;
import ariel.az.devcode20.configurationAndRouters.conexion;
import ariel.az.devcode20.models.ModelsMensajes;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailRegister, passRegister, nameRegister;
    private Button btnRegister;
    private Spinner spinnerTypeUser;
    String type;
    ArrayList<String> typeUser;
    private Router router;
    MultipartBody.Part procfile;
    RequestBody email, password , roleUser , nameUser;
    private static final int PReqCode = 2 ;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailRegister = findViewById(R.id.emailRegister);
        passRegister = findViewById(R.id.passRegister);
        nameRegister = findViewById(R.id.nameRegister);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);
        spinnerTypeUser = findViewById(R.id.spinnerTypeUser);

        typeUser = new ArrayList<>();
        typeUser.add("Selecciona");
        typeUser.add("estudiante");
        typeUser.add("docente");



        // TODO: 12/27/2019 creacion del spinner para eleccionar el rol del nuevo usuario
        spinnerTypeUser.setAdapter(new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_list_item_1,typeUser));
        spinnerTypeUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //TODO OBTENER QUE TIPO DE USUARIO HA SELECCIONADO EL USUARIO
                switch (position){
                    case 0 :
                        type = typeUser.get(0);
                        break;
                    case 1:
                        type = typeUser.get(1);
                        break;
                    case 2:
                        type = typeUser.get(2);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFieldsRegister();
            }
        });
    }





    private void checkFieldsRegister() {
        //TODO VALIDAR QUE TOODO LOS CAMPOS HAN SIDO AGREGADOS
        if (type == null || type == typeUser.get(0) || !email(emailRegister.getText().toString().trim())
                || TextUtils.isEmpty(passRegister.getText().toString().trim()) || TextUtils.isEmpty(nameRegister.getText().toString().trim())){
            Toast.makeText(this, "LLeve todos los campos", Toast.LENGTH_SHORT).show();
        }else{
            checkAndRequestForPermission();

        }
    }

    private boolean email(String email){
        //TODO VERIFICAR QUE EL USUARIO ESTE INGRESANDO UN CORREO
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return  pattern.matcher(email).matches();
    }


    private void checkAndRequestForPermission() {
        //TODO PEDIR PERMISO AL USUARIO PARA ACCEDER A LA GALERIA
        if (ContextCompat.checkSelfPermission(RegisterActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(RegisterActivity.this,"Porfavor necesitamos que nos de permiso",Toast.LENGTH_SHORT).show();
            }
            else
            {
                ActivityCompat.requestPermissions(RegisterActivity.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        }
        else
            openGallery();

    }




    private void openGallery() {
        // TODO: 12/27/2019 abrir galeria
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/");
        startActivityForResult(galleryIntent.createChooser(galleryIntent,"Seleccione una imagen"),10);
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO: 12/27/2019 obtener la infomrmacion de la imagen
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Uri photoUri = data.getData();
            String[] proj = { MediaStore.Images.Media.DATA };
            Cursor actualimagecursor = managedQuery(photoUri, proj,null, null, null);
            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA); actualimagecursor.moveToFirst();
            String img_path = actualimagecursor.getString(actual_image_column_index);
            File file = new File(img_path);

            if (file.exists()){
                //TODO VALIDAR SI SI EXISTE UNA FOTO  Y PREPARAR LAS VARIABLES PARA ENVIAR A LA BASE DE DATOS
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"),file);
                procfile = MultipartBody.Part.createFormData("photo",file.getName(), requestFile);
                nameUser = RequestBody.create(MediaType.parse("multipart/form-data"),nameRegister.getText().toString().trim());
                email = RequestBody.create(MediaType.parse("multipart/form-data"),emailRegister.getText().toString().trim());
                password = RequestBody.create(MediaType.parse("multipart/form-data"),passRegister.getText().toString().trim());
                roleUser = RequestBody.create(MediaType.parse("multipart/form-data"), type);
            }


            btnRegister.setVisibility(View.INVISIBLE);
            router = conexion.getApiService();
            Call<ModelsMensajes> modelsMensajesCall = router.routerCrearCuenta(procfile,nameUser,email,password,roleUser);
            modelsMensajesCall.enqueue(new Callback<ModelsMensajes>() {
                @Override
                public void onResponse(Call<ModelsMensajes> call, Response<ModelsMensajes> response) {
                    if (response.isSuccessful()){
                        String data = response.body().getMessage();
                        if (!data.equals("el email ya existe")){
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        }
                        Toast.makeText(RegisterActivity.this, data , Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ModelsMensajes> call, Throwable t) {
                    Log.e("Error", t.toString());
                }
            });
        }
    }
}
