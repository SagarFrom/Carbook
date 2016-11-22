package pe.edu.upc.carbook.client.activities;

import android.app.DatePickerDialog;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;
import java.text.SimpleDateFormat;
import pe.edu.upc.carbook.R;

public class AdvertCreateActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText toDateEditText;
    private EditText descriptionEditText;

    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;
    final Context context = this;

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

        findViewsById();
        setDateTimeField();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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

}
