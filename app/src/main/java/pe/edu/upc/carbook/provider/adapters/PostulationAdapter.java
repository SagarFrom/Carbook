package pe.edu.upc.carbook.provider.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.share.models.Advert;

/**
 * Created by Miguel on 18/11/2016.
 */

public class PostulationAdapter extends RecyclerView.Adapter<PostulationAdapter.ViewHolder> {
    private List<Advert> postulations;

    public void setPostulations(List<Advert> postulations) {
        this.postulations = postulations;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView availableAdvertCardView;
        public TextView clientNameTextView;
        public TextView carInfoTextView;
        public TextView descriptionTextView;
        public TextView statusTextView;
        public ImageView pictureImageView;

        public ViewHolder(View v) {
            super(v);
            availableAdvertCardView = (CardView) v.findViewById(R.id.availableAdvertCardView);
            clientNameTextView = (TextView) v.findViewById(R.id.clientNameTextView);
            carInfoTextView = (TextView) v.findViewById(R.id.carInfoTextView);
            descriptionTextView = (TextView) v.findViewById(R.id.descriptionTextView);
            statusTextView = (TextView) v.findViewById(R.id.statusTextView);
            pictureImageView = (ImageView) v.findViewById(R.id.pictureImageView);
        }
    }

    public PostulationAdapter() {
        postulations = new ArrayList<Advert>();
    }

    @Override
    public int getItemCount() {
        return this.postulations.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.provider_card_postulation, viewGroup, false);
        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        Advert item = this.postulations.get(i);

        viewHolder.clientNameTextView.setText(item.getClientName());
        viewHolder.carInfoTextView.setText(item.getCarInfo());
        viewHolder.descriptionTextView.setText(item.getDescription());

        if(item.getFirstPhotoUrl() != ""){
            Glide.with(viewHolder.itemView.getContext())
                    .load(item.getFirstPhotoUrl())
                    .centerCrop()
                    .into(viewHolder.pictureImageView);
        }

        switch (item.getPostulationStatus()){
            case "ESPERA":
                viewHolder.statusTextView.setTextColor(0xFFF1C500);
                viewHolder.statusTextView.setText("EN ESPERA");
                break;
            case "ACEPTADO":
                viewHolder.statusTextView.setTextColor(0xFF78CD51);
                viewHolder.statusTextView.setText("ACEPTADO");
                break;
            case "TERMINADO":
                viewHolder.statusTextView.setTextColor(0xFFEC6459);
                viewHolder.statusTextView.setText("TERMINADO");
                break;
        }

        viewHolder.availableAdvertCardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });

    }
}
