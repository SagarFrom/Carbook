package pe.edu.upc.carbook.client.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;

import java.util.List;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.share.models.GalleryAdvert;

/**
 * Created by usuario on 20/11/2016.
 */

public class AdvertDetailGalleryAdapter extends RecyclerView.Adapter<AdvertDetailGalleryAdapter.ViewHolder> {
    private List<GalleryAdvert> galleryAdverts;

    public void setGalleryAdverts(List<GalleryAdvert> galleryAdverts) {
        this.galleryAdverts = galleryAdverts;
    }

    @Override
    public AdvertDetailGalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.client_card_gallery_photos_advert,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdvertDetailGalleryAdapter.ViewHolder holder, int position) {
        holder.nameAdvertGalleryTextView.setText(galleryAdverts.get(position).getName());
        holder.statusTextView.setText(galleryAdverts.get(position).getStatus());
        holder.pictureGalleryAdvertANImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        holder.pictureGalleryAdvertANImageView.setErrorImageResId(R.mipmap.ic_launcher);
        holder.pictureGalleryAdvertANImageView.setImageUrl(galleryAdverts.get(position).getImageUrl());
    }

    @Override
    public int getItemCount() {
        return galleryAdverts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView galleryPhotoAdvertCardView;
        ANImageView pictureGalleryAdvertANImageView;
        TextView nameAdvertGalleryTextView;
        TextView statusTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            galleryPhotoAdvertCardView = (CardView) itemView.findViewById(R.id.galleryPhotoAdvertCardView);
            pictureGalleryAdvertANImageView = (ANImageView) itemView.findViewById(R.id.pictureGalleryAdvertANImageView);
            nameAdvertGalleryTextView = (TextView) itemView.findViewById(R.id.nameAdvertGalleryTextView);
            statusTextView = (TextView) itemView.findViewById(R.id.statusTextView);
        }
    }
}
