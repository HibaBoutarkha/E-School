package com.miola.eschool.Fragments;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
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
import com.miola.eschool.Adapter.TeacherSemestersAdapter;
import com.miola.eschool.Repository.Emploi;
import com.miola.eschool.Repository.Semester;
import com.miola.eschool.R;

import java.util.ArrayList;


public class TeacherSemesterFragment extends Fragment {
    private RecyclerView recyclerView;
    private TeacherSemestersAdapter adapter;
    private ArrayList<Semester> semesters= new ArrayList<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    public TeacherSemesterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        recyclerView= view.findViewById(com.miola.eschool.R.id.semesterrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter= new TeacherSemestersAdapter(getActivity(),semesters);
        recyclerView.setAdapter(adapter);

        //getting data
        getSemesters();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_student, container, false);
    }

    private void getSemesters(){
        String uid= user.getUid();
        db.collectionGroup("semesters").whereEqualTo("id_prof",uid).orderBy("semester").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null) try {
                    throw error;
                } catch (FirebaseFirestoreException e) {
                    Toast.makeText(getContext(), "Un problème est survenu. Veuillez réessayer plus tard", Toast.LENGTH_SHORT).show();
                }
                semesters.clear();
                    for (DocumentChange changeSemesters : value.getDocumentChanges()) {
                        if(changeSemesters.getType()==DocumentChange.Type.ADDED) {
                            semesters.add(changeSemesters.getDocument().toObject(Semester.class));
                        }
                        else getSemesters();
                    }
                adapter.notifyDataSetChanged();
                }

        });
    }
}