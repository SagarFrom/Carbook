package pe.edu.upc.carbook.client.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.provider.activities.LocalManageActivity;
import pe.edu.upc.carbook.provider.services.ProviderServices;
import pe.edu.upc.carbook.share.activities.CameraActivity;
import pe.edu.upc.carbook.share.helpers.SharedPreferencesManager;
import pe.edu.upc.carbook.share.models.Car;
import pe.edu.upc.carbook.share.models.Local;
import pe.edu.upc.carbook.share.models.User;

public class CarManageActivity extends AppCompatActivity {

    EditText brandEditText,modelEditText,plateNumberEditText;
    Car car;
    User userSession;
    SharedPreferencesManager spm;

    TextView urlImagetextView;
    //
    ImageButton buttonImageLocalManage;
    boolean nobundleatributes = true;

    public SharedPreferences editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_manage);
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

        spm = new SharedPreferencesManager(CarManageActivity.this);
        userSession = spm.getUserOnPreferences();

        brandEditText = (EditText)findViewById(R.id.brandEditText);
        modelEditText = (EditText)findViewById(R.id.modelEditText);
        plateNumberEditText = (EditText)findViewById(R.id.plateNumberEditText);
        urlImagetextView = (TextView)findViewById(R.id.textViewUrlImage);
        ///

        editor = getSharedPreferences("car", MODE_PRIVATE);

        buttonImageLocalManage = (ImageButton)findViewById(R.id.imageButtonLocalManage);

        buttonImageLocalManage.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v){

                nobundleatributes = false;

                Intent intent = new Intent(CarManageActivity.this,CameraActivity.class);
                startActivity(intent);


                Toast toast = Toast.makeText(CarManageActivity.this,"clickonbuttonima",Toast.LENGTH_SHORT);
                toast.show();
            }
        });


        if(nobundleatributes) {

            car = car.buildFromBundle(getIntent().getExtras());

            getSupportActionBar().setTitle("Registrar Local");
            if (Integer.parseInt(car.getCarId()) > 0) {
                brandEditText.setText(car.getBrand());
                modelEditText.setText(car.getModel());
                plateNumberEditText.setText(car.getPlateNumber().toString());
                getSupportActionBar().setTitle("Editar Carro");
            }
        }

        Button btnSave = (Button)findViewById(R.id.btnLocalSave);
        btnSave.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Toast toast = Toast.makeText(CarManageActivity.this,editor.getString("urllocal","blank").toString(),Toast.LENGTH_SHORT);
                toast.show();


                boolean hasRequired = true;
                if(TextUtils.isEmpty(brandEditText.getText().toString())) {
                    brandEditText.setError("Campo requerido");
                    hasRequired = false;
                }
                if(TextUtils.isEmpty(modelEditText.getText().toString())) {
                    modelEditText.setError("Campo requerido");
                    hasRequired = false;
                }
                if(TextUtils.isEmpty(plateNumberEditText.getText().toString())) {
                    plateNumberEditText.setError("Campo requerido");
                    hasRequired = false;
                }
                if(hasRequired){

                    //HashMap<String, String> dicMap = new HashMap<String, String>();
                    //dicMap.put("Name", "Local1");
                    //dicMap.put("ImageUrl", editor.getString("urllocal","blank").toString());


                    JSONObject gallery1 = new JSONObject();
                    try {
                        //gallery1.put("LocalId",local.getLocalId().toString());
                        gallery1.put("Name", "Local1");
                        gallery1.put("ImageUrl", editor.getString("urllocal","blank").toString());
                        //gallery1.put("ImageUrl","url");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                    JSONArray galleryArray = new JSONArray();
                    galleryArray.put(gallery1);


                    JSONObject restoObj = new JSONObject();
                    try {
                        restoObj.put("","");
                        //restoObj.put("LocalId",local.getLocalId().toString());
                        //restoObj.put("ProviderId",userSession.getUserId().toString());
                        //restoObj.put("Name",nameEditText.getText().toString());
                        //restoObj.put("Address",addressEditView.getText().toString());
                        //restoObj.put("Capacity",capacityEditView.getText().toString());
                        //restoObj.put("Latitude","7.546464646400000");
                        //restoObj.put("Longitude","8.879846516110000");
                        //restoObj.put("Status","ACT");
                        //restoObj.put("Gallery",galleryArray);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JSONObject jsongOBJallery = new JSONObject();
                    try {

                        jsongOBJallery.put("Gallery",galleryArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Toast to = Toast.makeText(CarManageActivity.this,restoObj.toString(),Toast.LENGTH_SHORT);
                    to.show();

                    AndroidNetworking.post(ProviderServices.LOCALS_MANAGE)
                            //.addBodyParameter("LocalId",local.getLocalId().toString())
                            //.addBodyParameter("ProviderId",userSession.getUserId().toString())
                            //.addBodyParameter("Name",nameEditText.getText().toString())
                            //.addBodyParameter("Address",addressEditView.getText().toString())
                            //.addBodyParameter("Capacity",capacityEditView.getText().toString())
                            //.addBodyParameter("Latitude","7.546464646400000")
                            //.addBodyParameter("Longitude","8.879846516110000")
                            //.addBodyParameter("Status","ACT")

                            .addJSONObjectBody(restoObj)

                            //.addBodyParameter("FirstPhotoUrl",editor.getString("urllocal","blank").toString())

                            .setTag("Test")
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try{

                                        Integer resultCode = response.getInt("Code");
                                        if(resultCode == 200){

                                            Toast toast = Toast.makeText(CarManageActivity.this,response.getString("Message"),Toast.LENGTH_SHORT);
                                            toast.show();
                                        }
                                        else
                                        {
                                            Toast toast = Toast.makeText(CarManageActivity.this,response.getString("Code") + response.getString("Message"),Toast.LENGTH_SHORT);
                                            toast.show();
                                        }


                                    }catch(JSONException e){

                                    }
                                }

                                @Override
                                public void onError(ANError anError) {
                                }
                            });
                }
            }
        });
    }

}
