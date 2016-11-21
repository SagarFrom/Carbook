package pe.edu.upc.carbook.client.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.widget.ANImageView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.client.services.clientServices;
import pe.edu.upc.carbook.share.models.Postulation;

public class PostulationDetailActivity extends AppCompatActivity {

    private ANImageView logoProviderANImageView;
    private TextView providerNameTextView, descriptionTextView,quotationTextView;
    private RatingBar providerRankRatingBar;
    private AppCompatButton acceptQuotationButton;
    private static String TAG = "Carbook";
    private Postulation postulation;
    final Context context = this;

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
        quotationTextView.setText(postulation.getQuotationWithSoles());
        providerRankRatingBar.setRating(Float.parseFloat(postulation.getProviderRank()));

        acceptQuotationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title
                alertDialogBuilder.setTitle("Aceptar Cotización");

                // set dialog message
                alertDialogBuilder
                        .setMessage("¡Presione SI para Aceptar!")
                        .setCancelable(false)
                        .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                                AcceptPostulation();
                                PostulationDetailActivity.this.finish();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });
    }

    private void AcceptPostulation(){
        AndroidNetworking.post(clientServices.ADVERTS_ACCEPT_POSTULATION)
                .addBodyParameter("PostulationId",postulation.getPostulationId())
                .setTag(TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            if(response.getString("Code").equalsIgnoreCase("200")){
                                Toast toast = Toast.makeText(context,response.getString("Message"),Toast.LENGTH_SHORT);
                                toast.show();
                            }else{
                                Toast toast = Toast.makeText(context,response.getString("Code") + response.getString("Message"),Toast.LENGTH_SHORT);
                                toast.show();
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
