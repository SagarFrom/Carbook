package pe.edu.upc.carbook.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
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
import pe.edu.upc.carbook.models.User;
import pe.edu.upc.carbook.services.CarbookService;

public class MainActivity extends AppCompatActivity {

    TextInputEditText emailTextInputEditText,
            passwordTextInputEditText;
    User user;

    private static String TAG = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                        Login();
                }
                else{
                    Login_in_BD();
                    //user.save();
                }
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
        AndroidNetworking.post(CarbookService.SOURCES_URL)
                .addBodyParameter("Correo",user.getEmail())
                .addBodyParameter("Contrasena",user.getPassword())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                       @Override
                       public void onResponse(JSONObject response) {

                           try {


                               user.setEmail(emailTextInputEditText.getText().toString());
                               user.setPassword((passwordTextInputEditText.getText().toString()));


                               String result = response.getString("Result");

                               if (result != "null") {

                                   User user_Exist =  user.findByUserName(user.getEmail());
                                   if(user_Exist == null)
                                     user.save();

                                   Context context = getApplicationContext();
                                   CharSequence text = "Success";
                                   int duration = Toast.LENGTH_SHORT;

                                   Toast toast = Toast.makeText(context, result, duration);
                                   toast.show();
                               }
                               //response.getJSONObject("Result");
                           }catch(JSONException e){

                           }


                       }

                       @Override
                       public void onError(ANError anError) {

                           Log.d(TAG,"fail");
                           Log.d(TAG,user.getEmail());
                           Log.d(TAG,user.getPassword());
                       }
                    }

                );



    }
}
