package trivial.speckmussweg;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.jorgecastillo.FillableLoader;

import java.util.Objects;

import trivial.speckmussweg.database.MyDatabase;

public class Home extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    Boolean backPressedCheck = false;
    private ActionBarDrawerToggle drawerToggle;
    FillableLoader fillableLoader;
    MyDatabase database;
    Uri uriProfileImage;
    View headerview;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = findViewById(R.id.drawer_layout);
        NavigationView nvDrawer = findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        drawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);

        headerview = nvDrawer.getHeaderView(0);

        RelativeLayout header = headerview.findViewById(R.id.nav_header);


        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                Fragment fragment = null;
                Class fragmentClass;
                fragmentClass = Profile.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
            }
        });

        /*fillableLoader = (FillableLoader) findViewById(R.id.home_fillableLoader);
        fillableLoader.setSvgPath(SVGPath.PIG_NEW);
        fillableLoader.start();*/

        setProfile();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;

        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_configurator:
                fragmentClass = Configurator.class;
                break;
            case R.id.nav_training:
                fragmentClass = Training_main.class;
                break;
            case R.id.nav_shutdown:
                System.exit(1);
            default:
                fragmentClass = TestFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.flContent, fragment, "fragment");
        fragmentTransaction.commit();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    // Is called when onstart() is complete
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    /*//Backtaste gedrückt UND es wurde mind. ein Name eingeben, dann trotzdem speichern?
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (Profile.checkContent()) {
                *//*if (TextUtils.isEmpty(editTextName.getText().toString())) {
                    keypressCheck = false;
                    finish();
                    *//*
            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(this));
                dialog.setTitle("Speichern?");
                dialog.setMessage("Änderung abspeichern?");
                dialog.setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //saveData();
                    }
                });
                dialog.setNegativeButton("Verwerfen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Home.this, "Eintrag nicht hinzugefügt!",
                                Toast.LENGTH_SHORT).show();
                        hideKeyboard(Home.this);
                        threadAdding.start();
                    }
                });
                dialog.create();
                dialog.show();
                //Variable wird nach 1 ms wieder auf false gesetzt,
                // damit man mit der backtaste nach dem Dialog wieder zum Dialog kommt!
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        backPressedCheck = false;
                    }
                }, 1);
                return true;
            }
        } else {
            backPressedCheck = false;
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }*/

    public void setProfile() {
        database = new MyDatabase(this);
        Cursor cursor = database.selectProfile(1);
        if (cursor.getCount() >= 1) {
            TextView headerName = headerview.findViewById(R.id.nav_header_name);
            TextView headerWeight = headerview.findViewById(R.id.nav_header_weight);
            TextView headerHeight = headerview.findViewById(R.id.nav_header_height);
            ImageView headerPic = headerview.findViewById(R.id.nav_header_picture);
            String temp = cursor.getString(1) + " " + cursor.getString(2);
            headerName.setText(temp);
            temp = cursor.getString(6) + " kg";
            headerWeight.setText(temp);
            temp = cursor.getString(5) + " cm";
            headerHeight.setText(temp);
            uriProfileImage = Uri.parse(cursor.getString(7));
            Glide.with(this).load(uriProfileImage).crossFade().
                    diskCacheStrategy(DiskCacheStrategy.ALL).into(headerPic);
        } else {
            Fragment fragment = null;
            Class fragmentClass;
            fragmentClass = Profile.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                Toast.makeText(this,
                        "You have to create a Profile first, before you can start!",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Insert the fragment by replacing any existing fragment
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager)
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    Thread threadAdding = new Thread() {
        @Override
        public void run() {
            try {
                Thread.sleep(2000); //2000 ms wegen length-short, 3500 bei length-long
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onBackPressed() {
        Fragment fragment = fragmentManager.findFragmentById(R.id.flContent);
        if (fragment != null) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        } else {
            super.onBackPressed();
        }
    }
}



