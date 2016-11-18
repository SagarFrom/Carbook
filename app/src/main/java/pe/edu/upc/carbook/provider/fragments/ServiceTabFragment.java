package pe.edu.upc.carbook.provider.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import pe.edu.upc.carbook.provider.adapters.ServiceGridAdapter;
import pe.edu.upc.carbook.provider.services.ProviderServices;
import pe.edu.upc.carbook.share.models.Service;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceTabFragment extends Fragment {

    private RecyclerView recycler;
    private GridLayoutManager layoutManager;
    private ServiceGridAdapter adapter;
    private List<Service> services;

    private static String TAG = "Provider Services";

    public ServiceTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.provider_content_fragment_local, container, false);

        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recycler.setLayoutManager(layoutManager);

        adapter = new ServiceGridAdapter();

        recycler.setAdapter(adapter);
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
                                adapter.setServices(services);
                                adapter.notifyDataSetChanged();
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
