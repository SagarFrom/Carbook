package pe.edu.upc.carbook.share.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.share.helpers.SharedPreferencesManager;
import pe.edu.upc.carbook.share.models.User;
import pe.edu.upc.carbook.share.services.*;

public class LoginActivity extends BaseActivity {

    TextInputEditText emailTextInputEditText,
            passwordTextInputEditText;
    User user;
    SharedPreferencesManager spm;

    private static String TAG = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spm = new SharedPreferencesManager(LoginActivity.this);

        //Update user
        user = new User();


        AppCompatButton signinButton = (AppCompatButton) findViewById(R.id.signinButton);
        signinButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //if user has internet
                boolean connection;
                connection = haveNetworkConnection();
                emailTextInputEditText = (TextInputEditText) findViewById(R.id.emailTextInputEditText);
                passwordTextInputEditText = (TextInputEditText) findViewById(R.id.passwordTextInputEditText);

                if(connection){
                    //ChangeScreem(RegisterActivity.class,true);
                    Login();
                }
                else{
                    //Login_in_BD();
                    //user.save();
                }
            }
        });

        AppCompatButton signupButton = (AppCompatButton) findViewById(R.id.siginUp);
        signupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                ChangeScreem(RegisterActivity.class,true);
            }
        });
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private void Login_in_BD(){
        int duration = Toast.LENGTH_SHORT;
        Context context = getApplicationContext();

        try{
            user.setEmail(emailTextInputEditText.getText().toString());
            user.setPassword((passwordTextInputEditText.getText().toString()));

            User user_Exist =  user.findByUserName(user.getEmail());

            if(user_Exist == null) {
                //user.save();
                Toast toast = Toast.makeText(context,"no existe" + user.getEmail(), duration);
                toast.show();
            }
            else{
                Toast toast = Toast.makeText(context,"sucess" + user.getEmail(), duration);
                toast.show();
            }


        }catch(Error e){

        }
    }

    private void Login(){

        AndroidNetworking.post(LoginService.LOGIN_SOURCES)
                .addBodyParameter("Correo",emailTextInputEditText.getText().toString())
                .addBodyParameter("Contrasena",passwordTextInputEditText.getText().toString())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                       @Override
                       public void onResponse(JSONObject response) {

                           try {
                               Integer resultCode = response.getInt("Code");
                               if(resultCode == 200){
                                   JSONObject result = response.getJSONObject("Result");
                                   user.setEmail(result.getString("Correo"));
                                   user.setRole(result.getString("Rol"));
                                   user.setUserId(result.getInt("UsuarioId"));

                                   switch (user.getRole()){
                                       case "PRO":
                                           user.setDocumentNumber(result.getString("NumeroDocumento"));
                                           user.setBusinessName(result.getString("RazonSocial"));
                                           break;
                                       case "CLI":
                                           user.setName(result.getString("Nombre"));
                                           user.setLastName(result.getString("ApellidoPaterno"));
                                           user.setDNI(result.getString("DNI"));
                                           break;
                                   }

                                   spm.saveUserOnPreferences(user);

                                   Intent intent = new Intent(LoginActivity.this,NavigationActivity.class);
                                   startActivity(intent);

                               }else{
                                   Context context = getApplicationContext();
                                   Toast toast = Toast.makeText(context, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT);
                                   toast.show();
                               }
                           }catch(JSONException e){

                           }


                       }

                       @Override
                       public void onError(ANError anError) {

                           Log.d(TAG,"fail");
                           Log.d(TAG,anError.getMessage());
                           Log.d(TAG,anError.getErrorBody());
                           Log.d(TAG,anError.getErrorDetail());
                       }
                    }

                );
    }
}
