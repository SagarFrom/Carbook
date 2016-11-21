package pe.edu.upc.carbook.provider.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.provider.adapters.AvailableAdvertAdapter;
import pe.edu.upc.carbook.provider.services.ProviderServices;
import pe.edu.upc.carbook.share.helpers.SharedPreferencesManager;
import pe.edu.upc.carbook.share.models.Advert;
import pe.edu.upc.carbook.share.models.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class AvailableAdvertFragment extends Fragment {
    private RecyclerView reclycler;
    private LinearLayoutManager layoutManager;
    private AvailableAdvertAdapter advertsAdapter;
    private List<Advert> adverts;
    User userSession;
    SharedPreferencesManager spm;

    private static String TAG = "Provider Services";

    public AvailableAdvertFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spm = new SharedPreferencesManager(this.getActivity());
        userSession = spm.getUserOnPreferences();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.provider_fragment_available_advert, container, false);
        reclycler = (RecyclerView) view.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(getActivity());
        reclycler.setLayoutManager(layoutManager);

        advertsAdapter = new AvailableAdvertAdapter();
        reclycler.setAdapter(advertsAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAdverts();
    }

    private void updateAdverts(){
        String url = ProviderServices.ADVERTS_SOURCES.replace("{1}",userSession.getUserId().toString()).replace("{2}","true");
        AndroidNetworking
                .get(url)
                .setPriority(Priority.LOW)
                .setTag(TAG)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("Code").equalsIgnoreCase("200")) {
                                adverts = Advert.buildFromJSONArray(response.getJSONArray("Result"));
                                advertsAdapter.setAdverts(adverts);
                                advertsAdapter.notifyDataSetChanged();
                            } else{
                                Toast toast = Toast.makeText(getActivity(),response.getString("Code") + response.getString("Message"),Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        } catch (JSONException e) {
                            Log.d(TAG, "Error: " + e.getLocalizedMessage());
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
