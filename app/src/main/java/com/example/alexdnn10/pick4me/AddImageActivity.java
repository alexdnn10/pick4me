package com.example.alexdnn10.pick4me;

import android.content.ActivityNotFoundException;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class AddImageActivity extends AppCompatActivity {
    private Uri picUri;
    ImageView mImageOne;
    ImageView mImageTwo;
    ImageView mImageThree;
    ImageView mImageFour;
    File f;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLERY = 2;
    private static final int PICK_CROP = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mImageOne = (ImageView) findViewById(R.id.image_one);
        mImageTwo = (ImageView) findViewById(R.id.image_two);
        mImageOne.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
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
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, PICK_FROM_CAMERA);
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
                f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    picUri=Uri.fromFile(f);
                    int outputX=500;
                    int outputY=500;
                    boolean scale=true;
                    boolean scaleUpIfNeeded=true;
                    int aspectX=1;
                    int aspectY=1;
                    int spotlightX=1;
                    int spotlightY=1;
                    boolean returnData=true;
                    startCropAnImage(outputX, outputY, scale, scaleUpIfNeeded, aspectX, aspectY, spotlightX, spotlightY, returnData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == PICK_FROM_GALLERY) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
               // Log.w("path of image from gallery......******************.........", picturePath+"");
                mImageOne.setImageBitmap(thumbnail);
            } else if (requestCode == PICK_CROP) {
                //Handler image crop
                Bitmap bitmap;
                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                        bitmapOptions);

                mImageOne.setImageBitmap(bitmap);
                String path = android.os.Environment
                        .getExternalStorageDirectory()
                        + File.separator
                        + "Phoenix" + File.separator + "default";
                f.delete();
                OutputStream outFile = null;
                File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                try {
                    outFile = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                    outFile.flush();
                    outFile.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public void startCropAnImage(int outputX, int outputY, boolean scale, boolean scaleUpIfNeeded, int aspectX, int aspectY, int spotlightX, int spotlightY, boolean returnData) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(picUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("outputX", outputX); // int
        intent.putExtra("outputY", outputY); // int
        intent.putExtra("scale", scale); // boolean
        intent.putExtra("scaleUpIfNeeded", scaleUpIfNeeded); // boolean
        intent.putExtra("aspectX", aspectX); // int
        intent.putExtra("aspectY", aspectY); // int
        intent.putExtra("spotlightX", spotlightX); // int
        intent.putExtra("spotlightY", spotlightY); // int
        intent.putExtra("return-data", returnData); // boolean
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, PICK_CROP);
        }
    }
}



