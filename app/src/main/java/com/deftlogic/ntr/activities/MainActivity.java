package com.deftlogic.ntr.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.deftlogic.ntr.R;
import com.deftlogic.ntr.backgroundTasks.GetSessionIdAsyncTask;
import com.deftlogic.ntr.data.FieldAssistDbHelper;
import com.deftlogic.ntr.fragments.CartFragment;
import com.deftlogic.ntr.fragments.FavoritesFragment;
import com.deftlogic.ntr.fragments.HomeFragment;
import com.deftlogic.ntr.fragments.SearchFragment;
import com.deftlogic.ntr.fragments.SettingsFragment;
import com.deftlogic.ntr.signin.SignIn;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.loopj.android.http.AsyncHttpClient;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FieldAssistDbHelper fieldAssistDbHelper;
    SQLiteDatabase db;
    private boolean sessionStatus = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSessionId();

        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
//        SignIn signIn = new SignIn(MainActivity.this);
//        signIn.checkSignIn();
        fieldAssistDbHelper = new FieldAssistDbHelper(this);
        db = fieldAssistDbHelper.getReadableDatabase();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        if (savedInstanceState == null) {
            // on first time display view for first nav item
            Fragment fragment = null;
            fragment = new HomeFragment();
            if (fragment != null) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();
            } else {
                // error in creating fragment
                Log.e("MainActivity", "Error in creating fragment");
            }
        }



    }

    private void getSessionId() {
        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("uSession", Context.MODE_PRIVATE);
        sessionStatus = sharedPreferences.getBoolean("status",false);
        if(!sessionStatus){
            new GetSessionIdAsyncTask(MainActivity.this).execute();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_home) {
           fragment = new HomeFragment();
        } else if (id == R.id.nav_search) {
            fragment = new SearchFragment();

        } else if (id == R.id.nav_favorites) {
            fragment = new FavoritesFragment();

        } else if (id == R.id.nav_cart) {
            fragment = new CartFragment();

        } else if (id == R.id.nav_settings) {
            fragment = new SettingsFragment();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        if (fragment != null) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, fragment).addToBackStack("Home").commit();
//            // update selected item and title, then close the drawer
//            mDrawerList.setItemChecked(position, true);
//            mDrawerList.setSelection(position);

            setTitle(item.getTitle());
            drawer.closeDrawer(GravityCompat.START);;
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
        return true;
    }
}
