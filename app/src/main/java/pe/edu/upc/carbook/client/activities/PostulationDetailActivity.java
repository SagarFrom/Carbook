package pe.edu.upc.carbook.client.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;

import org.w3c.dom.Text;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.share.models.Postulation;

public class PostulationDetailActivity extends AppCompatActivity {

    private ANImageView logoProviderANImageView;
    private TextView providerNameTextView, descriptionTextView,quotationTextView;
    private RatingBar providerRankRatingBar;
    private AppCompatButton acceptQuotationButton;
    private Postulation postulation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_activity_postulation_detail);
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

        acceptQuotationButton = (AppCompatButton) findViewById(R.id.acceptQuotationButton);
        logoProviderANImageView = (ANImageView) findViewById(R.id.logoProviderANImageView);
        providerNameTextView = (TextView) findViewById(R.id.providerNameTextView);
        descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        quotationTextView = (TextView) findViewById(R.id.quotationTextView);
        providerRankRatingBar = (RatingBar) findViewById(R.id.providerRankRatingBar);

        postulation = Postulation.buildFromBundle(getIntent().getExtras());
        logoProviderANImageView.setDefaultImageResId(R.drawable.default_image);
        logoProviderANImageView.setErrorImageResId(R.mipmap.ic_launcher);
        String urlImageDefault = "http://www.bayaderavirtual.info/imagenes/BOSS%20LOGO.jpg"; // SE BORRARA
        logoProviderANImageView.setImageUrl(urlImageDefault);
        providerNameTextView.setText(postulation.getProviderName());
        descriptionTextView.setText(postulation.getDescription());
        quotationTextView.setText(postulation.getQuotation());
        providerRankRatingBar.setRating(Float.parseFloat(postulation.getProviderRank()));

        acceptQuotationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
