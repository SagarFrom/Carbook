package pe.edu.upc.carbook.share.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.share.activities.BaseActivity;
import pe.edu.upc.carbook.share.services.LoginService;

/**
 * Created by Juanxps on 11/7/16.
 */

public class RegisterActivity extends BaseActivity {

    Spinner spinnerRol;
    TextInputEditText textEditCompanyName,textEditRuc
            ,textEditEmail,textEditPassword,textEditName,
            textEditMotherLastName,textEditFatherLastName,
            textEditDate,textEditDni;

    TextInputLayout textLayoutCompanyName,textLayoutRuc;



    @Override
    protected void onCreate(Bundle savedInstanceStatte){
        super.onCreate(savedInstanceStatte);
        setContentView(R.layout.activity_register);


        textEditCompanyName = (TextInputEditText) findViewById(R.id.companynameTextInputEditText);
        textEditRuc = (TextInputEditText) findViewById(R.id.rucTextInputEditText);

        textEditEmail = (TextInputEditText) findViewById(R.id.emailTextInputEditText);
        textEditPassword = (TextInputEditText) findViewById(R.id.passwordTextInputEditText);
        textEditName = (TextInputEditText) findViewById(R.id.nombreTextInputEditText);
        textEditFatherLastName = (TextInputEditText) findViewById(R.id.fatherlastnameTextInputEditText);
        textEditMotherLastName = (TextInputEditText) findViewById(R.id.motherlastnameTextInputEditText);
        textEditDate = (TextInputEditText) findViewById(R.id.birthdateTextInputEditText);
        textEditDni = (TextInputEditText) findViewById(R.id.dniTextInputEditText);

        textLayoutCompanyName = (TextInputLayout) findViewById(R.id.companynameTextInputLayout);
        textLayoutRuc = (TextInputLayout) findViewById(R.id.rucTextInputLayout);

        spinnerRol = (Spinner) findViewById(R.id.rolnamesspinner);

        IsRolSelected();



        AppCompatButton saveButtton = (AppCompatButton) findViewById(R.id.saveButton);
        saveButtton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                RegisterUser();

            }
        });

    }

    private void IsRolSelected(){
        spinnerRol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getBaseContext(),selected,Toast.LENGTH_LONG).show();

                if(textEditRuc.isEnabled()) {
                    //textCompanyName.setEnabled(false);
                    textEditCompanyName.setHeight(0);
                    textEditRuc.setHeight(0);

                    textEditCompanyName.setVisibility(view.INVISIBLE);
                    textLayoutCompanyName.setVisibility(view.INVISIBLE);
                    textEditRuc.setVisibility(view.INVISIBLE);
                    textLayoutRuc.setVisibility(view.INVISIBLE);

                    textEditRuc.setEnabled(false);
                    textLayoutRuc.setEnabled(false);
                    textEditCompanyName.setEnabled(false);
                    textLayoutCompanyName.setEnabled(false);
                }else{

                    textEditCompanyName.setHeight(textEditName.getHeight());
                    textEditRuc.setHeight(textEditName.getHeight());

                    textEditCompanyName.setVisibility(view.VISIBLE);
                    textLayoutCompanyName.setVisibility(view.VISIBLE);
                    textEditRuc.setVisibility(view.VISIBLE);
                    textLayoutRuc.setVisibility(view.VISIBLE);

                    textEditRuc.setEnabled(true);
                    textLayoutRuc.setEnabled(true);
                    textEditCompanyName.setEnabled(true);
                    textLayoutCompanyName.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void RegisterUser(){

        //AndroidNetworking.post(LoginService.REGISTER_SOURCES)
          //      .addBodyParameter("")


        AndroidNetworking.post(LoginService.REGISTER_SOURCES)
                .addBodyParameter("Correo",textEditEmail.getText().toString())
                .addBodyParameter("Contrasena",textEditPassword.getText().toString())
                .addBodyParameter("Nombre",textEditName.getText().toString())
                .addBodyParameter("ApellidoPaterno",textEditFatherLastName.getText().toString())
                .addBodyParameter("ApellidoMaterno",textEditMotherLastName.getText().toString())
                .addBodyParameter("FechaNacimiento",textEditDate.getText().toString())
                .addBodyParameter("DNI",textEditDni.getText().toString())
                .addBodyParameter("RazonSocial",textEditCompanyName.getText().toString())
                .addBodyParameter("NumeroDocumento",textEditRuc.getText().toString())
                .addBodyParameter("Rol",spinnerRol.getSelectedItem().toString() == "Proveedor" ? "PRO" : "CLI")
                .addBodyParameter("Estado","ACT")

                .setTag("Test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try{

                            Integer resultCode = response.getInt("Code");
                            if(resultCode == 200){
                                Toast toast = Toast.makeText(getApplicationContext(),"Success: " +
                                        response.getString("Result"),Toast.LENGTH_SHORT);
                                toast.show();
                            }
                            else
                            {
                                Toast toast = Toast.makeText(getApplicationContext(),response.getInt("Message"),Toast.LENGTH_SHORT);
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
