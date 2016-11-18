package pe.edu.upc.carbook.provider.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.provider.activities.LocalDetailActivity;
import pe.edu.upc.carbook.provider.activities.LocalManageActivity;
import pe.edu.upc.carbook.share.models.Local;

/**
 * Created by Miguel on 06/11/2016.
 */

public class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.ViewHolder> {

    private List<Local> locals;

    public void setLocals(List<Local> locals) {
        this.locals = locals;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView localCardView;
        public TextView localNameTextView;
        public TextView addressTextView;
        public TextView capacityTextView;
        public ImageButton editLocalImageButton;

        public ViewHolder(View v) {
            super(v);
            localCardView = (CardView) v.findViewById(R.id.localCardView);
            localNameTextView = (TextView) v.findViewById(R.id.localNameTextView);
            addressTextView = (TextView) v.findViewById(R.id.addressTextView);
            capacityTextView = (TextView) v.findViewById(R.id.capacityTextView);
            editLocalImageButton = (ImageButton) v.findViewById(R.id.editLocalImageButton);
        }
    }

    public LocalAdapter() {
        locals = new ArrayList<Local>();
    }

    @Override
    public int getItemCount() {
        return this.locals.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.provider_card_local, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        Local item = this.locals.get(i);

        viewHolder.localNameTextView.setText(item.getName());
        viewHolder.addressTextView.setText(item.getAddress());
        viewHolder.capacityTextView.setText(String.valueOf(item.getCapacity()));

        viewHolder.localCardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent localIntent = new Intent(v.getContext(), LocalDetailActivity.class);
                Bundle bundle = locals.get(i).toBundle();
                localIntent.putExtras(bundle);
                v.getContext().startActivity(localIntent);
            }
        });

        viewHolder.editLocalImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LocalManageActivity.class);
                Bundle bundle = locals.get(i).toBundle();
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);


            }
        });

    }
}