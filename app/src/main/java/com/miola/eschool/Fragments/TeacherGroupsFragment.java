package com.miola.eschool.Fragments;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;
import com.miola.eschool.Adapter.GroupItemAdapter;
import com.miola.eschool.R;
import com.miola.eschool.Repository.Group;
import java.util.ArrayList;



public class TeacherGroupsFragment extends Fragment implements View.OnClickListener {
    RecyclerView recyclerView;
    ExtendedFloatingActionButton addButton;
    ExtendedFloatingActionButton deleteButton;
    ArrayList<Group> groups= new ArrayList<>();
    ArrayList<Double> selected= new ArrayList<>();
    GroupItemAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    public TeacherGroupsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addButton= view.findViewById(R.id.addgroupbutton);
        deleteButton= view.findViewById(R.id.deletegroupbutton);
        addButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        recyclerView= view.findViewById(R.id.grouprecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter= new GroupItemAdapter(groups,getActivity(),selected);
        recyclerView.setAdapter(adapter);
        getGroups();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_groups, container, false);
    }


    private void getGroups(){
        selected.clear();
        String id_prof= user.getUid();
        db.collectionGroup("groupes").whereEqualTo("id_prof",id_prof).orderBy("id").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null) try {
                    throw error;
                } catch (FirebaseFirestoreException e) {
                    Toast.makeText(getContext(), "Un problème est survenu. Veuillez réessayé plus tard", Toast.LENGTH_SHORT).show();
                }
                groups.clear();
                for(DocumentChange change : value.getDocumentChanges()){
                    if(change.getType()==DocumentChange.Type.ADDED){
                        groups.add(change.getDocument().toObject(Group.class));
                    }
                    else{
                        getGroups();
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addgroupbutton: {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.placeholder, new AddGroupFragment(Math.random()));
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            }

            case R.id.deletegroupbutton: {
                if(selected.size()==0) Toast.makeText(getActivity(), "Aucun groupe n'est selectionné", Toast.LENGTH_SHORT).show();
                else{
                    for(double id: selected){
                        db.collectionGroup("groupes").whereEqualTo("id",id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> value) {
                                for(DocumentSnapshot document: value.getResult()){
                                    document.getReference().delete();
                                }
                                getGroups();
                            }
                        });
                    }
                }
                break;
            }
        }
    }
}