package pe.edu.upc.carbook.client.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;

import java.util.List;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.client.activities.PostulationDetailActivity;
import pe.edu.upc.carbook.client.activities.PostulationsActivity;
import pe.edu.upc.carbook.share.models.Postulation;

/**
 * Created by usuario on 21/11/2016.
 */

public class PostulationAdapter extends RecyclerView.Adapter<PostulationAdapter.ViewHolder>{

    private List<Postulation> postulations;

    public void setPostulations(List<Postulation> postulations){
        this.postulations = postulations;
    }

    @Override
    public PostulationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.client_card_postulation,parent,false);
        PostulationAdapter.ViewHolder viewHolder = new PostulationAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PostulationAdapter.ViewHolder holder, final int position) {
        String urlImageDefault = "http://www.bayaderavirtual.info/imagenes/BOSS%20LOGO.jpg";
        holder.providerNameTextView.setText(postulations.get(position).getProviderName());
        holder.quotationTextView.setText(postulations.get(position).getQuotationWithSoles());
        holder.logoProviderANImageView.setDefaultImageResId(R.drawable.default_image);
        holder.logoProviderANImageView.setErrorImageResId(R.mipmap.ic_launcher);
        holder.logoProviderANImageView.setImageUrl(urlImageDefault);
        holder.providerRankRatingBar.setRating(Float.parseFloat(postulations.get(position).getProviderRank()));

        holder.postulationCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itemItent  = new Intent(v.getContext(), PostulationDetailActivity.class);
                Bundle bundle = postulations.get(position).toBundle();
                itemItent.putExtras(bundle);
                v.getContext().startActivity(itemItent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postulations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView postulationCardView;
        ANImageView logoProviderANImageView;
        TextView providerNameTextView,quotationTextView;
        RatingBar providerRankRatingBar;
        public ViewHolder(View itemView) {
            super(itemView);
            postulationCardView = (CardView) itemView.findViewById(R.id.postulationCardView);
            logoProviderANImageView = (ANImageView) itemView.findViewById(R.id.logoProviderANImageView);
            providerNameTextView = (TextView) itemView.findViewById(R.id.providerNameTextView);
            quotationTextView = (TextView) itemView.findViewById(R.id.quotationTextView);
            providerRankRatingBar = (RatingBar) itemView.findViewById(R.id.providerRankRatingBar);
        }
    }
}
