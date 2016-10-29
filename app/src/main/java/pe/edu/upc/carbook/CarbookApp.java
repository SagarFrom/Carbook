package pe.edu.upc.carbook;

import com.androidnetworking.AndroidNetworking;
import com.orm.SugarApp;

/**
 * Created by Juanxps on 10/24/16.
 */

public class CarbookApp extends SugarApp {
    private static CarbookApp instance;

    public CarbookApp(){
        super();
        instance = this;
    }

    public static CarbookApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
    }
}
