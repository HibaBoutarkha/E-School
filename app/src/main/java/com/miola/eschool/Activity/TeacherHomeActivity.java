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
import com.miola.eschool.Fragments.*;
import com.miola.eschool.R;

import static androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED;


public class TeacherHomeActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
    private MenuItem previousitem;
    private TextView textview;
    private DrawerLayout drawerLayout;
    private FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        fragmentManager= this.getSupportFragmentManager();

        //getting UI
        NavigationView nav =findViewById(R.id.nav_teacher);
        drawerLayout= findViewById(R.id.drawerLayout);
        textview= findViewById(R.id.toolbarText);
        findViewById(R.id.main).bringToFront();

        //setting listeners
        findViewById(R.id.menuIcon).setOnClickListener(this);
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


    private void replace(MenuItem item){
        switch (item.getTitle().toString()){
            case "Home":{
                clearbackStack();
                showEmplois();
                break;
            }
            case "My profile": {
                clearbackStack();
                showProfile();
                break;
            }
            case "My students": {
                clearbackStack();
                showSemesters();
                break;
            }
            case "My groups": {
                clearbackStack();
                showGroups();
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
    public void showEmplois(){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, new EmploisFragment());
        fragmentTransaction.commit();
    }

    public void showCR(String semester, String week){
        Bundle bundle= new Bundle();
        bundle.putString("semester",semester);
        bundle.putString("week",week);

        //set fragment
        CRFragment crFragment= new CRFragment("");
        crFragment.setArguments(bundle);

        //add fragment
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, crFragment);
        fragmentTransaction.addToBackStack("upperfragment");
        fragmentTransaction.commit();
    }

    private void showProfile(){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, new TeacherProfileFragment());
        fragmentTransaction.commit();
    }

    private void showSemesters(){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, new TeacherSemesterFragment());
        fragmentTransaction.commit();
    }

    public void showStudents(String semester){
        Bundle bundle= new Bundle();
        bundle.putString("semester",semester);

        //set fragment
        TeacherStudentsFragment studentsFragment= new TeacherStudentsFragment();
        studentsFragment.setArguments(bundle);

        //add fragment
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, studentsFragment);
        fragmentTransaction.addToBackStack("upperfragment");
        fragmentTransaction.commit();
    }


    private void showGroups(){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, new TeacherGroupsFragment());
        fragmentTransaction.commit();
    }

}