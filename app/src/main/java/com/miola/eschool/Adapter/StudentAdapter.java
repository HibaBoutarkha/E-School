package com.miola.eschool.Adapter;

import android.content.Context;
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
import com.miola.eschool.Activity.TeacherHomeActivity;
import com.miola.eschool.Fragments.TeacherStudentsFragment;
import com.miola.eschool.R;
import com.miola.eschool.Repository.Student;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private List<String> selected;
    private List<Student> students;
    private AppCompatActivity context;

    public StudentAdapter(AppCompatActivity context, List<Student> students,List<String> selected) {
        this.students = students;
        this.context = context;
        this.selected= selected;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.student_item_layout,parent,false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = students.get(position);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                view.setSelected(!view.isSelected());
                int position = holder.getAdapterPosition();
                if(view.isSelected()) selected.add(students.get(position).getId());
                else selected.remove(students.get(position).getId());
                return false;
            }
        });
        Picasso.with(context).load(Uri.parse(student.getPhotoURL())).into(holder.profilepicture, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressbar.setVisibility(View.INVISIBLE);
                holder.fullname.setText(student.getNom());
                holder.email.setText(student.getEmail());
            }

            @Override
            public void onError() {
                Toast.makeText(context, "Une erreur est survenue. Veuillez réessayer plus tard", Toast.LENGTH_SHORT).show();
            }
        });
        holder.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + holder.email.getText().toString()));
                intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
                intent.putExtra(Intent.EXTRA_TEXT, "your_text");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder{
        TextView fullname;
        ImageView profilepicture;
        TextView email;
        ProgressBar progressbar;
        public StudentViewHolder(@NonNull View view) {
            super(view);
            fullname= view.findViewById(R.id.studentfullname);
            profilepicture= view.findViewById(R.id.studentprofilepicture);
            email= view.findViewById(R.id.studentemail);
            email.getPaint().setUnderlineText(true);
            progressbar= view.findViewById(R.id.studentprogressBar);
        }
    }
}
