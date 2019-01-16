package com.example.adina.theatretickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adina.theatretickets.Fragments.LIstAllTicketsFragment;
import com.example.adina.theatretickets.Fragments.ListAllPlaysFragment;
import com.example.adina.theatretickets.Fragments.ListAllUsersFragment;

public class AdminFirstActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private TextView mTextMessage;
    public static boolean isAdmin = false;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_first);


        isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);

        toolbar = findViewById(R.id.toolbar);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(toolbar);

        ImageButton imageButton = (ImageButton) toolbar.findViewById(R.id.logOutButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "LOGOUT", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AdminFirstActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        if (isAdmin) {
            navigation.getMenu().getItem(0).setVisible(true);
            navigation.getMenu().getItem(1).setVisible(true);
            navigation.getMenu().getItem(2).setVisible(true);
        } else {
            navigation.getMenu().getItem(0).setVisible(true);
            navigation.getMenu().getItem(1).setVisible(true);
            navigation.getMenu().getItem(2).setVisible(false);
        }

        navigation.setOnNavigationItemSelectedListener(this);
        setActualFragment();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment = null;
            Class fragmentClass = null;
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    fragmentClass = ListAllPlaysFragment.class;
                    break;

                case R.id.navigation_my_tickets:
                    fragmentClass = LIstAllTicketsFragment.class;
                    break;
                case R.id.navigation_users:
                    fragmentClass = ListAllUsersFragment.class;
                    break;
            }

            try {
                fragment = (Fragment) fragmentClass.newInstance();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
            } catch (Exception e){
                e.printStackTrace();
            }


            menuItem.setChecked(true);

            return true;
        }


        public void setActualFragment(){
        Fragment fragment = null;
        Class fragmentClass = null;
        fragmentClass = ListAllPlaysFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
            } catch (Exception e){
                e.printStackTrace();
            }
        }


    @Override
    public void onBackPressed() {
        //finish();
    }
}

