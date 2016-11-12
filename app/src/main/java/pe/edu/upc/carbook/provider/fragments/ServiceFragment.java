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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.provider.adapters.ServiceAdapter;
import pe.edu.upc.carbook.provider.services.ProviderServices;
import pe.edu.upc.carbook.share.models.Service;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends Fragment {
    private RecyclerView reclycler;
    private LinearLayoutManager layoutManager;
    private ServiceAdapter serviceAdapter;
    private List<Service> services;

    private static String TAG = "Provider Services";

    public ServiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.provider_fragment_service, container, false);
        reclycler = (RecyclerView) view.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(getActivity());
        reclycler.setLayoutManager(layoutManager);

        serviceAdapter = new ServiceAdapter();
        reclycler.setAdapter(serviceAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateServices();
    }

    private void updateServices(){
        AndroidNetworking
                .get(ProviderServices.SERVICES_SOURCES)
                .setPriority(Priority.LOW)
                .setTag(TAG)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("Code").equalsIgnoreCase("200")) {
                                services = Service.buildFromJSONArray(response.getJSONArray("Result"));
                                serviceAdapter.setServices(services);
                                serviceAdapter.notifyDataSetChanged();
                            } else if(response.getString("status").equalsIgnoreCase("error")) {
                                Log.d(TAG, "Response Error: " + response.getString("message"));
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
