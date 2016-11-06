package pe.edu.upc.carbook.provider.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.provider.adapters.LocalAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocalFragment extends Fragment {

    private LinearLayoutManager linearLayout;

    public LocalFragment() {
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

        RecyclerView recycler = (RecyclerView)view.findViewById(R.id.recycler);
        linearLayout = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(linearLayout);

        LocalAdapter adapter = new LocalAdapter();
        recycler.setAdapter(adapter);

        return view;
    }

}
