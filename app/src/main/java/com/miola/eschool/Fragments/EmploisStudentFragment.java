package com.miola.eschool.Fragments;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.CookieManager;
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
import com.miola.eschool.Adapter.EmploiAdapter;
import com.miola.eschool.R;
import com.miola.eschool.Repository.Emploi;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class EmploisStudentFragment extends Fragment {
    private RecyclerView recyclerView;
    private EmploiAdapter emploiAdapter;
    private ExtendedFloatingActionButton downloadbutton;
    private ArrayList<Emploi> emplois= new ArrayList<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    Long week;
    Long semester;

    public EmploisStudentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView= view.findViewById(com.miola.eschool.R.id.studentemploirecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        emploiAdapter= new EmploiAdapter((AppCompatActivity) getActivity(),emplois,"scholar");
        recyclerView.setAdapter(emploiAdapter);
        downloadbutton= view.findViewById(R.id.studentemploidownloadbutton);
        downloadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadPDF();
            }
        });
        //getting data
        getSemester();

    }

    public void getSemester(){
        db.collection("etudiants").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                semester= task.getResult().getLong("semester");
                getEmploi();
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_emplois_student, container, false);
    }
    private void downloadPDF(){
        db.collectionGroup("semesters").whereEqualTo("week",week).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(DocumentSnapshot doc: task.getResult()){
                    String url =doc.getString("URL");
                    if(url!="" && url!=null) {
                        String title = "S" + doc.get("semester") + "_week" + week+".pdf";
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                        request.setTitle(title);
                        String cookie = CookieManager.getInstance().getCookie(url);
                        request.addRequestHeader("cookie", cookie);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);
                        DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(getActivity().DOWNLOAD_SERVICE);
                        downloadManager.enqueue(request);
                        Toast.makeText(getActivity(), "Le téléchargement a commencé", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    public void getEmploi(){
        try {
            String uid=user.getUid();
            db.collection("emploi").orderBy("week", Query.Direction.DESCENDING).limit(1).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error!=null) try {
                        throw error;
                    } catch (FirebaseFirestoreException e) {
                        Toast.makeText(getContext(), "Un problème est survenu. Veuillez réessayé plus tard", Toast.LENGTH_SHORT).show();
                    }
                    for(DocumentChange change : value.getDocumentChanges()){
                        if(change.getType()==DocumentChange.Type.ADDED){
                            emplois.clear();
                            emploiAdapter.notifyDataSetChanged();
                            week= change.getDocument().getLong("week");
                            System.out.println("week"+week);
                            db.collectionGroup("semesters").whereEqualTo("semester",semester).whereEqualTo("week",week).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if(error!=null) try {
                                        throw error;
                                    } catch (FirebaseFirestoreException e) {
                                        System.out.println(e);
                                        Toast.makeText(getContext(), "Un problème est survenu. Veuillez réessayé plus tard", Toast.LENGTH_SHORT).show();
                                    }

                                    if(!value.isEmpty()) for(DocumentChange changeSemesters : value.getDocumentChanges()) {
                                        if(changeSemesters.getType()==DocumentChange.Type.ADDED) emplois.add(changeSemesters.getDocument().toObject(Emploi.class));
                                        else getEmploi();
                                    }

                                    emploiAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                }
            });

        }catch(IndexOutOfBoundsException e){
            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }
}