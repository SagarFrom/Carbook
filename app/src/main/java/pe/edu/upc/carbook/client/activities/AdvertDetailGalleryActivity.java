package pe.edu.upc.carbook.client.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
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
import pe.edu.upc.carbook.client.adapters.AdvertDetailGalleryAdapter;
import pe.edu.upc.carbook.client.services.clientServices;
import pe.edu.upc.carbook.share.helpers.SharedPreferencesManager;
import pe.edu.upc.carbook.share.models.GalleryAdvert;
import pe.edu.upc.carbook.share.models.User;

public class AdvertDetailGalleryActivity extends AppCompatActivity {
    RecyclerView galleryPhotosRecyclerView;
    AdvertDetailGalleryAdapter advertDetailGalleryAdapter;
    GridLayoutManager galleryPhotosLayoutManager;
    List<GalleryAdvert> galleryAdverts;
    private String IdAdvert;
    private static String TAG = "Carbook";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_activity_advert_detail_gallery);
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

        galleryPhotosRecyclerView = (RecyclerView) findViewById(R.id.galleryPhotosRecyclerView);
        //int spanCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
        galleryPhotosLayoutManager = new GridLayoutManager(this,2);
        galleryAdverts = new ArrayList<>();
        advertDetailGalleryAdapter = new AdvertDetailGalleryAdapter();
        advertDetailGalleryAdapter.setGalleryAdverts(galleryAdverts);
        galleryPhotosRecyclerView.setLayoutManager(galleryPhotosLayoutManager);
        galleryPhotosRecyclerView.setAdapter(advertDetailGalleryAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateGalleryPhotos();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        galleryPhotosLayoutManager.setSpanCount(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2);
    }

    private void updateGalleryPhotos(){
        if(IdAdvert.isEmpty()) IdAdvert = "0";
        String parameters = "/" + IdAdvert + "/photos";
        String url = clientServices.ADVERTS_SOURCES + parameters;
        Log.d(TAG,"URL: " + url);
        AndroidNetworking
                .get(url)
                .setPriority(Priority.LOW)
                .setTag("TEST")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("Code").equalsIgnoreCase("200")){
                                galleryAdverts = GalleryAdvert.buildFromJSONArray(response.getJSONArray("Result"));
                                advertDetailGalleryAdapter.setGalleryAdverts(galleryAdverts);
                                advertDetailGalleryAdapter.notifyDataSetChanged();
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
