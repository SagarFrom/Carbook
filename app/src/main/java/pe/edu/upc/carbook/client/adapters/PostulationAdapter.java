package pe.edu.upc.carbook.client.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pe.edu.upc.carbook.R;
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
    public void onBindViewHolder(PostulationAdapter.ViewHolder holder, int position) {
        
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
