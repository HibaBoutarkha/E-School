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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.miola.eschool.R;
import com.miola.eschool.Repository.Group;
import org.w3c.dom.Document;


public class UpdateGroupFragment extends Fragment {
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    Button updateButton;
    EditText name;
    EditText description;
    Double id;
    public UpdateGroupFragment(Double id) {
        this.id=id;
    }


    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = view.findViewById(R.id.group_update_Name);
        description = view.findViewById(R.id.group_update_Description);
        updateButton = view.findViewById(R.id.updategroupbutton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupname= name.getText().toString();
                String groupdescription = name.getText().toString();

                db.collectionGroup("groupes").whereEqualTo("id",id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                        document.getReference().update("nom",groupname);
                        document.getReference().update("description",groupdescription);
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                });
            }
        });
        updateView();
    }

    public void updateView(){
        db.collectionGroup("groupes").whereEqualTo("id",id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                DocumentSnapshot document = task.getResult().getDocuments().get(0);
                name.setText(document.get("nom").toString());
                description.setText(document.get("description").toString());
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_group, container, false);
    }


}