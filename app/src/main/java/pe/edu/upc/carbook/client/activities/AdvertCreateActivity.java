package pe.edu.upc.carbook.client.activities;

import android.Manifest;
import android.app.DatePickerDialog;

import android.content.Context;
import android.content.Intent;
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
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.text.SimpleDateFormat;
import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.client.services.clientServices;
import pe.edu.upc.carbook.share.activities.CameraActivity;
import pe.edu.upc.carbook.share.helpers.SharedPreferencesManager;
import pe.edu.upc.carbook.share.models.Advert;
import pe.edu.upc.carbook.share.models.Car;
import pe.edu.upc.carbook.share.models.User;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Created by Richard on 18/11/16.
 */


public class AdvertCreateActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText toDateEditText;
    private EditText descriptionEditText;
    private Spinner carInfoSpinner;

    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;
    final Context context = this;
    private static String TAG = "Carbook";
    private SharedPreferencesManager spm;
    private User userSession;

    private List<Car> carsOfClient = new ArrayList<>();
    private List<String> nameFullCars;
    private Advert newAdverd = new Advert();

    //Para la Toma de Foto
    private Uri fileUri;
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final int MEDIA_TYPE_VIDEO = 2;
    private static final int MEDIA_TYPE_IMAGE_PICK = 3;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    private static final int PICK_IMAGE_ACTIVITY_REQUEST_CODE = 300;
    private Uri lastOutputMediaFileUri = null;
    private Switch photoCaptureSwitch;
    private static final int PERMISSION_REQUEST_CAMERA = 100;
    private boolean cameraAvailable = false;
    private static String namephoto;
    private ImageView advertImageView;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_activity_advert_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
        spm = new SharedPreferencesManager(context);
        userSession = spm.getUserOnPreferences();
        findViewsById();
        setDateTimeField();
        updateDataCars();

        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        carInfoSpinner = (Spinner) findViewById(R.id.carInfoSpinner);

        carInfoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();
                newAdverd.setCarId(carsOfClient.get(position).getCarId());

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //PARA LA FOTO
        photoCaptureSwitch = (Switch) findViewById(R.id.photoCaptureSwitch);
        validatePermissions();
        final StorageReference gsReference;
        gsReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://carbookdb.appspot.com/");
        advertImageView = (ImageView) findViewById(R.id.advertImageView);

        gsReference.child("Photos/Locals/IMG_20161119_173212.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Toast.makeText(AdvertCreateActivity.this, uri.toString() , Toast.LENGTH_LONG).show();
                Picasso.with(AdvertCreateActivity.this).load(uri.toString()).fit().centerCrop().into(advertImageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(onClickListenerForFloatingActionButton());
    }

    private View.OnClickListener onClickListenerForFloatingActionButton() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photoCaptureSwitch.isChecked()){
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent,PICK_IMAGE_ACTIVITY_REQUEST_CODE);
                } else{
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                }
                lastOutputMediaFileUri = fileUri;
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
            if(resultCode == RESULT_OK) {
                Log.d(TAG, "ResultCode: RESULT_OK");
                String fileName = data != null ? data.getData().getPath() : lastOutputMediaFileUri.getPath();
                final StorageReference mStorage;
                mStorage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://carbookdb.appspot.com/");
                StorageReference filepath = mStorage.child("Photos/Locals").child(namephoto);
                Toast.makeText(this, "ruta: " + fileUri.toString(), Toast.LENGTH_LONG).show();
                filepath.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG,"entro");
                        Uri downloadUri = taskSnapshot.getDownloadUrl();
                        Log.d("name:",downloadUri.toString());
                        Toast.makeText(AdvertCreateActivity.this, downloadUri.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else if(resultCode == RESULT_CANCELED) {
                Log.d(TAG, "ResultCode: RESULT_CANCELED");
            } else {
                Log.d(TAG, "ResultCode: "+Integer.toString(resultCode));
            }
        }
        if(requestCode == PICK_IMAGE_ACTIVITY_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                final String fileName = data != null ? data.getData().getPath() : lastOutputMediaFileUri.getPath();
                final StorageReference mStorage;
                mStorage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://carbookdb.appspot.com/");
                StorageReference filepath = mStorage.child("Photos/Locals").child(namephoto);
                String prefix = "Content:";
                Uri prueba = data.getData();
                filepath.putFile(prueba).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        advertImageView = (ImageView) findViewById(R.id.advertImageView);
                        mStorage.child("Photos/Locals/" + namephoto).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(AdvertCreateActivity.this, uri.toString() , Toast.LENGTH_LONG).show();
                                Picasso.with(AdvertCreateActivity.this).load(uri.toString()).fit().centerCrop().into(advertImageView);
                                newAdverd.setFirstPhotoUrl(uri.toString());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }
                });
                Toast.makeText(this, "Image picked to: " + fileUri.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        File mediaStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
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
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + namephoto);
        }
        else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        }
        else {
            if(type == MEDIA_TYPE_IMAGE_PICK){
                namephoto = "IMG_" + timeStamp + ".jpg";
                mediaFile = new File( mediaStorageDir.getPath() + File.separator + namephoto);
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

    private void validatePermissions(){
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
        photoCaptureSwitch.setEnabled(cameraAvailable);
    }

    private void inicializarAdapter(){
        // DATA para el Spinner
        int carsCount = carsOfClient.size();
        Log.d(TAG,"Cantidad: " + carsCount);
        nameFullCars = new ArrayList<String>();
        for(int i = 0; i < carsCount; i++){
            nameFullCars.add(carsOfClient.get(i).getCarFullName());
        }
        //CREAR ADAPTER ESPECIAL PARA EL SPINNER
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,nameFullCars);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        carInfoSpinner.setAdapter(dataAdapter);
    }

    private void findViewsById() {
        toDateEditText = (EditText) findViewById(R.id.toDateEditText);
        toDateEditText.setInputType(InputType.TYPE_NULL);
        toDateEditText.requestFocus();
    }

    private void setDateTimeField(){
        toDateEditText.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                toDateEditText.setText(dateFormatter.format(newDate.getTime()));
                //Aca cambiar la data del Año
                newAdverd.setEndDate(dateFormatter.format(newDate.getTime()));
                Toast toast = Toast.makeText(context,"Fecha Fin: "+ newDate.getTime().toString() ,Toast.LENGTH_SHORT);
                toast.show();
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View view){
        if(view == toDateEditText){
            toDatePickerDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.client_menu_public, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_nuevo:
                crearAnuncio();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDataCars();
    }

    private void crearAnuncio(){
        newAdverd.setDescription(descriptionEditText.getText().toString());
        newAdverd.setClientId(userSession.getUserId().toString());
        newAdverd.setCreationDate("24/11/2016");
        newAdverd.setStatus("ACT");
        newAdverd.setSelectionDate("");
        newAdverd.setCantApplications("0");
        newAdverd.setPostulationStatus("");

        AndroidNetworking.post(clientServices.ADVERTS_POST)
                .addBodyParameter("ClientId",newAdverd.getClientId())
                .addBodyParameter("CarId",newAdverd.getCarId())
                .addBodyParameter("Description",newAdverd.getDescription())
                .addBodyParameter("CreationDate",newAdverd.getCreationDate())
                .addBodyParameter("EndDate",newAdverd.getEndDate())
                .addBodyParameter("Status",newAdverd.getStatus())
                .addBodyParameter("NPostulations",newAdverd.getCantApplications())
                .setTag(TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            if(response.getString("Code").equalsIgnoreCase("200")){

                                Toast toast = Toast.makeText(AdvertCreateActivity.this,response.getString("Message"),Toast.LENGTH_SHORT);
                                toast.show();
                                AdvertCreateActivity.this.finish();
                            }
                            else
                            {
                                Toast toast = Toast.makeText(AdvertCreateActivity.this,response.getString("Code") + response.getString("Message"),Toast.LENGTH_SHORT);
                                toast.show();
                                Log.d(TAG, "Response Error: " + response.getString("Message"));
                                Log.d(TAG, "Response Error: " + response.getString("ExceptionMessage"));
                                AdvertCreateActivity.this.finish();
                            }


                        }catch(JSONException e){
                            Log.d(TAG,"Error: " + e.getLocalizedMessage());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError " + anError.getErrorBody());
                    }
                });

    }

    private void updateDataCars(){
        String url = clientServices.CLIENT_CAR_SOURCES.replace("{1}",userSession.getUserId().toString());
        AndroidNetworking
                .get(url)
                .setPriority(Priority.MEDIUM)
                .setTag(TAG)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("Code").equalsIgnoreCase("200")) {
                                carsOfClient = Car.buildFromJSONArray(response.getJSONArray("Result"));
                                inicializarAdapter();
                            } else {
                                Log.d(TAG, "Response Error: " + response.getString("Message"));
                                Log.d(TAG, "Response Error: " + response.getString("ExceptionMessage"));
                            }
                        } catch (JSONException e){
                            Log.d(TAG,"Error: " + e.getLocalizedMessage());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError " + anError.getErrorBody());
                    }
                });

    }



}
