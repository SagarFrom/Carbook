package pe.edu.upc.carbook.provider.activities;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.provider.services.ProviderServices;
import pe.edu.upc.carbook.share.helpers.SharedPreferencesManager;
import pe.edu.upc.carbook.share.models.Advert;
import pe.edu.upc.carbook.share.models.User;

public class AdvertDetailActivity extends AppCompatActivity {
    private Button postulateButton;
    private PopupWindow popupWindow;
    private CoordinatorLayout coordinatorLayout;
    User userSession;
    SharedPreferencesManager spm;

    ImageView pictureImageView;
    TextView clientNameTextView,carInfoTextView,descriptionTextView,beginDateTextView,endDateTextView;
    Advert advert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_activity_advert_detail);
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
        getSupportActionBar().setTitle("Detalles Anuncio");

        spm = new SharedPreferencesManager(AdvertDetailActivity.this);
        userSession = spm.getUserOnPreferences();

        pictureImageView = (ImageView) findViewById(R.id.pictureImageView);
        clientNameTextView = (TextView) findViewById(R.id.clientNameTextView);
        carInfoTextView = (TextView) findViewById(R.id.carInfoTextView);
        descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        beginDateTextView = (TextView) findViewById(R.id.beginDateTextView);
        endDateTextView = (TextView) findViewById(R.id.endDateTextView);

        advert = Advert.buildFromBundle(getIntent().getExtras());
        if(advert.getFirstPhotoUrl() != ""){
            Glide.with(getBaseContext())
                    .load(advert.getFirstPhotoUrl())
                    .centerCrop()
                    .into(pictureImageView);
        }
        clientNameTextView.setText(advert.getClientName());
        carInfoTextView.setText(advert.getCarInfo());
        descriptionTextView.setText(advert.getDescription());
        beginDateTextView.setText(advert.getCreationDate());
        endDateTextView.setText(advert.getEndDate());

        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinator);
        postulateButton = (Button)findViewById(R.id.postulateButton);

        postulateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LayoutInflater inflate = (LayoutInflater) AdvertDetailActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
//                View customView = inflate.inflate(R.layout.provider_activity_postulate,null);
//                popupWindow = new PopupWindow(
//                        customView,
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        false
//                );
//                popupWindow.setOutsideTouchable(false);
//                popupWindow.setFocusable(true);
//                popupWindow.update();
//                if(Build.VERSION.SDK_INT>=21){
//                    popupWindow.setElevation(5.0f);
//                }
//                ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
//
//                // Set a click listener for the popup window close button
//                closeButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        // Dismiss the popup window
//                        popupWindow.dismiss();
//                    }
//                });
//                popupWindow.showAtLocation(coordinatorLayout, Gravity.CENTER,0,0);

                // custom dialog
                final Dialog dialog = new Dialog(AdvertDetailActivity.this);
                dialog.setContentView(R.layout.provider_activity_postulate);
                dialog.setTitle("Title...");

                ImageButton dialogButton = (ImageButton) dialog.findViewById(R.id.ib_close);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });



                Button saveButton = (Button) dialog.findViewById(R.id.btnPostulateSave);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    EditText quotationEditText = (EditText)dialog.findViewById(R.id.quotationEditText);
                    EditText descriptionEditText   = (EditText)dialog.findViewById(R.id.descriptionEditText);
                    @Override
                    public void onClick(View v) {
                        AndroidNetworking.post(ProviderServices.ADVERTS_POSTULATE)
                                .addBodyParameter("AdvertId",advert.getAdvertId())
                                .addBodyParameter("ProviderId",userSession.getUserId().toString())
                                .addBodyParameter("Quotation",quotationEditText.getText().toString())
                                .addBodyParameter("Description",descriptionEditText.getText().toString())

                                .setTag("Test")
                                .setPriority(Priority.MEDIUM)
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        try{

                                            Integer resultCode = response.getInt("Code");
                                            if(resultCode == 200){

                                                Toast toast = Toast.makeText(AdvertDetailActivity.this,response.getString("Message"),Toast.LENGTH_SHORT);
                                                toast.show();
                                                dialog.dismiss();
                                            }
                                            else
                                            {
                                                Toast toast = Toast.makeText(AdvertDetailActivity.this,response.getString("Code") + response.getString("Message"),Toast.LENGTH_SHORT);
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

                dialog.show();
            }
        });
    }

}
