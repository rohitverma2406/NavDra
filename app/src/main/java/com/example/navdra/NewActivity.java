package com.example.navdra;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class NewActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    androidx.appcompat.widget.Toolbar toolbar;
    public ImageView dimage;
    public TextView dname;
    public TextView dbra;
    public TextView dstat;
    public  TextView dphone;
    public  TextView demail;
    private FirebaseDatabase database;
    private DatabaseReference post;
    FirebaseAuth mAuth;

    String phone;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new);

        dimage = findViewById(R.id.image);
        dname = findViewById(R.id.navhname);
        dbra = findViewById(R.id.navhbra);
        dstat = findViewById(R.id.navhstatus);
        dphone = findViewById(R.id.navehpho);
        demail = findViewById(R.id.navhemail);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();



        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(NewActivity.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = findViewById(R.id.navigation_view);
        View navView = navigationView.inflateHeaderView(R.layout.navigation_header);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                UserMenuSelected(menuItem);
                return true;
            }


        });







    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected( item);

    }
    private void UserMenuSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.edit_prof:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.leaderboard:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.logout:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.useapp:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.about:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.need:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.report:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

        }
    }
}