package pe.edu.upc.carbook.client.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.client.activities.AdvertDetailActivity;
import pe.edu.upc.carbook.share.models.Advert;

/**
 * Created by usuario on 6/11/2016.
 */

public class AdvertAdapter extends RecyclerView.Adapter<AdvertAdapter.ViewHolder>{
    private List<Advert> adverts;
    public void setAdverts(List<Advert> adverts){ this.adverts = adverts; }

    @Override
    public AdvertAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_advert_client,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdvertAdapter.ViewHolder holder, final int position) {
        holder.fechaCreacionTextView.setText(adverts.get(position).getCreationDate());
        holder.descripcionTextView.setText(adverts.get(position).getDescription());
        holder.cantidadPostulantesTextView.setText(adverts.get(position).getCantApplications());
        Glide.with(holder.itemView.getContext())
                .load(adverts.get(position).getFirstPhotoUrl())
                .centerCrop()
                .into(holder.firstPhotoImageView);
        holder.advertClientCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itemItent  = new Intent(v.getContext(), AdvertDetailActivity.class);
                Bundle bundle = adverts.get(position).toBundle();
                itemItent.putExtras(bundle);
                v.getContext().startActivity(itemItent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return adverts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView advertClientCardView;
        TextView fechaCreacionTextView, descripcionTextView, cantidadPostulantesTextView;
        ImageView firstPhotoImageView;
        //advertClientCardView
        public ViewHolder(View itemView) {
            super(itemView);
            advertClientCardView = (CardView) itemView.findViewById(R.id.advertClientCardView);
            fechaCreacionTextView = (TextView) itemView.findViewById(R.id.fechaCreacionTextView);
            descripcionTextView = (TextView) itemView.findViewById(R.id.descriptionTextView);
            cantidadPostulantesTextView = (TextView) itemView.findViewById(R.id.cantidadPostulantesTextView);
            firstPhotoImageView = (ImageView) itemView.findViewById(R.id.firstPhotoImageView);
        }
    }
}
