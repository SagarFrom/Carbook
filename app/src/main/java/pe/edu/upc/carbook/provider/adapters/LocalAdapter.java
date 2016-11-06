package pe.edu.upc.carbook.provider.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.share.models.Local;

/**
 * Created by Miguel on 06/11/2016.
 */

public class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView localNameTextView;
        public TextView addressTextView;
        public TextView capacityTextView;

        public ViewHolder(View v) {
            super(v);
            localNameTextView = (TextView) v.findViewById(R.id.localNameTextView);
            addressTextView = (TextView) v.findViewById(R.id.addressTextView);
            capacityTextView = (TextView) v.findViewById(R.id.capacityTextView);
        }
    }

    public LocalAdapter() {
    }

    @Override
    public int getItemCount() {
        return Local.locals.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.provider_card_local, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Local item = Local.locals.get(i);

        viewHolder.localNameTextView.setText(item.getName());
        viewHolder.addressTextView.setText(item.getAddress());
        viewHolder.capacityTextView.setText(String.valueOf(item.getCapacity()));

    }
}