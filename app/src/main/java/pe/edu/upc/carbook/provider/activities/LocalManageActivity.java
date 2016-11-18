package pe.edu.upc.carbook.provider.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.provider.services.ProviderServices;
import pe.edu.upc.carbook.share.helpers.SharedPreferencesManager;
import pe.edu.upc.carbook.share.models.Local;
import pe.edu.upc.carbook.share.models.User;

public class LocalManageActivity extends AppCompatActivity {
    EditText nameEditText,addressEditView,capacityEditView;
    Local local;
    User userSession;
    SharedPreferencesManager spm;


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

        local = Local.buildFromBundle(getIntent().getExtras());

        getSupportActionBar().setTitle("Registrar Local");
        if(local.getLocalId() > 0){
            nameEditText.setText(local.getName());
            addressEditView.setText(local.getAddress());
            capacityEditView.setText(local.getCapacity().toString());
            getSupportActionBar().setTitle("Editar Local");
        }

        Button btnSave = (Button)findViewById(R.id.btnLocalSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidNetworking.post(ProviderServices.LOCALS_MANAGE)
                        .addBodyParameter("LocalId",local.getLocalId().toString())
                        .addBodyParameter("ProviderId",userSession.getUserId().toString())
                        .addBodyParameter("Name",nameEditText.getText().toString())
                        .addBodyParameter("Address",addressEditView.getText().toString())
                        .addBodyParameter("Capacity",capacityEditView.getText().toString())
                        .addBodyParameter("Latitude","7.546464646400000")
                        .addBodyParameter("Longitude","8.879846516110000")
                        .addBodyParameter("Status","ACT")
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
        });
    }

}
