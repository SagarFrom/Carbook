package pe.edu.upc.carbook.provider.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.provider.activities.AdvertDetailActivity;
import pe.edu.upc.carbook.share.models.Advert;

/**
 * Created by Miguel on 18/11/2016.
 */

public class AvailableAdvertAdapter extends RecyclerView.Adapter<AvailableAdvertAdapter.ViewHolder> {
    private List<Advert> adverts;

    public void setAdverts(List<Advert> adverts) {
        this.adverts = adverts;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView availableAdvertCardView;
        public TextView clientNameTextView;
        public TextView carInfoTextView;
        public TextView descriptionTextView;
        public TextView beginDateTextView;
        public TextView endDateTextView;
        public ImageView pictureImageView;

        public ViewHolder(View v) {
            super(v);
            availableAdvertCardView = (CardView) v.findViewById(R.id.availableAdvertCardView);
            clientNameTextView = (TextView) v.findViewById(R.id.clientNameTextView);
            carInfoTextView = (TextView) v.findViewById(R.id.carInfoTextView);
            descriptionTextView = (TextView) v.findViewById(R.id.descriptionTextView);
            beginDateTextView = (TextView) v.findViewById(R.id.beginDateTextView);
            endDateTextView = (TextView) v.findViewById(R.id.endDateTextView);
            pictureImageView = (ImageView) v.findViewById(R.id.pictureImageView);
        }
    }

    public AvailableAdvertAdapter() {
        adverts = new ArrayList<Advert>();
    }

    @Override
    public int getItemCount() {
        return this.adverts.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.provider_card_available_advert, viewGroup, false);
        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        Advert item = this.adverts.get(i);

        viewHolder.clientNameTextView.setText(item.getClientName());
        viewHolder.carInfoTextView.setText(item.getCarInfo());
        viewHolder.descriptionTextView.setText(item.getDescription());
        viewHolder.beginDateTextView.setText(item.getCreationDate());
        viewHolder.endDateTextView.setText(item.getEndDate());

        if(item.getFirstPhotoUrl() != ""){
            Glide.with(viewHolder.itemView.getContext())
                    .load(item.getFirstPhotoUrl())
                    .centerCrop()
                    .into(viewHolder.pictureImageView);
        }

        viewHolder.availableAdvertCardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent itemItent  = new Intent(v.getContext(), AdvertDetailActivity.class);
                Bundle bundle = adverts.get(i).toBundle();
                itemItent.putExtras(bundle);
                v.getContext().startActivity(itemItent);
            }
        });

    }
}
