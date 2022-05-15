package com.miola.eschool.Fragments;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;
import com.miola.eschool.Adapter.EmploiAdapter;
import com.miola.eschool.Adapter.StudentAdapter;
import com.miola.eschool.R;
import com.miola.eschool.Repository.Emploi;
import com.miola.eschool.Repository.Student;

import java.util.ArrayList;
import java.util.List;


public class TeacherStudentsFragment extends Fragment {
    private List<String> selected= new ArrayList<>();
    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private ArrayList<Student> students= new ArrayList<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public TeacherStudentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView= view.findViewById(R.id.studentsrecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        studentAdapter= new StudentAdapter((AppCompatActivity) getActivity(),students,selected);
        recyclerView.setAdapter(studentAdapter);

        getStudents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_students, container, false);
    }

    private void getStudents(){
        Long semester = Long.parseLong(getArguments().getString("semester"));

        db.collection("etudiants").whereEqualTo("semester",semester).orderBy("nom").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null) try {
                    throw error;
                } catch (FirebaseFirestoreException e) {
                    Toast.makeText(getContext(), "Un problème est survenu. Veuillez réessayé plus tard", Toast.LENGTH_SHORT).show();
                }

                for(DocumentChange change : value.getDocumentChanges()){
                    if(change.getType()==DocumentChange.Type.ADDED){
                        students.add(change.getDocument().toObject(Student.class));
                    }
                    studentAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private  void test(){
        db.collection("etudiants").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                System.out.println(value.getDocuments());
            }
        });
    }
}