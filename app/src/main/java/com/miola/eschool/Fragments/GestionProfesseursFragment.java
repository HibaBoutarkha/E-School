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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;
import com.miola.eschool.Adapter.ProfesseurAdapter;
import com.miola.eschool.Adapter.StudentAdapter;
import com.miola.eschool.R;
import com.miola.eschool.Repository.Professeur;
import com.miola.eschool.Repository.Student;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;


public class GestionProfesseursFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Professeur> professeurs= new ArrayList<>();
    ProfesseurAdapter adapter;
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    public GestionProfesseursFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = view.findViewById(R.id.professeursrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter= new ProfesseurAdapter((AppCompatActivity) getActivity(),professeurs);
        recyclerView.setAdapter(adapter);
        getProfesseurs();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gestion_professeurs, container, false);
    }

    public void getProfesseurs(){
        db.collection("professeurs").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                if(error!=null) try {
                    throw error;
                } catch (FirebaseFirestoreException e) {
                    Toast.makeText(getContext(), "Un problème est survenu. Veuillez réessayé plus tard", Toast.LENGTH_SHORT).show();
                }
                professeurs.clear();
                for(DocumentSnapshot doc: value){
                    professeurs.add(doc.toObject(Professeur.class));
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}