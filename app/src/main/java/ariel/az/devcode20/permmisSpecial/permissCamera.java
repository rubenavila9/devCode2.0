package ariel.az.devcode20.permmisSpecial;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import ariel.az.devcode20.Activities.RegisterActivity;

public class permissCamera {

    private Activity activity;
    private static final int PReqCode = 2 ;

    public permissCamera(Activity activity) {
        this.activity = activity;
    }

    public boolean checkAndRequestForPermission() {
        //TODO PEDIR PERMISO AL USUARIO PARA ACCEDER A LA GALERIA
        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(activity,"Porfavor necesitamos que nos de permiso",Toast.LENGTH_SHORT).show();
            }
            else
            {
                ActivityCompat.requestPermissions(activity,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        }
        else{
            return true;
        }
        return false;
    }
}
