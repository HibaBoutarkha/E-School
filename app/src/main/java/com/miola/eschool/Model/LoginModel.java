package com.miola.eschool.Model;

import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.miola.eschool.Activity.LoginActivity;
import com.miola.eschool.Repository.User;


public class LoginModel {
    private LoginActivity activity;
    private FirebaseAuth auth;
    private static LoginModel model=null;

    public LoginModel(LoginActivity activity){
        this.activity=activity;
        this.auth=FirebaseAuth.getInstance();
    }


    public void checkRole(){
        auth.getCurrentUser().getIdToken(false).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
            @Override
            public void onSuccess(GetTokenResult result) {
                try {
                    boolean isAccount = (boolean) result.getClaims().get(activity.getAccountType());
                    if(isAccount) activity.setUI(activity.getAccountType(),auth.getUid());
                }catch(NullPointerException e){
                    Toast.makeText(activity, "There's no similar credentials in our database for this account type", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void authenticateUser(LoginActivity activity){

        User user= activity.getUser();
        auth.signInWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      if(task.isSuccessful()) {
                          checkRole();
                      }
                      else Toast.makeText(activity, "Email or password are wrong", Toast.LENGTH_LONG).show();
                  }
              }
      );
    }
}
