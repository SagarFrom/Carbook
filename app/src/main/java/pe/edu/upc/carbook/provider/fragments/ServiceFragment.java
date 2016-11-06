package pe.edu.upc.carbook.provider.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.provider.adapters.ServiceAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends Fragment {
    private RecyclerView reclycler;
    private LinearLayoutManager layoutManager;
    private ServiceAdapter serviceAdapter;

    public ServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.provider_fragment_service, container, false);
        reclycler = (RecyclerView) view.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(getActivity());
        reclycler.setLayoutManager(layoutManager);

        serviceAdapter = new ServiceAdapter();
        reclycler.setAdapter(serviceAdapter);
        return view;
    }

}
