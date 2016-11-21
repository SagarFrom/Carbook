package pe.edu.upc.carbook.client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.share.models.Advert;

public class AdvertDetailActivity extends AppCompatActivity {

    private Advert advert;
    ANImageView firstPhotoANImageView;
    TextView nameCarModelTextView, descriptionTextView, fechaCreacionTextView,
            fechaFinTextView, cantidadPostulantesTextView;
    Button verGaleriaButton, verPostulacionesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_activity_advert_detail);
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
        verGaleriaButton = (Button) findViewById(R.id.verGaleriaButton);
        firstPhotoANImageView = (ANImageView) findViewById(R.id.firstPhotoANImageView);
        nameCarModelTextView = (TextView) findViewById(R.id.nameCarModelTextView);
        descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        fechaCreacionTextView = (TextView) findViewById(R.id.fechaCreacionTextView);
        fechaFinTextView = (TextView) findViewById(R.id.fechaFinTextView);
        cantidadPostulantesTextView = (TextView) findViewById(R.id.cantidadPostulantesTextView);
        advert = Advert.buildFromBundle(getIntent().getExtras());
        firstPhotoANImageView.setImageUrl(advert.getFirstPhotoUrl());
        firstPhotoANImageView.setErrorImageResId(R.mipmap.ic_launcher);
        firstPhotoANImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        nameCarModelTextView.setText(advert.getCarInfo()); // POR AHORA PRIMERO QUIERO QUE VER SI FUNCIONA EL INTENT
        descriptionTextView.setText(advert.getDescription());
        fechaCreacionTextView.setText(advert.getCreationDate());
        fechaFinTextView.setText(advert.getEndDate());
        cantidadPostulantesTextView.setText(advert.getCantApplications());

        verGaleriaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itemItent  = new Intent(v.getContext(), AdvertDetailGalleryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("idAdvert",advert.getAdvertId());
                itemItent.putExtras(bundle);
                v.getContext().startActivity(itemItent);
            }
        });
    }

}
