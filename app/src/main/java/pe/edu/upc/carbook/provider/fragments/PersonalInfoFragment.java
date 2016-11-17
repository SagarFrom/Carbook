package pe.edu.upc.carbook.provider.fragments;


import android.content.Context;
import android.os.Bundle;
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
import pe.edu.upc.carbook.share.activities.NavigationActivity;
import pe.edu.upc.carbook.share.helpers.SharedPreferencesManager;
import pe.edu.upc.carbook.share.models.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalInfoFragment extends Fragment {
    TextInputEditText businessNameEditText,documentNumberEditText,emailEditText;
    SharedPreferencesManager spm;
    User userSession;
    Context fragmentContext;

    public PersonalInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.provider_fragment_personal_info, container, false);
        fragmentContext = this.getActivity();
        spm = new SharedPreferencesManager(fragmentContext);
        userSession = spm.getUserOnPreferences();

        businessNameEditText = (TextInputEditText) view.findViewById(R.id.businessNameEditText);
        documentNumberEditText = (TextInputEditText) view.findViewById(R.id.documentNumberEditText);
        emailEditText = (TextInputEditText) view.findViewById(R.id.emailEditText);

        businessNameEditText.setText(userSession.getBusinessName());
        documentNumberEditText.setText(userSession.getDocumentNumber());
        emailEditText.setText(userSession.getEmail());


        Button btnSave = (Button)view.findViewById(R.id.savePersonalInfoBtn);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidNetworking.post(ProviderServices.EDIT_INFO_SOURCES)
                        .addBodyParameter("RazonSocial",businessNameEditText.getText().toString())
                        .addBodyParameter("NumeroDocumento",documentNumberEditText.getText().toString())
                        .addBodyParameter("Rol","PRO")
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
                                        Toast toast = Toast.makeText(fragmentContext,response.getInt("Message"),Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                    else
                                    {
                                        Toast toast = Toast.makeText(fragmentContext,response.getString("Code") + response.getInt("Message"),Toast.LENGTH_SHORT);
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

        return view;
    }

}
