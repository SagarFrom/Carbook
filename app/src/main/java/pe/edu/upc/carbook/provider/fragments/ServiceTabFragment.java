package pe.edu.upc.carbook.provider.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.provider.adapters.ServiceGridAdapter;
import pe.edu.upc.carbook.share.models.Service;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceTabFragment extends Fragment {

    private RecyclerView recycler;
    private GridLayoutManager layoutManager;
    private ServiceGridAdapter adapter;

    public ServiceTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.provider_fragment_recycler, container, false);

        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recycler.setLayoutManager(layoutManager);

        adapter = new ServiceGridAdapter(Service.services);

        recycler.setAdapter(adapter);
        return view;
    }

}
