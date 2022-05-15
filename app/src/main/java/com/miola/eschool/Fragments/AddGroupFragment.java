package com.miola.eschool.Fragments;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.DocumentTransform;
import com.miola.eschool.R;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;


public class AddGroupFragment extends Fragment implements View.OnClickListener {
    double id;
    EditText nomgroup;
    EditText description;
    Button addButton;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final FirebaseFirestore db= FirebaseFirestore.getInstance();

    public AddGroupFragment(double id) {
        // Required empty public constructor
        this.id=id;
    }


    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nomgroup= view.findViewById(R.id.groupName);
        description= view.findViewById(R.id.group_Description);
        addButton= view.findViewById(R.id.addbutton);
        addButton.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_group, container, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addbutton:{
                String nom= nomgroup.getText().toString();
                String description= this.description.getText().toString();
                String id_prof= user.getUid();
                HashMap<String, Object> group = new HashMap<>();
                group.put("nom",nom);
                group.put("description",description);
                group.put("id_prof",id_prof);
                group.put("id",id);
                System.out.println("group "+group);

                db.collection("professeurs").document(user.getUid()).collection("groupes").document().set(group).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                });}
        }
    }
}