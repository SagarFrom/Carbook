package pe.edu.upc.carbook.share.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by hypnotic on 4/11/2016.
 */

public class BaseActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;


    public void ChangeScreem(Class toActivity,Boolean delete)
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
    public void setUserId(int name) {
        // Stores given name in Preferences
        if(sharedPreferences != null) {
            // Opens Preferences file for edition
            SharedPreferences.Editor e = sharedPreferences.edit();
            e.putInt("USR_ID", name);
            e.commit();
        }
    }
    public int getUserId() {
        // Obtains stored name if present,
        // otherwise returns an empty string
        if(sharedPreferences == null) {
            sharedPreferences = getSharedPreferences(
                    "USR_ID", MODE_PRIVATE);
        }
        return sharedPreferences.getInt("USR_ID",0);
    }
    public void setUserName(String name) {
        // Stores given name in Preferences
        if(sharedPreferences != null) {
            // Opens Preferences file for edition
            SharedPreferences.Editor e = sharedPreferences.edit();
            e.putString("USR_NAME", name);
            e.commit();
        }
    }
    public String getUserName() {
        // Obtains stored name if present,
        // otherwise returns an empty string
        if(sharedPreferences == null) {
            sharedPreferences = getSharedPreferences(
                    "USR_NAME", MODE_PRIVATE);
        }
        return sharedPreferences.getString("USR_NAME", "");
    }
    public void setUserRole(String role) {
        // Stores given name in Preferences
        if(sharedPreferences != null) {
            // Opens Preferences file for edition
            SharedPreferences.Editor e = sharedPreferences.edit();
            e.putString("USR_ROLE", role);
            e.commit();
        }
    }
    public String getUserRole() {
        // Obtains stored name if present,
        // otherwise returns an empty string
        if(sharedPreferences == null) {
            sharedPreferences = getSharedPreferences(
                    "USR_ROLE", MODE_PRIVATE);
        }
        return sharedPreferences.getString("USR_ROLE", "");
    }
}
