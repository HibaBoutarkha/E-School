package com.miola.eschool.Activity;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.miola.eschool.R;

public class TeacherMainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private MenuItem previousitem=null;
    private String uid;
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.menuIcon:{
                DrawerLayout drawerLayout= findViewById(R.id.drawerLayout);
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        //setting listeners
        findViewById(R.id.menuIcon).setOnClickListener(this);
        NavigationView nav =findViewById(R.id.nav_teacher);
        nav.setNavigationItemSelectedListener(this);
        TextView text= findViewById(R.id.textView);
        //setting the uid
        uid=(String) getIntent().getExtras().get("uid");
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(previousitem!=null) previousitem.setChecked(false);
        item.setChecked(true);
        previousitem=item;
        return true;
    }
}