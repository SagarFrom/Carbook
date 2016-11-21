package pe.edu.upc.carbook.client.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.client.adapters.PostulationAdapter;
import pe.edu.upc.carbook.client.services.clientServices;
import pe.edu.upc.carbook.share.models.Postulation;

public class PostulationsActivity extends AppCompatActivity {

    private List<Postulation> postulations = new ArrayList<>();
    private RecyclerView postulationsRecyclerView;
    private PostulationAdapter postulationAdapter;
    private static String TAG = "Carbook";
    private String IdAdvert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_activity_postulations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        IdAdvert = getIntent().getExtras().getString("idAdvert");
        Log.d(TAG,"IdAdvert: " + IdAdvert);

        postulationsRecyclerView = (RecyclerView) findViewById(R.id.postulationsRecyclerView);
        postulationAdapter = new PostulationAdapter();
        postulationAdapter.setPostulations(postulations);
        postulationsRecyclerView.setAdapter(postulationAdapter);
        postulationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePostulations();
    }

    private void updatePostulations(){
        if(IdAdvert.isEmpty()) IdAdvert = "0";
        String url = clientServices.ADVERTS_SOURCES.replace("{1}",IdAdvert).replace("{2}","postulations");
        Log.d(TAG,"URL: " + url);
        AndroidNetworking
                .get(url)
                .setPriority(Priority.LOW)
                .setTag(TAG)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            if(response.getString("Code").equalsIgnoreCase("200")){
                                postulations = Postulation.buildFromJSONArray(response.getJSONArray("Result"));
                                postulationAdapter.setPostulations(postulations);
                                postulationAdapter.notifyDataSetChanged();
                            }else{
                                Log.d(TAG, "Response Error: " + response.getString("Message"));
                                Log.d(TAG, "Response Error: " + response.getString("ExceptionMessage"));
                            }
                        }catch (JSONException e){
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
