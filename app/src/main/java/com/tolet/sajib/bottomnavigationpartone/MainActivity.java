package com.tolet.sajib.bottomnavigationpartone;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.tolet.sajib.bottomnavigationpartone.utils.BottomNavigationViewHelper;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.ConnectionBuddyConfiguration;
import com.zplesac.connectionbuddy.interfaces.ConnectivityChangeListener;
import com.zplesac.connectionbuddy.models.ConnectivityEvent;
import com.zplesac.connectionbuddy.models.ConnectivityState;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements
        ConnectivityChangeListener, NavigationView.OnNavigationItemSelectedListener {

    private TextView networkConnectionCheckTxt;
    private static BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkConnectionCheckTxt = findViewById(R.id.networkConnectionTV);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
         drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView=findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);
         //initViews();
        initViewtwo();
        navigationView.setNavigationItemSelectedListener(this);

        ConnectionBuddyConfiguration connectionBuddyConfiguration = new ConnectionBuddyConfiguration.Builder(this).build();
        ConnectionBuddy.getInstance().init(connectionBuddyConfiguration);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        toggle.setHomeAsUpIndicator(R.drawable.ic_drawer);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }



    private void initViews() {
        networkConnectionCheckTxt = findViewById(R.id.networkConnectionTV);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        loadFragment(new NewsfeedFragment());
        // bottomNavigationView.setOnNavigationItemSelectedListener(MainActivity.this);

    }

    static final int TYPE_nav_news_feed = 1;
    static final int TYPE_nav_friends = 2;
    static final int TYPE_nav_chat = 3;
    static final int TYPE_nav_profile = 4;
    static final int TYPE_nav_notification = 5;


    private void initViewtwo() {
        showFragment(TYPE_nav_news_feed);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {


            switch (item.getItemId()) {
                case R.id.nav_news_feed:
                    showFragment(TYPE_nav_news_feed);
                    return true;

                case R.id.nav_friends:
                    showFragment(TYPE_nav_friends);
                    return true;

                case R.id.nav_chat:
//loadFragment(ChatMainFragment.getFragment(ChatMainFragment.TYPE_RECENT));
                    return true;

                case R.id.nav_profile:
                    showFragment(TYPE_nav_profile);
                    return true;

                case R.id.nav_notification:
                    showFragment(TYPE_nav_notification);
                    return true;
            }
            return false;
        });
    }


    private void showFragment(int type) {
        switch (type) {

            case TYPE_nav_news_feed:
                loadFragment(NewsfeedFragment.getFragment());
                break;

            case TYPE_nav_friends:
                loadFragment(FriendsFragment.getFragment());
                break;


            case TYPE_nav_profile:
                loadFragment(ProfileFragment.getFragment());
                break;


          /*  case TYPE_nav_notification:
                loadFragment(NotificationsFragment.getFragment());
                break;*/


        }
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
            return true;
        }

        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectionBuddy.getInstance().registerForConnectivityEvents(this, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ConnectionBuddy.getInstance().unregisterFromConnectivityEvents(this);
    }

    @Override
    public void onConnectionChange(ConnectivityEvent event) {
        if (event.getState().getValue() == ConnectivityState.CONNECTED) {
            networkConnectionCheckTxt.setText(R.string.connected);
            networkConnectionCheckTxt.setBackgroundColor(getResources().getColor(R.color.colorGreen));
            networkConnectionCheckTxt.setTextColor(getResources().getColor(R.color.colorWhite));
            new Handler().postDelayed(() -> networkConnectionCheckTxt.setVisibility(View.GONE), 2000);

        } else if (event.getState().getValue() == ConnectivityState.DISCONNECTED) {
            networkConnectionCheckTxt.setText(R.string.disConnected);
            networkConnectionCheckTxt.setBackgroundColor(getResources().getColor(R.color.colorRed));
            networkConnectionCheckTxt.setTextColor(getResources().getColor(R.color.colorWhite));
            networkConnectionCheckTxt.setVisibility(View.VISIBLE);

            //Toast.makeText(this, "Connection Lost", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
// DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });


        switch (menuItem.getItemId()) {
            case R.id.nev_private:

                bottomNavigationView.setSelectedItemId(R.id.nav_chat);
                return true;
            case R.id.nev_public:
                bottomNavigationView.setSelectedItemId(R.id.nav_chat);
                return true;

            case R.id.nav_news_feed:
                bottomNavigationView.setSelectedItemId(R.id.nav_news_feed);
// showFragment(TYPE_nav_news_feed);
                return true;

            case R.id.nav_friends:
                bottomNavigationView.setSelectedItemId(R.id.nav_friends);
// showFragment(TYPE_nav_friends);
                return true;

            case R.id.nav_profile:
                bottomNavigationView.setSelectedItemId(R.id.nav_profile);

// showFragment(TYPE_nav_profile);
                return true;

            case R.id.nav_notifications_1:
                bottomNavigationView.setSelectedItemId(R.id.nav_notification);

// showFragment(TYPE_nav_notification);
                return true;


        }

        return false;
    }



   /* @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        int bottomId = menuItem.getItemId();
        switch (bottomId) {
            case R.id.nav_news_feed:

               fragment = new NewsfeedFragment();
                break;
            case R.id.nav_friends:
                fragment = new FriendsFragment();
                break;
            case R.id.nav_chat:
                fragment = new ChatFragment();
                break;
            case R.id.nav_profile:
                fragment = new ProfileFragment();
                break;
            case R.id.nav_notification:
                fragment = new ProfileFragment();
                break;
        }
        return loadFragments(fragment);

    }*/

}

