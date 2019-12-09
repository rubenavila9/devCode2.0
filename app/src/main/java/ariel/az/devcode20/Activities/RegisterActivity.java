package ariel.az.devcode20.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import ariel.az.devcode20.R;
import ariel.az.devcode20.configurationAndRouters.Router;
import ariel.az.devcode20.configurationAndRouters.conexion;
import ariel.az.devcode20.models.ModelsRegister;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailRegister, passRegister;
    private Button btnRegister;

    private Spinner spinnerTypeUser;
    String type;
    ArrayList<String> typeUser;
    static int REQUESCODE = 1;
    private Uri pickUri;
    private ImageView user_Img;
    private Router router;
    RequestBody nameUser , emailUser, roleUser, fileReqBody;
    MultipartBody.Part  part;
    public static final String MULTIPART_TYPE = "multipart/form-data";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailRegister = findViewById(R.id.emailRegister);
        passRegister = findViewById(R.id.passRegister);
        btnRegister = findViewById(R.id.btnRegister);
        user_Img = findViewById(R.id.imgUserRegister);
        spinnerTypeUser = findViewById(R.id.spinnerTypeUser);

        typeUser = new ArrayList<>();
        typeUser.add("Selecciona");
        typeUser.add("estudiante");
        typeUser.add("docente");
        //creacion del spinner


        spinnerTypeUser.setAdapter(new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_list_item_1,typeUser));
        spinnerTypeUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //averiguo si el usuario que opcion a elegido
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
                register();
            }
        });
    }

    private void register() {
        //metodo para registar usuarios
        if (type == null || type == typeUser.get(0)){
            Toast.makeText(this, "No se olvide de selecionar", Toast.LENGTH_SHORT).show();
        }else{
            openGallery();

        }
    }


    private void openGallery() {
        //abrir galeria
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //obtener la imagen

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUESCODE){
            pickUri = data.getData();
            user_Img.setImageURI(pickUri);
            String photo = pickUri.toString();
            File file = new File(photo);
            fileReqBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);


            String name = "bryan";
            String email = "ed@gmail.com";
            String role = "student";
            nameUser = RequestBody.create(MediaType.parse(MULTIPART_TYPE), name);
            emailUser = RequestBody.create(MediaType.parse(MULTIPART_TYPE),email);
            roleUser = RequestBody.create(MediaType.parse(MULTIPART_TYPE), role);
            part = MultipartBody.Part.createFormData("photo", file.getName(), fileReqBody);

            router = conexion.getApiService();
            Call<ModelsRegister> modelsRegisterCall = router.updateUser(nameUser,emailUser);

            modelsRegisterCall.enqueue(new Callback<ModelsRegister>() {
                @Override
                public void onResponse(Call<ModelsRegister> call, Response<ModelsRegister> response) {
                    if (response.isSuccessful()){

                    }
                }

                @Override
                public void onFailure(Call<ModelsRegister> call, Throwable t) {

                }
            });






        }
    }




    private String getPackageCodePath(Uri pickUri) {
        //indentificamos la ubicacion del archivo si salio de descargas imagenes o videos.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(this,pickUri)){
            if(isExternalStorageDocument(pickUri)){

            }
            else if(isDownloadsDocument(pickUri)){


            }else if(isMediaDocument(pickUri)){
                String docId = DocumentsContract.getDocumentId(pickUri);
                String[] split = docId.split(":");
                String type = split[0];

                Uri contentUri;
                if("image".equals(type)){
                    contentUri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                      split[1]
                    };
                    return getDataColumn(this,contentUri,selection,selectionArgs);
                }

            }

        }
        return "";
    }


    private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs){
        Cursor cursor = null;
        String column = "_data";
        String[] projection = {
          column
        };

        try {
            cursor = context.getContentResolver().query(uri,projection,selection,selectionArgs,null);

            if (cursor != null && cursor.moveToLast()){
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        }finally {
            if(cursor != null) cursor.close();
        }


        return null;
    }

    void cc(Uri fileUri){


    }













    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }



}
