package ariel.az.devcode20.Fragmentos;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ariel.az.devcode20.Activities.Principal;
import ariel.az.devcode20.SharedPreferencesUser.SaveDataUser;
import ariel.az.devcode20.configurationAndRouters.Router;
import ariel.az.devcode20.configurationAndRouters.conexion;
import ariel.az.devcode20.models.ModelsMensajes;
import ariel.az.devcode20.permmisSpecial.permissCamera;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.tiper.MaterialSpinner;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ariel.az.devcode20.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class publicacionFragmento extends Fragment {


    private String img_path, simpleDateFormat;
    private int postionNivel = 0;
    private SharedPreferences preferences;
    private Router router;
    private MaterialSpinner materialSpinner;
    private FloatingActionButton floatingActionButtonPublications;
    private TextInputEditText namePublications, descriptionPublications;
    private permissCamera  permissCamera;
    private TextView messagePhoto;
    private Button buttonpublicacion;
    File file;
    MultipartBody.Part procfile;
    RequestBody namePubl, descripPubl, levelPubl, iduser, day;
    public publicacionFragmento() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publicacionfragmento, container, false);
        buttonpublicacion = view.findViewById(R.id.buttonpublication);
        floatingActionButtonPublications = view.findViewById(R.id.photoPublications);
        namePublications = view.findViewById(R.id.namePublications);
        descriptionPublications = view.findViewById(R.id.descriptionPublications);
        messagePhoto = view.findViewById(R.id.messagePhoto);
        preferences = getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);;


        floatingActionButtonPublications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissCamera = new permissCamera(getActivity());
                if (permissCamera.checkAndRequestForPermission()){
                    openGallery();
                }
            }
        });
        materialSpinner = view.findViewById(R.id.material_spinner);



        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.nivel,android.R.layout.simple_list_item_1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materialSpinner.setAdapter(arrayAdapter);
        materialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected( MaterialSpinner materialSpinner, View view, int i, long l) {
                switch (i){
                    case 0:
                        postionNivel = 1;
                        return;
                    case 1:
                        postionNivel = 2;
                        return;
                    case 2:
                        postionNivel = 3;
                        return;
                    case 3:
                        postionNivel = 4;
                        return;
                    case 4:
                        postionNivel = 5;
                        return;
                    case 5:
                        postionNivel = 6;
                    case 6:
                        postionNivel = 7;
                        return;
                    case 7:
                        postionNivel = 8;
                        return;
                    case 8:
                        postionNivel = 9;
                        return;
                    case 9:
                        postionNivel = 10;
                        return;
                }
            }

            @Override
            public void onNothingSelected(@NotNull MaterialSpinner materialSpinner) {

            }
        });



        buttonpublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (postionNivel == 0 || TextUtils.isEmpty(namePublications.getText().toString()) || TextUtils.isEmpty(descriptionPublications.getText().toString())){
                   Toast.makeText(getActivity(), "Llene Campos", Toast.LENGTH_SHORT).show();
               }else {
                   valPhotoPublications();
               }
            }
        });
        return view;

    }



    private void openGallery() {
        // TODO: 12/27/2019 abrir galeria
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/");
        startActivityForResult(galleryIntent.createChooser(galleryIntent,"Seleccione una imagen"),10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Uri photoUri = data.getData();
            String[] proj = { MediaStore.Images.Media.DATA };
            Cursor actualimagecursor = getActivity().managedQuery(photoUri, proj,null, null, null);
            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA); actualimagecursor.moveToFirst();
            img_path = actualimagecursor.getString(actual_image_column_index);
            file = new File(img_path);
            if (file.exists()){
                messagePhoto.setVisibility(View.VISIBLE);
            }
        }
    }

    private void valPhotoPublications(){
        router = conexion.getApiService();
        Date myDate = new Date();
        simpleDateFormat  = new SimpleDateFormat("dd-MM-yyyy").format(myDate);
        try {
            day = RequestBody.create(MediaType.parse("multipart/form-data"),simpleDateFormat);
            namePubl = RequestBody.create(MediaType.parse("multipart/form-data"),namePublications.getText().toString().trim());
            descripPubl = RequestBody.create(MediaType.parse("multipart/form-data"),descriptionPublications.getText().toString().trim());
            levelPubl = RequestBody.create(MediaType.parse("multipart/form-data"),String.valueOf(postionNivel).trim());
            iduser = RequestBody.create(MediaType.parse("multipart/form-data"),String.valueOf(SaveDataUser.getIdUser(preferences)).trim());
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"),file);
            procfile = MultipartBody.Part.createFormData("photo",file.getName(), requestFile);
            setPublicationsDB();
        }catch (Exception e){
            procfile = MultipartBody.Part.createFormData("photo","null");
            setPublicationsDB();
        }
    }

    private void setPublicationsDB(){
        buttonpublicacion.setVisibility(View.INVISIBLE);
        Call<ModelsMensajes> setPublications = router.routerCrearPublications(procfile,namePubl,descripPubl,levelPubl,day,iduser);
        setPublications.enqueue(new Callback<ModelsMensajes>() {
            @Override
            public void onResponse(Call<ModelsMensajes> call, Response<ModelsMensajes> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    buttonpublicacion.setVisibility(View.VISIBLE);
                    startActivity(new Intent(getActivity(),Principal.class));
                }
            }

            @Override
            public void onFailure(Call<ModelsMensajes> call, Throwable t) {
                Log.e("error",t.getMessage());
            }
        });
    }


    private void messageUserDialog(){
        TextView messageUser;
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_photo);
        messageUser = dialog.findViewById(R.id.messageUser);
         messageUser.setText("Vemos que iras hacer publicación " + SaveDataUser.getNameUser(preferences) + " ayudanos con una foto para qué la comunidad" +
                 " te pueda entender de la mejor forma posible dale clic al botón rojo y selecciona tu imagen y podrás publicar!!");
        dialog.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }



}
