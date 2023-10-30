package com.example.bai1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bai1.Fragment.GioiThieu_Fragment;
import com.example.bai1.Fragment.ThongKe_Fragment;
import com.example.bai1.Fragment.Thu_Chi_Fragment;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private RelativeLayout viewFrament;
    private NavigationView navigationView;
    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        viewFrament = findViewById(R.id.viewFrament);
        navigationView = findViewById(R.id.navigationView);
        Intent intent = getIntent();
        userName = intent.getStringExtra("user");

        // xử lý toolbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("QUẢN LÝ THU CHI");
        actionBar.setLogo(R.drawable.manager);
        actionBar.setHomeAsUpIndicator(R.drawable.menu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment;
                Bundle bundle = new Bundle();
                switch (item.getItemId()){
                    default:
                    case R.id.mThu:
                        fragment = new Thu_Chi_Fragment();
                        bundle.putInt("trangthai", 0);
                        fragment.setArguments(bundle);
                        break;
                    case R.id.mChi:
                        fragment = new Thu_Chi_Fragment();
                        bundle.putInt("trangthai", 1);
                        fragment.setArguments(bundle);
                        break;
                    case R.id.mThongKe:
                        fragment = new ThongKe_Fragment();
                        break;
                    case R.id.gioiThieu:
                        fragment = new GioiThieu_Fragment();
                        break;
                    case R.id.thoat:
                        fragment = new GioiThieu_Fragment();
                        finish();
                        break;
                }
                item.setCheckable(true);
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.viewFrament, fragment)
                        .commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            TextView txtUserName = findViewById(R.id.txtUserName);
            txtUserName.setText(userName);
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}