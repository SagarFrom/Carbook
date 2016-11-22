package pe.edu.upc.carbook.provider.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.provider.services.ProviderServices;
import pe.edu.upc.carbook.share.activities.CameraActivity;
import pe.edu.upc.carbook.share.activities.RegisterActivity;
import pe.edu.upc.carbook.share.activities.BaseActivity;
import pe.edu.upc.carbook.share.helpers.SharedPreferencesManager;
import pe.edu.upc.carbook.share.models.GenericPhoto;
import pe.edu.upc.carbook.share.models.Local;
import pe.edu.upc.carbook.share.models.User;

public class LocalManageActivity extends AppCompatActivity {
    EditText nameEditText,addressEditView,capacityEditView;
    Local local;
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
        setContentView(R.layout.provider_activity_local_manage);
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

        spm = new SharedPreferencesManager(LocalManageActivity.this);
        userSession = spm.getUserOnPreferences();

        nameEditText = (EditText)findViewById(R.id.nameEditText);
        addressEditView = (EditText)findViewById(R.id.addressEditView);
        capacityEditView = (EditText)findViewById(R.id.capacityEditView);

        urlImagetextView = (TextView)findViewById(R.id.textViewUrlImage);
        ///

        editor = getSharedPreferences("local", MODE_PRIVATE);

        buttonImageLocalManage = (ImageButton)findViewById(R.id.imageButtonLocalManage);

        buttonImageLocalManage.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v){

                nobundleatributes = false;

                Intent intent = new Intent(LocalManageActivity.this,CameraActivity.class);
                startActivity(intent);


                Toast toast = Toast.makeText(LocalManageActivity.this,"clickonbuttonima",Toast.LENGTH_SHORT);
                toast.show();
            }
        });


        if(nobundleatributes) {

            local = Local.buildFromBundle(getIntent().getExtras());

            getSupportActionBar().setTitle("Registrar Local");
            if (local.getLocalId() > 0) {
                nameEditText.setText(local.getName());
                addressEditView.setText(local.getAddress());
                capacityEditView.setText(local.getCapacity().toString());
                getSupportActionBar().setTitle("Editar Local");
            }
        }

        Button btnSave = (Button)findViewById(R.id.btnLocalSave);
        btnSave.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Toast toast = Toast.makeText(LocalManageActivity.this,editor.getString("urllocal","blank").toString(),Toast.LENGTH_SHORT);
                toast.show();


                boolean hasRequired = true;
                if(TextUtils.isEmpty(nameEditText.getText().toString())) {
                    nameEditText.setError("Campo requerido");
                    hasRequired = false;
                }
                if(TextUtils.isEmpty(addressEditView.getText().toString())) {
                    addressEditView.setError("Campo requerido");
                    hasRequired = false;
                }
                if(TextUtils.isEmpty(capacityEditView.getText().toString())) {
                    capacityEditView.setError("Campo requerido");
                    hasRequired = false;
                }
                if(hasRequired){

                    HashMap<String, String> dicMap = new HashMap<String, String>();
                    dicMap.put("Name", "Local1");
                    dicMap.put("ImageUrl", editor.getString("urllocal","blank").toString());


                        JSONObject gallery1 = new JSONObject();
                    try {
                        //gallery1.put("LocalId",local.getLocalId().toString());
                        gallery1.put("Name", "Local1");
                        //gallery1.put("ImageUrl", editor.getString("urllocal","blank").toString());
                        gallery1.put("ImageUrl","url");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JSONArray galleryArray = new JSONArray();

                    galleryArray.put(gallery1);

                    JSONObject jsongOBJallery = new JSONObject();
                    try {
                        jsongOBJallery.put("Gallery",dicMap);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Toast to = Toast.makeText(LocalManageActivity.this,galleryArray.toString(),Toast.LENGTH_SHORT);
                    to.show();

                    AndroidNetworking.post(ProviderServices.LOCALS_MANAGE)
                            .addBodyParameter("LocalId",local.getLocalId().toString())
                            .addBodyParameter("ProviderId",userSession.getUserId().toString())
                            .addBodyParameter("Name",nameEditText.getText().toString())
                            .addBodyParameter("Address",addressEditView.getText().toString())
                            .addBodyParameter("Capacity",capacityEditView.getText().toString())
                            .addBodyParameter("Latitude","7.546464646400000")
                            .addBodyParameter("Longitude","8.879846516110000")
                            .addBodyParameter("Status","ACT")

                            .addJSONObjectBody(jsongOBJallery)

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

                                            Toast toast = Toast.makeText(LocalManageActivity.this,response.getString("Message"),Toast.LENGTH_SHORT);
                                            toast.show();
                                        }
                                        else
                                        {
                                            Toast toast = Toast.makeText(LocalManageActivity.this,response.getString("Code") + response.getString("Message"),Toast.LENGTH_SHORT);
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
