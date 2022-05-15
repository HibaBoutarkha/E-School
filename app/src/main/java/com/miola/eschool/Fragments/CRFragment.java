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
import com.miola.eschool.Adapter.CRAdapter;
import com.miola.eschool.R;
import com.miola.eschool.Repository.CR;

import java.util.ArrayList;

public class CRFragment extends Fragment {
    private RecyclerView recyclerView;
    private CRAdapter crAdapter;
    private ArrayList<CR> crs= new ArrayList<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    private String account;
    public CRFragment(String account){
        this.account=account;
    }
    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView= view.findViewById(com.miola.eschool.R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        crAdapter= new CRAdapter((AppCompatActivity) getActivity(),crs);
        recyclerView.setAdapter(crAdapter);
        //getting data
        if(account=="scolar") getCRScholar();
        else getCR();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cr, container, false);
    }

    private void getCR(){
        try {
            String uid = user.getUid();
            String semester = getArguments().getString("semester");
            String week = getArguments().getString("week");
            db.collectionGroup("CR").whereEqualTo("id_prof",uid).whereEqualTo("semester",semester).whereEqualTo("week",week).orderBy("seance").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error!=null) try {
                        throw error;
                    } catch (FirebaseFirestoreException e) {
                        Toast.makeText(getContext(), "Un problème est survenu. Veuillez réessayé plus tard", Toast.LENGTH_SHORT).show();
                    }
                    for(DocumentChange change : value.getDocumentChanges()){
                        if(change.getType()==DocumentChange.Type.ADDED){
                            System.out.println(change.getDocument());
                            crs.add(change.getDocument().toObject(CR.class));
                        }
                        crAdapter.notifyDataSetChanged();
                    }
                }
            });

        }catch(IndexOutOfBoundsException e){
            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void getCRScholar(){
        try {
            String uid = user.getUid();
            String semester = getArguments().getString("semester");
            String week = getArguments().getString("week");
            db.collectionGroup("CR").whereEqualTo("semester",semester).whereEqualTo("week",week).orderBy("seance").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error!=null) try {
                        throw error;
                    } catch (FirebaseFirestoreException e) {
                        Toast.makeText(getContext(), "Un problème est survenu. Veuillez réessayé plus tard", Toast.LENGTH_SHORT).show();
                    }
                    for(DocumentChange change : value.getDocumentChanges()){
                        if(change.getType()==DocumentChange.Type.ADDED){
                            System.out.println(change.getDocument());
                            crs.add(change.getDocument().toObject(CR.class));
                        }
                        crAdapter.notifyDataSetChanged();
                    }
                }
            });

        }catch(IndexOutOfBoundsException e){
            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }
}