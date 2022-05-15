package com.miola.eschool.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.miola.eschool.Activity.TeacherHomeActivity;
import com.miola.eschool.Repository.Semester;
import com.miola.eschool.R;

import java.util.List;

public class TeacherSemestersAdapter extends RecyclerView.Adapter<TeacherSemestersAdapter.SemesterViewHolder> {
    private List<Semester> semesters;
    private TeacherHomeActivity context;

    public TeacherSemestersAdapter(Context context, List<Semester> semesters) {
        this.semesters = semesters;
        this.context= (TeacherHomeActivity) context;
    }

    @NonNull
    @Override
    public SemesterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.semester_item_layout,parent,false);
        return new TeacherSemestersAdapter.SemesterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SemesterViewHolder holder, int position) {
        Semester semester= semesters.get(position);
        holder.semester.setText(""+semester.getSemester());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String semester=holder.semester.getText().toString();
                context.showStudents(semester);
            }
        });
    }

    @Override
    public int getItemCount() {
        return semesters.size();
    }

    public static class SemesterViewHolder extends RecyclerView.ViewHolder{
        TextView semester;
        ConstraintLayout parentLayout;
        public SemesterViewHolder(@NonNull View itemView) {
            super(itemView);
            semester=itemView.findViewById(R.id.semester);
            parentLayout= itemView.findViewById(R.id.semesteritem);
        }
    }
}
