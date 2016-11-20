package pe.edu.upc.carbook.provider.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pe.edu.upc.carbook.R;

public class PostulateActivity extends AppCompatActivity {
    Button btnPostulateSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_activity_postulate);

        btnPostulateSave = (Button)findViewById(R.id.btnPostulateSave);
        btnPostulateSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
