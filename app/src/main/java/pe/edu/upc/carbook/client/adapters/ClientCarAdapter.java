package pe.edu.upc.carbook.client.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.client.activities.CarDetailActivity;
import pe.edu.upc.carbook.client.activities.PostulationDetailActivity;
import pe.edu.upc.carbook.client.fragments.ClientFragment;
import pe.edu.upc.carbook.provider.activities.LocalDetailActivity;
import pe.edu.upc.carbook.provider.activities.LocalManageActivity;
import pe.edu.upc.carbook.provider.adapters.LocalAdapter;
import pe.edu.upc.carbook.share.models.Car;
import pe.edu.upc.carbook.share.models.Local;


/**
 * Created by hypnotic on 23/11/2016.
 */

public class ClientCarAdapter extends RecyclerView.Adapter<ClientCarAdapter.ViewHolder> {

    private List<Car> cars;

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView carCardView;
        public TextView carNameTextView;
        public TextView modelNameTextView;
        public ImageButton editLocalImageButton;

        public ViewHolder(View v) {
            super(v);
            carCardView = (CardView) v.findViewById(R.id.carCardView);
            carNameTextView = (TextView) v.findViewById(R.id.carNameTextView);
            modelNameTextView = (TextView) v.findViewById(R.id.modelNameTextView);
            editLocalImageButton = (ImageButton) v.findViewById(R.id.editLocalImageButton);
        }
    }

    public ClientCarAdapter() {
        cars = new ArrayList<Car>();
    }

    @Override
    public int getItemCount() {
        return this.cars.size();
    }

    @Override
    public ClientCarAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.provider_card_local, viewGroup, false);
        return new ClientCarAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ClientCarAdapter.ViewHolder viewHolder, final int i) {
        Car item = this.cars.get(i);

        viewHolder.carNameTextView.setText(item.getCarFullName());
        viewHolder.modelNameTextView.setText(item.getModel());

        viewHolder.carCardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent carIntent = new Intent(v.getContext(), CarDetailActivity.class);
                Bundle bundle = cars.get(i).toBundle();
                carIntent.putExtras(bundle);
                v.getContext().startActivity(carIntent);
            }
        });

        viewHolder.editLocalImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CarDetailActivity.class);
                Bundle bundle = cars.get(i).toBundle();
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);


            }
        });

    }
}
