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
import com.miola.eschool.Fragments.GestionEtudiantsFragment;
import com.miola.eschool.Fragments.GestionProfesseursFragment;
import com.miola.eschool.R;

import static androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED;

public class AdminHomeActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
    private MenuItem previousitem;
    private TextView textview;
    private DrawerLayout drawerLayout;
    private FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        fragmentManager= this.getSupportFragmentManager();

        //getting UI
        NavigationView nav =findViewById(R.id.nav_admin);
        drawerLayout= findViewById(R.id.admindrawerLayout);
        textview= findViewById(R.id.admintoolbarText);
        findViewById(R.id.admain).bringToFront();

        //setting listeners
        findViewById(R.id.adminmenuIcon).setOnClickListener(this);
        nav.setNavigationItemSelectedListener(this);

        //setting UI
        previousitem=nav.getMenu().findItem(R.id.admincheckedItem);
        previousitem.setChecked(true);
        drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED);

        getProsseurs();
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
            case R.id.adminmenuIcon:{
                drawerLayout.open();
                drawerLayout.bringToFront();
                drawerLayout.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    private void replace(MenuItem item){
        switch (item.getTitle().toString()){
            case "Gestion Professeurs":{
                clearbackStack();
                getProsseurs();
                break;
            }
            case "Gestion Etudiants": {
                clearbackStack();
                getEtudiants();
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

    public void getProsseurs(){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.adminplaceholder, new GestionProfesseursFragment());
        fragmentTransaction.commit();
    }

    public void getEtudiants(){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.adminplaceholder, new GestionEtudiantsFragment());
        fragmentTransaction.commit();
    }
}