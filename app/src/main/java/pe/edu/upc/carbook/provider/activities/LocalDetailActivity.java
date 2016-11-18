package pe.edu.upc.carbook.provider.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.share.models.Local;

public class LocalDetailActivity extends AppCompatActivity {

    ImageView pictureImageView;
    TextView nameTextView,addressTextView,capacityTextView;
    Local local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_activity_local_detail);
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

        pictureImageView = (ImageView) findViewById(R.id.pictureImageView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        addressTextView = (TextView) findViewById(R.id.addressTextView);
        capacityTextView = (TextView) findViewById(R.id.capacityTextView);

        local = Local.buildFromBundle(getIntent().getExtras());

        getSupportActionBar().setTitle(local.getName());
        if(local.getFirstPhotoUrl() != ""){
            Glide.with(getBaseContext())
                    .load(local.getFirstPhotoUrl())
                    .centerCrop()
                    .into(pictureImageView);
        }
        nameTextView.setText(local.getName());
        addressTextView.setText(local.getAddress());
        capacityTextView.setText(local.getCapacity().toString());

    }

}
