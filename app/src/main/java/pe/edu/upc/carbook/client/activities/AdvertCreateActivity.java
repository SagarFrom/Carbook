package pe.edu.upc.carbook.client.activities;

import android.app.DatePickerDialog;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.text.SimpleDateFormat;
import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.client.services.clientServices;
import pe.edu.upc.carbook.share.helpers.SharedPreferencesManager;
import pe.edu.upc.carbook.share.models.Car;
import pe.edu.upc.carbook.share.models.User;

public class AdvertCreateActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText toDateEditText;
    private EditText descriptionEditText;
    private Spinner carInfoSpinner;

    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;
    final Context context = this;
    private static String TAG = "Carbook";
    private SharedPreferencesManager spm;
    private User userSession;

    private List<Car> carsOfClient = new ArrayList<>();
    private List<String> nameFullCars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_activity_advert_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy",Locale.US);
        spm = new SharedPreferencesManager(context);
        userSession = spm.getUserOnPreferences();
        findViewsById();
        setDateTimeField();
        updateDataCars();

        carInfoSpinner = (Spinner) findViewById(R.id.carInfoSpinner);

        carInfoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void inicializarAdapter(){
        // DATA para el Spinner
        int carsCount = carsOfClient.size();
        Log.d(TAG,"Cantidad: " + carsCount);
        nameFullCars = new ArrayList<String>();
        for(int i = 0; i < carsCount; i++){
            nameFullCars.add(carsOfClient.get(i).getCarFullName());
        }
        //CREAR ADAPTER ESPECIAL PARA EL SPINNER
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,nameFullCars);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        carInfoSpinner.setAdapter(dataAdapter);
    }

    private void findViewsById() {
        toDateEditText = (EditText) findViewById(R.id.toDateEditText);
        toDateEditText.setInputType(InputType.TYPE_NULL);
        toDateEditText.requestFocus();
    }

    private void setDateTimeField(){
        toDateEditText.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                toDateEditText.setText(dateFormatter.format(newDate.getTime()));
                //Aca cambiar la data del Año
                Toast toast = Toast.makeText(context,"Fecha Fin: "+ newDate.getTime().toString() ,Toast.LENGTH_SHORT);
                toast.show();
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View view){
        if(view == toDateEditText){
            toDatePickerDialog.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDataCars();
    }

    private void updateDataCars(){
        String url = clientServices.CLIENT_CAR_SOURCES.replace("{1}",userSession.getUserId().toString());
        AndroidNetworking
                .get(url)
                .setPriority(Priority.MEDIUM)
                .setTag(TAG)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("Code").equalsIgnoreCase("200")) {
                                carsOfClient = Car.buildFromJSONArray(response.getJSONArray("Result"));
                                inicializarAdapter();
                            } else {
                                Log.d(TAG, "Response Error: " + response.getString("Message"));
                                Log.d(TAG, "Response Error: " + response.getString("ExceptionMessage"));
                            }
                        } catch (JSONException e){
                            Log.d(TAG,"Error: " + e.getLocalizedMessage());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, "onError " + anError.getErrorBody());
                    }
                });

    }

}
