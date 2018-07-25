package trivial.speckmussweg;

import android.content.Context;
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.jorgecastillo.FillableLoader;
import com.github.jorgecastillo.State;
import com.github.jorgecastillo.listener.OnStateChangeListener;

import de.hdodenhof.circleimageview.CircleImageView;
import trivial.speckmussweg.database.MyDatabase;
import trivial.speckmussweg.database.SVGPath;

public class Home extends AppCompatActivity implements OnStateChangeListener {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    FillableLoader fillableLoader;
    static MyDatabase database;
    Uri uriProfileImage;
    View headerview;
    static FragmentTransaction fragmentTransaction;
    static FragmentManager fragmentManager;
    MenuItem profileMenuItem;
    public static boolean booleanSwitchPossible = true;
    TextView headerText;
    ImageView spaconIcon;
    public static int kcalSum = 0;

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
        fillableLoader = findViewById(R.id.home_fillableLoader);
        headerText = findViewById(R.id.spacon_textview);
        spaconIcon = findViewById(R.id.spacon_imageview);
        fillableLoader.setSvgPath(SVGPath.NEW_FAT_PIG);
        animateViews(1000);
        setTitle("Spacon");

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleanSwitchPossible) {
                    fillableLoader.setVisibility(View.GONE);
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
                    setTitle("Profile");
                    setProfile();
                    if (profileMenuItem != null) {
                        profileMenuItem.setChecked(false);
                    }
                }
            }
        });


        setProfile();
    }

    //method to animate the views on startscreen
    private void animateViews(int duration) {
        if (headerText.getVisibility() == View.VISIBLE) {
            headerText.setVisibility(View.GONE);
        }
        if (spaconIcon.getVisibility() == View.VISIBLE) {
            spaconIcon.setVisibility(View.GONE);
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 1 second
                headerText.startAnimation(AnimationUtils.loadAnimation(Home.this, R.anim.view_fallingfromsky));
                headerText.setVisibility(View.VISIBLE);

                spaconIcon.startAnimation(AnimationUtils.loadAnimation(Home.this, R.anim.view_fadein_long));
                spaconIcon.setVisibility(View.VISIBLE);

                if (fillableLoader.getVisibility() == View.GONE) {
                    fillableLoader.setVisibility(View.VISIBLE);
                }
                fillableLoader.start();
            }
        }, duration);


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

    @Override
    public void onStateChange(int state) {
        switch (state) {
            case State.FILL_STARTED:

            case State.FINISHED:

        }
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
        if (booleanSwitchPossible) {
            // Create a new fragment and specify the fragment to show based on nav item clicked
            Fragment fragment = null;

            Class fragmentClass;
            switch (menuItem.getItemId()) {
                case R.id.nav_configurator:
                    fragmentClass = Configurator.class;
                    break;
                case R.id.nav_training:
                    fragmentClass = Training.class;
                    break;
                case R.id.nav_aboutus:
                    fragmentClass = AboutUs.class;
                    break;
                case R.id.nav_credits:
                    fragmentClass = Credits.class;
                    break;
                case R.id.nav_shutdown:
                    System.exit(1);
                default:
                    fragmentClass = Home.class;
                    break;
            }
            fillableLoader.setVisibility(View.GONE);
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

            setProfile();
            // Highlight the selected item has been done by NavigationView
            menuItem.setChecked(true);
            profileMenuItem = menuItem;
            // Set action bar title
            setTitle(menuItem.getTitle());
            // Close the navigation drawer
            mDrawer.closeDrawers();
        } else {
            switch (menuItem.getItemId()) {
                case R.id.nav_shutdown:
                    System.exit(1);
            }
            Toast.makeText(this, "Please fill in all Fields with '*'",
                    Toast.LENGTH_LONG).show();
        }

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

    public void setProfile() {
        database = new MyDatabase(this);
        Cursor cursor = database.selectProfile(1);
        if (cursor.getCount() >= 1) {
            TextView headerName = headerview.findViewById(R.id.nav_header_name);
            TextView headerWeight = headerview.findViewById(R.id.nav_header_weight);
            TextView headerHeight = headerview.findViewById(R.id.nav_header_height);
            CircleImageView headerPic = headerview.findViewById(R.id.nav_header_picture);
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
            booleanSwitchPossible = false;
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

    //if true, then user can leave profile at start
    public static void setBooleanSwitchPossible() {
        booleanSwitchPossible = true;
    }

    @Override
    public void onBackPressed() {
        if (booleanSwitchPossible) {
            try {
                Fragment fragment = fragmentManager.findFragmentById(R.id.flContent);
                if (fragment != null) {
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.remove(fragment);
                    fragmentTransaction.commit();
                    setTitle("Spacon");
                    animateViews(100);
                    setProfile();
                } else {
                    super.onBackPressed();
                }
            } catch (NullPointerException ignored) {
            }
        } else {
            Toast.makeText(this, "Please fill in all Fields with '*'",
                    Toast.LENGTH_LONG).show();
        }

    }

    //get all meals from database for training
    public static void getKcalFromDatabaseForTraining(Context cxt) {
        int kcal = 0;
        database = new MyDatabase(cxt);
        Cursor cursor = null;
        for (int i = 1; i <= 4; i++) {
            cursor = database.selectBread(i);
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        kcal += cursor.getInt(2);
                    } while (cursor.moveToNext());
                }
            }
        }

        for (int i = 1; i <= 4; i++) {
            cursor = database.selectCheese(i);
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        kcal += cursor.getInt(2);
                    } while (cursor.moveToNext());
                }
            }
        }

        for (int i = 1; i <= 4; i++) {
            cursor = database.selectMeat(i);
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        kcal += cursor.getInt(2);
                    } while (cursor.moveToNext());
                }
            }
        }

        for (int i = 1; i <= 4; i++) {
            cursor = database.selectSalad(i);
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        kcal += cursor.getInt(2);
                    } while (cursor.moveToNext());
                }
            }
        }

        for (int i = 1; i <= 4; i++) {
            cursor = database.selectExtras(i);
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        kcal += cursor.getInt(2);
                    } while (cursor.moveToNext());
                }
            }
        }

        for (int i = 1; i <= 4; i++) {
            cursor = database.selectSauce(i);
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        kcal += cursor.getInt(2);
                    } while (cursor.moveToNext());
                }
            }
        }
        cursor.close();
        database.close();
        kcalSum = kcal;
    }

}



