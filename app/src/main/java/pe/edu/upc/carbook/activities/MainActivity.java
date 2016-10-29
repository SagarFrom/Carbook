package pe.edu.upc.carbook.activities;

import android.content.Context;
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

                emailTextInputEditText = (TextInputEditText) findViewById(R.id.emailTextInputEditText);
                passwordTextInputEditText =
                        (TextInputEditText) findViewById(R.id.passwordTextInputEditText);

                if(user != null) {
                    user.setEmail(emailTextInputEditText.getText().toString());
                    user.setPassword((passwordTextInputEditText.getText().toString()));
                    Login();

                }

            }
        });

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


                               String result = response.getString("Result");

                               if (result != "null") {

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
