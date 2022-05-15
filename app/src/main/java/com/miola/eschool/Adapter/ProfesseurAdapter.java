package com.miola.eschool.Adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.miola.eschool.R;
import com.miola.eschool.Repository.Professeur;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProfesseurAdapter extends RecyclerView.Adapter<ProfesseurAdapter.ProfesseurViewHolder> {
    AppCompatActivity context;
    List<Professeur> professeurs;

    public ProfesseurAdapter(AppCompatActivity context, List<Professeur> professeurs){
        this.context=context;
        this.professeurs=professeurs;
    }

    @NonNull
    @NotNull
    @Override
    public ProfesseurViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_profile,parent,false);
        return new ProfesseurViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProfesseurViewHolder holder, int position) {
        Professeur professeur = professeurs.get(position);
        Uri uri = Uri.parse(professeur.getPhotoURL());
        Picasso.with(context).load(uri).into(holder.profilepicture, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                holder.departement.setText(professeur.getDepartement());
                holder.email.setText(professeur.getEmail());
                holder.fullname.setText(professeur.getNom());
                holder.matieres.setText(professeur.getMatiere());
                holder.email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + holder.email.getText().toString()));
                        intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
                        intent.putExtra(Intent.EXTRA_TEXT, "your_text");
                        context.startActivity(intent);
                    }
                });
                try {
                    holder.progressBar.setVisibility(View.INVISIBLE);
                } catch (NullPointerException e) {
                }
            }

            @Override
            public void onError() {
                Toast.makeText(context, "Une erreur est survenue. Veuillez essayer plus tard ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return professeurs.size();
    }


    static class ProfesseurViewHolder extends RecyclerView.ViewHolder {
        private TextView departement;
        private TextView fullname;
        private TextView matieres;
        private TextView email;
        private ImageView profilepicture;
        private ProgressBar progressBar;
        public ProfesseurViewHolder(@NonNull @NotNull View view) {
            super(view);
            departement=view.findViewById(R.id.departement);
            matieres=view.findViewById(R.id.matieres);
            fullname=view.findViewById(R.id.fullname);
            email=view.findViewById(R.id.profil_email);
            email.getPaint().setUnderlineText(true);
            profilepicture=view.findViewById(R.id.profilepicture);
            progressBar= view.findViewById(R.id.progressBar);
        }
    }
}
