package pe.edu.upc.carbook.client.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.share.models.Advert;


public class AdvertDetailFragment extends Fragment {

    private Advert advert;
    ANImageView firstPhotoANImageView;
    TextView nameCarModelTextView, descriptionTextView, fechaCreacionTextView,
            fechaFinTextView, cantidadPostulantesTextView;
    Button verGaleriaButton, verPostulacionesButton;

    public AdvertDetailFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.client_fragment_advert_detail, container, false);
        firstPhotoANImageView = (ANImageView)view.findViewById(R.id.firstPhotoANImageView);
        nameCarModelTextView = (TextView) view.findViewById(R.id.nameCarModelTextView);
        descriptionTextView = (TextView) view.findViewById(R.id.descriptionTextView);
        fechaCreacionTextView = (TextView) view.findViewById(R.id.fechaCreacionTextView);
        fechaFinTextView = (TextView) view.findViewById(R.id.fechaFinTextView);
        cantidadPostulantesTextView = (TextView) view.findViewById(R.id.cantidadPostulantesTextView);
        advert = Advert.buildFromBundle(getArguments());
        firstPhotoANImageView.setImageUrl(advert.getFirstPhotoUrl());
        firstPhotoANImageView.setErrorImageResId(R.mipmap.ic_launcher);
        firstPhotoANImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        nameCarModelTextView.setText(advert.getCarId()); // POR AHORA PRIMERO QUIERO QUE VER SI FUNCIONA EL INTENT
        descriptionTextView.setText(advert.getDescription());
        fechaCreacionTextView.setText(advert.getCreationDate());
        fechaFinTextView.setText(advert.getEndDate());
        cantidadPostulantesTextView.setText(advert.getCantApplications());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
