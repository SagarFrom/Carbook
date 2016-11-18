package pe.edu.upc.carbook.provider.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import pe.edu.upc.carbook.provider.activities.LocalManageActivity;
import pe.edu.upc.carbook.provider.adapters.LocalAdapter;
import pe.edu.upc.carbook.provider.services.ProviderServices;
import pe.edu.upc.carbook.share.helpers.SharedPreferencesManager;
import pe.edu.upc.carbook.share.models.Local;
import pe.edu.upc.carbook.share.models.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocalFragment extends Fragment {

    private RecyclerView recycler;
    private LinearLayoutManager linearLayout;
    private LocalAdapter adapter;
    private List<Local> locals;
    private static String TAG = "Provider Locals";
    SharedPreferencesManager spm;
    User userSession;

    public LocalFragment() {
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
        View view = inflater.inflate(R.layout.provider_fragment_local, container, false);

        FloatingActionButton myFab = (FloatingActionButton)  view.findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LocalManageActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        recycler = (RecyclerView)view.findViewById(R.id.recycler);
        linearLayout = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(linearLayout);

        adapter = new LocalAdapter();
        recycler.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateServices();
    }

    private void updateServices(){
        String url = ProviderServices.LOCALS_SOURCES.replace("{1}",userSession.getUserId().toString());
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
                                locals = Local.buildFromJSONArray(response.getJSONArray("Result"));
                                adapter.setLocals(locals);
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
