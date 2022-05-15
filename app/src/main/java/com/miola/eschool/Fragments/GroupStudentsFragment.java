package com.miola.eschool.Fragments;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;
import com.miola.eschool.Adapter.GroupItemAdapter;
import com.miola.eschool.Adapter.StudentAdapter;
import com.miola.eschool.R;
import com.miola.eschool.Repository.Student;

import java.util.ArrayList;
import java.util.List;


public class GroupStudentsFragment extends Fragment implements View.OnClickListener {
    double id;
    RecyclerView recyclerView;
    List<String> selected= new ArrayList<>();
    ExtendedFloatingActionButton addbutton;
    ExtendedFloatingActionButton deletebutton;
    ArrayList<Student> students= new ArrayList<>();
    StudentAdapter adapter;
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    public GroupStudentsFragment(double id) {
        this.id=id;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = view.findViewById(R.id.studentsrecyclerview);
        addbutton= view.findViewById(R.id.addstudentsbutton);
        deletebutton= view.findViewById(R.id.deletestudentsbutton);
        addbutton.setOnClickListener(this);
        deletebutton.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter= new StudentAdapter((AppCompatActivity) getActivity(),students, selected);
        recyclerView.setAdapter(adapter);
        getStudents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_students, container, false);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.addstudentsbutton:{
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.placeholder, new AddGroupStudentsFragment(id));
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            }

            case R.id.deletestudentsbutton:{
                if(selected.size()==0) Toast.makeText(getActivity(), "Aucun etudiant n'est selectionné", Toast.LENGTH_SHORT).show();
                else{
                    for(String id: selected){
                        db.collectionGroup("etudiantGroup").whereEqualTo("id_etudiant",id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> value) {
                                for(DocumentSnapshot document: value.getResult()){
                                    document.getReference().delete();
                                }
                                getStudents();
                            }
                        });
                    }
                    selected.clear();
                }
                break;
            }
        }
    }

    private void getStudents(){
        System.out.println("this is student group fragment");
        students.clear();
        adapter.notifyDataSetChanged();
        selected.clear();
        db.collectionGroup("etudiantGroup").whereEqualTo("id_groupe",id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        students.clear();
                        for(DocumentSnapshot doc: task.getResult()) {
                            String studentId = doc.getString("id_etudiant");
                            db.collection("etudiants").document(studentId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    Student student = task.getResult().toObject(Student.class);
                                    student.setId(studentId);
                                    System.out.println("student id"+ studentId);
                                    students.add(student);
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                });
    }
}