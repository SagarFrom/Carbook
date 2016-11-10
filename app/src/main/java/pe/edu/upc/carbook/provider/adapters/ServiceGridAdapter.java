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
import java.util.List;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.share.models.Service;

public class ServiceGridAdapter extends RecyclerView.Adapter<ServiceGridAdapter.ViewHolder> {

    private List<Service> services;

    public void setServices(List<Service> services) {
        this.services = services;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView clientNameTextView;
        public TextView carInfoTextView;
        public ImageView pictureImageView;

        public ViewHolder(View v) {
            super(v);
            clientNameTextView = (TextView) v.findViewById(R.id.clientNameTextView);
            carInfoTextView = (TextView) v.findViewById(R.id.carInfoTextView);
            pictureImageView = (ImageView) v.findViewById(R.id.pictureImageView);
        }
    }

    public ServiceGridAdapter(List<Service> items) {
        this.services = items;
    }

    @Override
    public int getItemCount() {

        return services.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.provider_grid_item_service, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Service item = services.get(i);

        Glide.with(viewHolder.itemView.getContext())
                .load(item.getFirstPhotoUrl())
                .centerCrop()
                .into(viewHolder.pictureImageView);

        viewHolder.carInfoTextView.setText(item.getCustomerCar());
        viewHolder.clientNameTextView.setText(item.getCustomerName());

//        try{
//            URL url = new URL(item.getFirstImageUrl());
//            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            viewHolder.pictureImageView.setImageBitmap(bmp);
//            //more code goes here
//        }catch(MalformedURLException ex){
//        }catch (IOException ex){
//        }
    }
}
