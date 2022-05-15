package com.miola.eschool.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.miola.eschool.R;
import com.squareup.picasso.Picasso;


public class TeacherProfileFragment extends Fragment {
    private View view;
    private TextView departement;
    private TextView fullname;
    private TextView matieres;
    private TextView email;
    private ImageView profilepicture;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    public TeacherProfileFragment() {
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.view=view;
        departement=view.findViewById(R.id.departement);
        matieres=view.findViewById(R.id.matieres);
        fullname=view.findViewById(R.id.fullname);
        email=view.findViewById(R.id.profil_email);
        email.getPaint().setUnderlineText(true);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + email.getText().toString()));
                intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
                intent.putExtra(Intent.EXTRA_TEXT, "your_text");
                startActivity(intent);
            }
        });
        profilepicture=view.findViewById(R.id.profilepicture);
        getProfile();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private void getProfile(){
        String uid= user.getUid();
        db.collection("professeurs").document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                                           @Override
                                                                           public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                                                               if(error!=null) try {
                                                                                   throw error;
                                                                               } catch (FirebaseFirestoreException e) {
                                                                                   Toast.makeText(getContext(), "Un problème est survenu. Veuillez réessayé plus tard", Toast.LENGTH_SHORT).show();
                                                                               }
                                                                               if(value!=null && value.exists()){
                                                                                   Uri uri= Uri.parse(value.getString("photoURL"));
                                                                                   Picasso.with(getContext()).load(uri).into(profilepicture, new com.squareup.picasso.Callback() {
                                                                                       @Override
                                                                                       public void onSuccess() {
                                                                                           departement.setText(value.getString("departement"));
                                                                                           matieres.setText(value.getString("matieres"));
                                                                                           fullname.setText(value.getString("nom"));
                                                                                           email.setText(user.getEmail().trim());
                                                                                           view.setVisibility(View.VISIBLE);
                                                                                           try {
                                                                                               getActivity().findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                                                                                           }catch(NullPointerException e){}
                                                                                       }

                                                                                       @Override
                                                                                       public void onError() {
                                                                                           Toast.makeText(getContext(), "Une erreur est survenue. Veuillez essayer plus tard ", Toast.LENGTH_SHORT).show();
                                                                                       }
                                                                                   });

                                                                               }
                                                                           }
                                                                       }
        );
    }
}