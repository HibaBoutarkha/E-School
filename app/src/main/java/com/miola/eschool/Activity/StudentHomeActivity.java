package com.miola.eschool.Activity;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;
import com.miola.eschool.Fragments.EmploisFragment;
import com.miola.eschool.Fragments.EmploisStudentFragment;
import com.miola.eschool.R;

import static androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED;

public class StudentHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private MenuItem previousitem;
    private TextView textview;
    private DrawerLayout drawerLayout;
    private FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        //getting UI
        NavigationView nav =findViewById(R.id.nav_student);
        drawerLayout= findViewById(R.id.studentdrawerLayout);
        textview= findViewById(R.id.studenttoolbarText);
        findViewById(R.id.studentmain).bringToFront();

        //setting listeners
        findViewById(R.id.studentmenuIcon).setOnClickListener(this);
        nav.setNavigationItemSelectedListener(this);

        //setting UI
        previousitem=nav.getMenu().findItem(R.id.checkedItem);
        previousitem.setChecked(true);
        drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED);

        //setting emplois fragment
        showEmplois();

    }

    @Override
    public void onBackPressed(){
        int count=fragmentManager.getBackStackEntryCount();
        if(count==1){
            fragmentManager.popBackStack();
        }
        else {
            drawerLayout.open();
            drawerLayout.bringToFront();
            drawerLayout.setVisibility(View.VISIBLE);
        }
    }

    private void clearbackStack(){
        int count=fragmentManager.getBackStackEntryCount();
        while(count>0){
            fragmentManager.popBackStack();
            count--;
        }
    }
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.menuIcon:{
                drawerLayout.open();
                drawerLayout.bringToFront();
                drawerLayout.setVisibility(View.VISIBLE);
                break;
            }
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.setVisibility(View.INVISIBLE);
        if(previousitem!=null) previousitem.setChecked(false);
        item.setChecked(true);
        previousitem=item;
        textview.setText(item.getTitle().toString());
        try {
            Thread.sleep(120);
        }catch(InterruptedException e){}
        replace(item);
        return true;
    }

    private void replace(MenuItem item){
        switch (item.getTitle().toString()){
            case "Home":{
                clearbackStack();
                showEmplois();
                break;
            }
            case "My profile": {
                clearbackStack();
                break;
            }
            case "My teachers": {
                clearbackStack();
                break;
            }

        }
    }

    public void showEmplois(){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.studentplaceholder, new EmploisStudentFragment());
        fragmentTransaction.commit();
    }
}