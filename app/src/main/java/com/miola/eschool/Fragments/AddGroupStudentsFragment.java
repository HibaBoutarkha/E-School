package com.miola.eschool.Fragments;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;
import com.miola.eschool.Adapter.StudentAdapter;
import com.miola.eschool.R;
import com.miola.eschool.Repository.Group;
import com.miola.eschool.Repository.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AddGroupStudentsFragment extends Fragment implements View.OnClickListener {
    Double id_group;
    List<Student> students= new ArrayList<>();
    RecyclerView recyclerView;
    List<String> selected= new ArrayList<>();
    StudentAdapter adapter;
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    ExtendedFloatingActionButton donebutton;

    public AddGroupStudentsFragment(Double id) {
        this.id_group=id;
    }


    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView= view.findViewById(R.id.addstudentsrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adapter= new StudentAdapter((AppCompatActivity) getActivity(),students,selected);
        recyclerView.setAdapter(adapter);
        donebutton= view.findViewById(R.id.donebutton);
        donebutton.setOnClickListener(this);
        getStudents();
        }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_group_students, container, false);
    }


    public void getStudents(){
        selected.clear();
        db.collection("etudiants").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                students.clear();
                for(DocumentSnapshot change : task.getResult()) {
                    Student student = change.toObject(Student.class);
                    student.setId(change.getId());
                    students.add(student);
                }
                    adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(selected.isEmpty()) Toast.makeText(getActivity(), "Aucun etudiant n'est selectionné", Toast.LENGTH_SHORT).show();
         else   db.collectionGroup("groupes").whereEqualTo("id_prof",user.getUid()).whereEqualTo("id",id_group).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    students.clear();
                    for(String id_etudiant: selected){
                        DocumentReference doc= task.getResult().getDocuments().get(0).getReference();
                        HashMap<String, Object> student = new HashMap<>();
                        student.put("id_groupe",id_group);
                        student.put("id_etudiant",id_etudiant);
                        doc.collection("etudiantGroup").document().set(student);
                    }
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });
        }
}