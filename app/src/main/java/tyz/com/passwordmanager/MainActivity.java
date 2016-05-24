package tyz.com.passwordmanager;

/**
 * T1 is proof of concept for a side-bar managing an application
 * Author DMK
 * Working with Android User Interface Design Chapter 6
 */
/*
The view page which holds the current activity is primarily set up
in setupTabs method. This works if using the nav drawer but it
may be necessary to load a fragment in there directly through other
means such as the settings menu.

 */
    /*
    To add another tab item edit TabPagerAdapter:
    Create a Screen{...} Fragment object based on ScreenDatePhase or ScreenHelp fragments
        Set the new name by refactoring
    Create a fragment{...}.xml to load in its onCreateView
    Add a case to this getItem
    Add a string for the Tab enum and a tab enum value
     */

/*
To add another nav drawer menu item edit menu/nav_drawer.xml
    add an item to the array with an offset id and title
    add string for mX
    add mX to string array

 */
/*
To add an item to the overflow/settings menu
    add an item to the settings.xml file
    add a second TabPagerAdapter (make sure to inherit from TabStatePagerAdapter)


    add a case to onOptionsItemSelected
 */

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ScreenPasswordDecodeFragment.OnMasterPasswordSetListener {
    private static final String TAG = "MainActivity";

    //Used to set and restore state:
    private static final String SELECTED_POSITION = "selectedPosition";

    private static final String SELECTED_TITLE = "selectedTitle";


    @Override
    public void onMasterPasswordSet(int position) {
        Toast.makeText(this, "PasswordSet", Toast.LENGTH_LONG).show();
    }

    private int mCurrentNavPosition;
    private String [] mDetailTitles;
    // Drawer layout in this app is an XML wrapper in our main application
    //  It contains as an element the NavigationView which will be the
    //  pull-out drawer.
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private Toolbar mToolbar; // Bar across top of app with burger title.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //http://developer.android.com/training/appbar/setting-up.html
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDetailTitles = getResources().getStringArray(R.array.screen_titles);
        /* call to setSupportActionBar(mToolbar); must appear BEFORE
                setNavigationOnClickListener
            is set. If not the pull out window still works but the hamburger click does not.

         */
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // Enable opening of drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Add drawer listeners
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        //setupTabs(0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;

    }
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        setTitle(menuItem);
        loadFragment(menuItem.getItemId());
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setTitle(MenuItem mi){
/*        switch (mi.getItemId()){
            case R.id.m1:
                *//* NOTE! if mTitle only is set
                   The title of the toolbar will be reset to the Activity title by
                   the onCreate call to mNavigationView.setNavigationItemSelectedListener(this);
                   We must change the title of the Activity if we wish to persist the title bar
                   change on orientation
                    *//*
                this.setTitle(R.string.t0);
                break;
            case R.id.m2:
                this.setTitle(R.string.t1);
                break;
            case R.id.m3:
                this.setTitle(R.string.t2);
                break;
            default:
                Log.w(TAG, "Unknown drawer item selected");
                break;
        }*/

        int order = -1;
        for (int i=0;i < mDetailTitles.length;i++) {
            if(mDetailTitles[i].equals(mi.getTitle())) {
                order = i;
            }

        }
        mCurrentNavPosition = order;
        this.setTitle(mDetailTitles[mCurrentNavPosition]);
    }

    private void SetTitle(String inStr){
        this.setTitle(inStr);
    }

    private void loadFragment(int which){
        if (findViewById(R.id.fragment_container) != null) {
            BaseFragment fragment = null;
            Bundle args = new Bundle();
            args.putString("Dummy","Dummy");

            switch (which){
                case R.id.m1: //Look Up
                    fragment = ScreenPasswordDecodeFragment.newInstance();
                    break;
                case R.id.m2: //Todo:
                default:
                    throw new Resources.NotFoundException("Attempt to load non-existant fragment:" + which);

            }

            // Create a new Fragment to be placed in the activity layout

            fragment.setArguments(args);
            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            fragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_password_decode:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...

                setTitle("Decode");
                Toast.makeText(this,"Decode",Toast.LENGTH_LONG).show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentNavPosition = savedInstanceState.getInt(SELECTED_POSITION, 0);
        if(mCurrentNavPosition != -1) {
            final Menu menu = mNavigationView.getMenu();
            final MenuItem menuItem = menu.getItem(mCurrentNavPosition);
            menuItem.setChecked(true);
            setTitle(menuItem);
        }else{
            setTitle(savedInstanceState.getString(SELECTED_TITLE, "oops"));

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_POSITION, mCurrentNavPosition);
        outState.putString(SELECTED_TITLE, getCurrentTitle());
    }




    public String getCurrentTitle(){
        //return mDetailTitles[mCurrentNavPosition];
        return mToolbar.getTitle().toString();
    }
}

