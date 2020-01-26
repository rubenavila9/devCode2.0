package ariel.az.devcode20.Adaptadores;

import android.view.ScaleGestureDetector;
import android.widget.ImageView;

public class ScalerListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

    private float scale = 1f;
    private ImageView imageView;

    public ScalerListener(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        scale *= detector.getScaleFactor();
        imageView.setScaleX(scale);
        imageView.setScaleY(scale);
        return true;

    }
}
