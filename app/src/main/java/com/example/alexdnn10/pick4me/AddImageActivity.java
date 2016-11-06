package com.example.alexdnn10.pick4me;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddImageActivity extends AppCompatActivity {
    private Uri picUri;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private ImageView mImageOne;
    private ImageView mImageTwo;
    private ImageView mImageThree;
    private ImageView mImageFour;
    int flag=0;//flag=0 - not image load, =1 - load from camera, =2 - load from gallery
    Bitmap bitmap;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLERY = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mImageOne = (ImageView) findViewById(R.id.image_one);
        mImageTwo = (ImageView) findViewById(R.id.image_two);
        mImageThree = (ImageView) findViewById(R.id.image_three);
        mImageFour = (ImageView) findViewById(R.id.image_four);
        mImageOne.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
                flag=1;
            }
        });
        mImageTwo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
                flag=2;
            }
        });
        mImageThree.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
                flag=3;
            }
        });
        mImageFour.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
                flag=4;
            }
        });
    }
        // new select photo
    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddImageActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                        // Create the File where the photo should go
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {
                            // Error occurred while creating the File
                            //Log.i(TAG, "IOException");
                        }
                        // Continue only if the File was successfully created
                        if (photoFile != null) {
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                            startActivityForResult(cameraIntent, PICK_FROM_CAMERA);
                        }
                    }
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, PICK_FROM_GALLERY);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_FROM_CAMERA) {
                try {
                    picUri = Uri.parse(mCurrentPhotoPath);
                    CropImage.activity(picUri)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == PICK_FROM_GALLERY) {
                picUri = data.getData();
                CropImage.activity(picUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    try {
                        mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    switch (flag) {
                        case 1: mImageOne.setImageBitmap(mImageBitmap);
                        case 2: mImageTwo.setImageBitmap(mImageBitmap);
                        case 3: mImageThree.setImageBitmap(mImageBitmap);
                        case 4: mImageFour.setImageBitmap(mImageBitmap);
                        default: break;
                    }
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
    public void SavePhotoUri (Uri imageuri, String Filename){

        File FilePath = new File(context.getDir(Environment.DIRECTORY_PICTURES, Context.MODE_PRIVATE));
        try {
            Bitmap selectedImage = MediaStore.Images.Media.getBitmap(context.getContentResolver(),imageuri);
            String destinationImagePath = FilePath+"/"+Filename;
            FileOutputStream destination = new FileOutputStream(destinationImagePath);
            selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, destination);
            destination.close();
        } catch (Exception e) {
            Log.e("error", e.toString());
        }
    }
}
