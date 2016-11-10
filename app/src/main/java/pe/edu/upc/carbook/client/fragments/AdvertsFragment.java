package pe.edu.upc.carbook.client.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import java.util.ArrayList;
import java.util.List;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.client.adapters.AdvertAdapter;
import pe.edu.upc.carbook.client.services.clientServices;
import pe.edu.upc.carbook.share.models.Advert;

/**
 * Created by usuario on 6/11/2016.
 */

public class AdvertsFragment extends Fragment {

    private LinearLayoutManager linearLayout;
    private List<Advert> adverts = new ArrayList<>();
    AdvertAdapter advertAdapter;
    private RecyclerView advertsRecyclerView;
    private static String TAG = "Carbook";

    public AdvertsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.client_fragment_adverts, container, false);

        advertAdapter = new AdvertAdapter();
        advertAdapter.setAdverts(adverts);
        advertAdapter.setActivity(getActivity());
        linearLayout = new LinearLayoutManager(getActivity());
        advertsRecyclerView = (RecyclerView)view.findViewById(R.id.advertsRecyclerView);
        advertsRecyclerView.setLayoutManager(linearLayout);
        advertsRecyclerView.setAdapter(advertAdapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return view;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        updateAdverts();
    }
    private void updateAdverts(){
        AndroidNetworking
                .get(clientServices.ADVERTS_SOURCES + "/2")
                //.addPathParameter("/","2") //Ahorita esta en duro
                .setPriority(Priority.LOW)
                .setTag("TEST")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if(response.getString("Code").equalsIgnoreCase("200")){
                                adverts = Advert.buildFromJSONArray(response.getJSONArray("Result"));
                                advertAdapter.setAdverts(adverts);
                                advertAdapter.notifyDataSetChanged();
                            }else{
                                Log.d(TAG, "Response Error: " + response.getString("Message"));
                                Log.d(TAG, "Response Error: " + response.getString("ExceptionMessage"));
                            }
                        } catch (JSONException e){
                            Log.d(TAG,"Error: " + e.getLocalizedMessage());
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
