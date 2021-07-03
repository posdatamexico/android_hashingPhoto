package com.example.hashingphoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.NonNull;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE= 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;


    Button captureBtn;
    ImageView imageView;

    Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image_view);
        captureBtn = findViewById(R.id.capture_image_btn);

        captureBtn.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              //if system os is >= marshmallow, request permission
                                              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                  if (checkSelfPermission(Manifest.permission.CAMERA) ==
                                                          PackageManager.PERMISSION_DENIED ||
                                                          checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                                                  PackageManager.PERMISSION_DENIED) {
                                                      // permission not enable, request it
                                                      String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                                      // show popup to request permission
                                                      requestPermissions(permission, PERMISSION_CODE);
                                                  } else {
                                                      // permission already granted
                                                      openCamera();
                                                  }

                                              }
                                          }

                                          ;

                                          private void openCamera() {
                                              contentValues values = new ContentValues();
                                              values.put(MediaStore.Images.Media.TITLE, "New Picture");
                                              values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
                                              image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                                              // Camera intent
                                              Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                              CameraIntent.putEtra(MediaStore.EXTRA_OUTPUT, image_uri);
                                              startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);

                                          }

                                          // handling permission
                                          @Override
                                          public void onRequestPermissionsResult(int RequestCode, String[] permissions, int[] grantResults) {
                                              if (requestCode == PERMISSION_REQUEST_CODE_ACCESS_COARSE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                                  openCamera();
                                              } else {
                                                  //permission from popup was denied ***

                                                  Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                                              }

                                          }
        });
        @Override
        protected void onActivityResult(int RequestCode, int ResultCode, Intent data)
        {
            //called when image was capture from camera
            if (resultCode == RESULT_OK) {
                // set the image capture to our imageView
                imageView.setImageUri(image_uri);
            }
        }
    }