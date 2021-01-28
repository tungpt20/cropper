package dev.tino.cropper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import dev.tino.imagecropper.CropImage;
import dev.tino.imagecropper.CropImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageCropped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageCropped = findViewById(R.id.img_cropped);

        findViewById(R.id.btn_upload).setOnClickListener(v -> CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAspectRatio(1, 1)
                .start(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            String message = "";
            if (resultCode == RESULT_OK) {
                if (result != null) {
                    imageCropped.setImageURI(result.getUri());
                    message = "Cropping successful, Sample: " + result.getSampleSize();
                }
            } else {
                if (result != null) {
                    message = "Cropping Fail: " + result.getError().getMessage();
                } else {
                    message = "Cropping Error";
                }
            }
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }
}