package com.miola.eschool.Activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;
import com.miola.eschool.Model.LoginModel;
import com.miola.eschool.R;
import com.miola.eschool.Repository.User;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private User user;

    private RadioGroup accountType;
    private EditText email;
    private EditText password;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.loginButton: {
                this.user=new User(this.email.getText().toString().trim(), this.password.getText().toString().trim());
                if(user.emptyFields()) Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                else {
                    this.authenticateUser();}
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getting the model instance
        this.model= new LoginModel(this);

        //listeners
        findViewById(R.id.loginButton).setOnClickListener(this);

        //get UI elements
        this.email=findViewById(R.id.emailTextField);
        this.password=findViewById(R.id.passwordTextField);
        this.accountType=findViewById(R.id.accountType);

    }

    public User getUser(){
        return this.user;
    }

    public String getAccountType(){
        int id=accountType.getCheckedRadioButtonId();
        if(id==R.id.adminAccount) return "admin";
        else if(id==R.id.scholarAccount) return "scholar";
        else return "teacher";
    }

    public void setUI(String account,String uid){

        switch (account){
            case "admin":
                Intent adintent = new Intent(this, AdminHomeActivity.class);
                adintent.putExtra("uid",uid);
                startActivity(adintent);
                break;
            case "scholar":
                Intent intent = new Intent(this, StudentHomeActivity.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
                break;
            case "teacher":
                Intent myIntent = new Intent(this, TeacherHomeActivity.class);
                myIntent.putExtra("uid",uid);
                startActivity(myIntent);
                break;
        }
    }
    //Controller
    private LoginModel model;

    private void authenticateUser(){
        model.authenticateUser(this);
    }


}