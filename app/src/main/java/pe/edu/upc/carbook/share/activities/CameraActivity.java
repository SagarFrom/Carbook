package pe.edu.upc.carbook.share.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pe.edu.upc.carbook.R;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Created by Juanxps on 11/20/16.
 */


public class CameraActivity extends AppCompatActivity {

    public SharedPreferences.Editor editor;
    // Log.d TAG
    private static final String TAG = "Carbook";
    // File Uri
    private Uri fileUri;
    // Media Type Constants
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final int MEDIA_TYPE_VIDEO = 2;
    private static final int MEDIA_TYPE_IMAGE_PICK = 3;
    // Activity Request Constants
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    private static final int PICK_IMAGE_ACTIVITY_REQUEST_CODE = 300;
    // Last captured File
    private Uri lastOutputMediaFileUri = null;
    // UI Widgets
    FloatingActionButton fab;
    Switch videoCaptureSwitch;
    // Permission Request
    private static final int PERMISSION_REQUEST_CAMERA = 100;
    // Camera Feature availability status
    private boolean cameraAvailable = false;

    private static  String namephoto;

    private ImageView ima;

    private TextView textViewUrlImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        videoCaptureSwitch = (Switch) findViewById(R.id.videoCaptureSwitch);
        //textViewUrlImage = (TextView) findViewById(R.id.textViewUrlImage);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(onClickListenerForFloatingActionButton());
        validatePermissions();

        editor = getSharedPreferences("local", MODE_PRIVATE).edit();

        //FirebaseStorage storage = FirebaseStorage.getInstance();
        //Picasso.with(MainActivity.this).load(downloadUri).fit().centerCrop().into(mImageView);


        //Photos/Locals/IMG_20161119_173212.jpg"

        final StorageReference gsReference;
        gsReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://carbookdb.appspot.com/");


        //String urll = gsReference.child("Photos/Locals/IMG_20161119_173212.jpg").getDownloadUrl().get

        ima = (ImageView) findViewById(R.id.imageViewlocal);



        gsReference.child("Photos/Locals/IMG_20161119_173212.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Toast.makeText(CameraActivity.this, uri.toString() , Toast.LENGTH_LONG).show();
                Picasso.with(CameraActivity.this).load(uri.toString()).fit().centerCrop().into(ima);
               // ima.text(uri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

                /*
        gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'


                Picasso.with(MainActivity.this).load(String.valueOf(gsReference.getDownloadUrl())).fit().centerCrop().into(ima);

                Toast.makeText(MainActivity.this, "result:" + gsReference.getDownloadUrl().getResult(), Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


       Toast.makeText(MainActivity.this, "result:" + gsReference.getDownloadUrl().getResult().toString(), Toast.LENGTH_LONG).show();
        */



        //Picasso.with(MainActivity.this).load("https://firebasestorage.googleapis.com/v0/b/carbookdb.appspot.com/o/Photos%2FLocals%2FIMG_20161119_173212.jpg?alt=media&token=b503eb0b-1f17-49b3-82d7-8ebf45748052").fit().centerCrop().into(ima);

        //Glide.with(MainActivity.this)
        //       .using(new FirebaseImageLoader())
        //        .load(gsReference);
    }

