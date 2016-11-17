package pe.edu.upc.carbook.share.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.upc.carbook.share.models.User;

/**
 * Created by hypnotic on 4/11/2016.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    public void ChangeScreem(Class toActivity, Boolean delete)
    {
        Intent intent = new Intent(this,toActivity);
        startActivity(intent);
        if(delete)
            finish();

    }
    //ssss
    public void ChangeScreem(Class toActivity,Boolean delete,Integer fromAnimation,Integer toAnimation)
    {
        Intent intent = new Intent(this,toActivity);
        startActivity(intent);
        overridePendingTransition(fromAnimation,toAnimation );
        if(delete)
            finish();
    }

}
