package pe.edu.upc.carbook.client.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import pe.edu.upc.carbook.share.models.User;

/**
 * Created by hypnotic on 18/11/2016.
 */

public class DataClientFragment extends Fragment {
    TextInputEditText clientNameEditText,clientFatherNameEditText,clientMotherNameEditText, emailEditText
            ,documentNumberEditText;
    SharedPreferencesManager spm;
    Button saveDataClientButton;
    Context fragmentContext;
    User userSession;

    public DataClientFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.client_frangment_data,container, false);
        spm = new SharedPreferencesManager(this.getActivity());
        userSession = spm.getUserOnPreferences();
        clientNameEditText = (TextInputEditText)view.findViewById(R.id.clientNameEditText);
        clientFatherNameEditText = (TextInputEditText)view.findViewById(R.id.clientFatherLastNameEditText);
        clientMotherNameEditText = (TextInputEditText)view.findViewById(R.id.clientMotherLastNameEditText);
        emailEditText = (TextInputEditText)view.findViewById(R.id.emailEditText);
        documentNumberEditText = (TextInputEditText)view.findViewById(R.id.documentNumberEditText);
        saveDataClientButton = (Button)view.findViewById(R.id.saveDataClientButton);

        clientNameEditText.setText(userSession.getName());
        emailEditText.setText(userSession.getEmail());
        documentNumberEditText.setText(userSession.getDocumentNumber());

        saveDataClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidNetworking.post(ProviderServices.EDIT_INFO_SOURCES)
                        .addBodyParameter("Nombre",clientNameEditText.getText().toString())
                        .addBodyParameter("ApellidoPaterno",clientFatherNameEditText.getText().toString())
                        .addBodyParameter("ApellidoMaterno",clientFatherNameEditText.getText().toString())
                        .addBodyParameter("NumeroDocumento",documentNumberEditText.getText().toString())
                        .addBodyParameter("Rol","CLI")
                        .addBodyParameter("UsuarioId",userSession.getUserId().toString())
                        .setTag("Test")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try{

                                    Integer resultCode = response.getInt("Code");
                                    if(resultCode == 200){
                                        spm.saveUserOnPreferences(userSession);
                                        Toast toast = Toast.makeText(getActivity(),response.getString("Message"),Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                    else
                                    {
                                        Toast toast = Toast.makeText(getActivity(),response.getString("Code") + response.getString("Message"),Toast.LENGTH_SHORT);
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
        return  view;
    }
}