    private View.OnClickListener onClickListenerForFloatingActionButton() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Verify video capture switch state
                if(videoCaptureSwitch.isChecked()) {
                    // Capture Video
                    /*
                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
                    */

                    Intent intent = new Intent(Intent.ACTION_PICK);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent,PICK_IMAGE_ACTIVITY_REQUEST_CODE);

                }
                else {
                    // Capture Image
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                }
                lastOutputMediaFileUri = fileUri;
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        };
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Log.d(TAG, "ResultCode: RESULT_OK");
                String fileName = data != null ? data.getData().getPath() :
                        lastOutputMediaFileUri.getPath();

                /////

                final StorageReference mStorage;
                mStorage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://carbookdb.appspot.com/");


                StorageReference filepath = mStorage.child("Photos/Locals").child(namephoto);

                Toast.makeText(this, "ruta: " +
                        fileUri.toString(), Toast.LENGTH_LONG).show();


                filepath.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        //mProgress.dismiss();
                        Log.d(TAG,"entro");

                        Uri downloadUri = taskSnapshot.getDownloadUrl();
                        Log.d("name:",downloadUri.toString());

                        Toast.makeText(CameraActivity.this, downloadUri.toString(), Toast.LENGTH_SHORT).show();


                        /*        .into(ima);

                        gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Got the download URL for 'users/me/profile.png'

                                Picasso.with(MainActivity.this).load(String.valueOf(gsReference.getDownloadUrl())).fit().centerCrop().into(ima);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });*/

                        //Toast.makeText(MainActivity.this, "Uploading Finihed...", Toast.LENGTH_SHORT).show();

                    }
                });

                /////



                //Toast.makeText(this, "Image saved to: "+ fileName, Toast.LENGTH_LONG).show();
            } else if(resultCode == RESULT_CANCELED) {
                Log.d(TAG, "ResultCode: RESULT_CANCELED");
            } else {
                Log.d(TAG, "ResultCode: "+Integer.toString(resultCode));
            }
        }
        if(requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Log.d(TAG, "ResultCode: RESULT_OK");
                String fileName = data != null ? data.getData().getPath() :
                        lastOutputMediaFileUri.getPath();
                Toast.makeText(this, "Video saved to: " +
                        fileName, Toast.LENGTH_LONG).show();
            } else if(resultCode == RESULT_CANCELED) {
                Log.d(TAG, "ResultCode: RESULT_CANCELED");
            } else {
                Log.d(TAG, "ResultCode: "+Integer.toString(resultCode));
            }
        }

        if(requestCode == PICK_IMAGE_ACTIVITY_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                String fileName = data != null ? data.getData().getPath() :
                        lastOutputMediaFileUri.getPath();


                //Toast.makeText(this, "Image picked to: " +
                //   fileName.toString(), Toast.LENGTH_LONG).show();

                //Toast.makeText(this, "Image picked to: " + data.getDataString(), Toast.LENGTH_LONG).show();


                final StorageReference mStorage;
                mStorage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://carbookdb.appspot.com/");


                StorageReference filepath = mStorage.child("Photos/Locals").child(namephoto);

                String prefix = "Content:";
                Uri prueba = data.getData();
                //Uri prueba = Uri.fromFile(new File(fileName.startsWith(data.getDataString())));



                filepath.putFile(prueba).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        ///

                        ima = (ImageView) findViewById(R.id.imageViewlocal);



                        mStorage.child("Photos/Locals/" + namephoto).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Got the download URL for 'users/me/profile.png'

                                Toast.makeText(CameraActivity.this, uri.toString() , Toast.LENGTH_LONG).show();
                                Picasso.with(CameraActivity.this).load(uri.toString()).fit().centerCrop().into(ima);

                                editor.putString("urllocal",uri.toString());
                                editor.commit();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                        ////

                    }
                });


                //filepath.putFile(fileUri);

                //filepath.putFile()
                Toast.makeText(this, "Image picked to: " +
                        fileUri.toString(), Toast.LENGTH_LONG).show();


            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
         //   return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    private Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        File mediaStorageDir =
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES);
        if(!mediaStorageDir.exists()) {
            if(!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Failed to create directory");
                return null;
            }
        }
        else {
            Log.d(TAG, "Directory found");
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if(type == MEDIA_TYPE_IMAGE) {
            namephoto = "IMG_" + timeStamp + ".jpg";
            //mediaFile = new File(
            //        mediaStorageDir.getPath() +
            //                File.separator + "IMG_" + timeStamp + ".jpg");

            mediaFile = new File(
                    mediaStorageDir.getPath() +
                            File.separator + namephoto);
        }
        else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(
                    mediaStorageDir.getPath() +
                            File.separator + "VID_" + timeStamp + ".mp4");

        }
        else {
            if(type == MEDIA_TYPE_IMAGE_PICK){
                namephoto = "IMG_" + timeStamp + ".jpg";
                mediaFile = new File(
                        mediaStorageDir.getPath() +
                                File.separator + namephoto);

            }
            else {

                return null;
            }
        }

        try {
            Log.d(TAG, mediaFile.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mediaFile;
    }

    private void validatePermissions() {
        if(permissionsGranted()) {
            cameraAvailable = true;
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);

    }
    private boolean permissionsGranted() {
        boolean grantedCameraPermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PERMISSION_GRANTED);
        boolean grantedStoragePermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED);
        Log.d(TAG, "Permission for CAMERA: " + String.valueOf(grantedCameraPermission));
        Log.d(TAG, "Permission for STORAGE: " + String.valueOf(grantedStoragePermission));
        return (grantedCameraPermission && grantedStoragePermission);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA: {
                cameraAvailable = ((grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) &&
                        (grantResults.length > 0 && grantResults[1] == PERMISSION_GRANTED));
            }

        }
        updatePermissionsDependentFeatures();
    }

    private void updatePermissionsDependentFeatures() {
        fab.setEnabled(cameraAvailable);
        videoCaptureSwitch.setEnabled(cameraAvailable);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setContentView(R.layout.activity_camera);

        setResult(RESULT_OK);
    }
}
