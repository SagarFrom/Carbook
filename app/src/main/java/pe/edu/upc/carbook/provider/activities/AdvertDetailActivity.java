package pe.edu.upc.carbook.provider.activities;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.share.models.Advert;

public class AdvertDetailActivity extends AppCompatActivity {
    private Button postulateButton;
    private PopupWindow popupWindow;
    private CoordinatorLayout coordinatorLayout;

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
                LayoutInflater inflate = (LayoutInflater) AdvertDetailActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                View customView = inflate.inflate(R.layout.provider_activity_postulate,null);
                popupWindow = new PopupWindow(
                        customView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        false
                );
                popupWindow.setOutsideTouchable(false);
                popupWindow.setFocusable(true);
                popupWindow.update();
                if(Build.VERSION.SDK_INT>=21){
                    popupWindow.setElevation(5.0f);
                }
                ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

                // Set a click listener for the popup window close button
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAtLocation(coordinatorLayout, Gravity.CENTER,0,0);
            }
        });
    }

}
