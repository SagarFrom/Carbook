package pe.edu.upc.carbook.share.activities;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.client.fragments.AdvertsFragment;
import pe.edu.upc.carbook.client.fragments.ClientFragment;
import pe.edu.upc.carbook.provider.fragments.AvailableAdvertFragment;
import pe.edu.upc.carbook.provider.fragments.PostulationFragment;
import pe.edu.upc.carbook.provider.fragments.ProfileFragment;
import pe.edu.upc.carbook.provider.fragments.ServiceFragment;
import pe.edu.upc.carbook.share.helpers.SharedPreferencesManager;
import pe.edu.upc.carbook.share.models.User;

public class NavigationActivity extends BaseActivity {
    private DrawerLayout drawerLayout;
    SharedPreferencesManager spm;
    User userSession;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shared_activity_navigation);
        spm = new SharedPreferencesManager(NavigationActivity.this);
        userSession = spm.getUserOnPreferences();


        invalidateOptionsMenu();

        Context context = getApplicationContext();
        String name = "";
        switch (userSession.getRole()){
            case "PRO":
                name = userSession.getBusinessName();
                break;
            case "CLI":
                name = userSession.getName();
                break;
        }

        Toast toast = Toast.makeText(context, "Bienvenido " + name, Toast.LENGTH_SHORT);
        toast.show();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            prepareDrawer(navigationView);
            // Seleccionar item por defecto
            selectItem(navigationView.getMenu().getItem(0));
        }

        addToolbar();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    private void addToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.drawer_toggle);
            ab.setDisplayHomeAsUpEnabled(true);
        }
//        toolbar.getMenu().clear();

    }

    private void prepareDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        selectItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

    }

    private void selectItem(MenuItem itemDrawer) {
        Fragment genericFragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {
            case R.id.nav_item_profile:
                switch (userSession.getRole()){
                    case "PRO":
                        genericFragment = new ProfileFragment();
                        break;
                    case "CLI":
                        genericFragment = new ClientFragment();
                        break;
                }
                break;
            case R.id.nav_item_adverts:
                // Fragmento para la sección Cuenta
                genericFragment = new AdvertsFragment();
                break;
            case R.id.nav_item_av_adverts:
                genericFragment = new AvailableAdvertFragment();
                break;
            case R.id.nav_item_postulations:
                genericFragment = new PostulationFragment();
                break;
            case R.id.nav_item_services:
                switch (userSession.getRole()){
                    case "PRO":
                        genericFragment = new ServiceFragment();
                        break;
                    case "CLI":
                        //POR HACER: INICIALIZACION DEL SERVICIOS DEL CLIENTE
                        break;
                }
                break;
        }
        if (genericFragment != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, genericFragment)
                    .commit();
        }

        // Setear título actual
        setTitle(itemDrawer.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.shared_menu_drawer, menu);
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        switch (userSession.getRole()){
            case "PRO":
                nav_Menu.findItem(R.id.nav_item_adverts).setVisible(false);
                break;
            case "CLI":
                nav_Menu.findItem(R.id.nav_item_postulations).setVisible(false);
                nav_Menu.findItem(R.id.nav_item_av_adverts).setVisible(false);
                break;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

