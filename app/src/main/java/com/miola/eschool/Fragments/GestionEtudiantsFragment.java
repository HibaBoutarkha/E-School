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
import com.miola.eschool.Adapter.ProfesseurAdapter;
import com.miola.eschool.Adapter.StudentAdapter;
import com.miola.eschool.R;
import com.miola.eschool.Repository.Professeur;
import com.miola.eschool.Repository.Student;

import java.util.ArrayList;


public class GestionEtudiantsFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Student> etudiants= new ArrayList<>();
    StudentAdapter adapter;
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    public GestionEtudiantsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view ,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = view.findViewById(R.id.etudiantsrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter= new StudentAdapter((AppCompatActivity) getActivity(),etudiants, new ArrayList<>());
        recyclerView.setAdapter(adapter);
        getEtudiants();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gestion_etudiants, container, false);
    }

    public void getEtudiants(){
        db.collection("etudiants").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                if(error!=null) try {
                    throw error;
                } catch (FirebaseFirestoreException e) {
                    Toast.makeText(getContext(), "Un problème est survenu. Veuillez réessayé plus tard", Toast.LENGTH_SHORT).show();
                }
                etudiants.clear();
                for(DocumentSnapshot doc: value){
                    etudiants.add(doc.toObject(Student.class));
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}