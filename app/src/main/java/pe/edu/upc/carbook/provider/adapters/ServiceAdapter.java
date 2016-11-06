package pe.edu.upc.carbook.provider.adapters;

/**
 * Created by Miguel on 05/11/2016.
 */


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.share.models.Service;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView clientNameTextView;
        public TextView carInfoTextView;
        public TextView descriptionTextView;
        public TextView costTextView;
        public ImageView pictureImageView;

        public ViewHolder(View v) {
            super(v);
            clientNameTextView = (TextView) v.findViewById(R.id.clientNameTextView);
            carInfoTextView = (TextView) v.findViewById(R.id.carInfoTextView);
            descriptionTextView = (TextView) v.findViewById(R.id.descriptionTextView);
            costTextView = (TextView) v.findViewById(R.id.costTextView);
            pictureImageView = (ImageView) v.findViewById(R.id.pictureImageView);
        }
    }

    public ServiceAdapter() {
    }

    @Override
    public int getItemCount() {
        return Service.services.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.provider_card_service, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Service item = Service.services.get(i);

        Glide.with(viewHolder.itemView.getContext())
                .load(item.getFirstImageUrl())
                .centerCrop()
                .into(viewHolder.pictureImageView);
        viewHolder.carInfoTextView.setText(item.getClientCar());
        viewHolder.clientNameTextView.setText(item.getClientName());
        viewHolder.descriptionTextView.setText(item.getDescription());
        viewHolder.costTextView.setText(item.getTotal().toString());
    }
}
