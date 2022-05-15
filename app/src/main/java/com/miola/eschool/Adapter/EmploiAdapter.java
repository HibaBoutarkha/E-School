package com.miola.eschool.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.miola.eschool.Activity.TeacherHomeActivity;
import com.miola.eschool.Fragments.CRFragment;
import com.miola.eschool.R;
import com.miola.eschool.Repository.Emploi;

import java.util.List;

public class EmploiAdapter extends RecyclerView.Adapter<EmploiAdapter.EmploiViewHolder> {
    private List<Emploi> emplois;
    private AppCompatActivity context;
    private String account;
    public EmploiAdapter(AppCompatActivity context, List<Emploi> emplois, String account) {
        this.context = context;
        this.emplois = emplois;
        this.account= account;
    }

    @NonNull
    @Override
    public EmploiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.week_item_layout,parent,false);
        return new EmploiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmploiViewHolder holder, int position) {
        Emploi emploi= emplois.get(position);
        holder.semester.setText(""+emploi.getSemester());
        holder.week.setText(""+emploi.getWeek());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String semester=holder.semester.getText().toString();
                String week= holder.week.getText().toString();
                showCR(semester,week);
            }
        });
    }

    @Override
    public int getItemCount() {
        return emplois.size();
    }

    public static class EmploiViewHolder extends RecyclerView.ViewHolder{
        TextView semester, week;
        ConstraintLayout parentLayout;
        public EmploiViewHolder(@NonNull View itemView) {
            super(itemView);
            week = itemView.findViewById(R.id.week);
            semester = itemView.findViewById(R.id.semester);
            parentLayout= itemView.findViewById(R.id.teacheritem);
        }
    }

    public void showCR(String semester, String week){
        Bundle bundle= new Bundle();
        bundle.putString("semester",semester);
        bundle.putString("week",week);

        //set fragment
        CRFragment crFragment= new CRFragment(account);
        crFragment.setArguments(bundle);

        //add fragment
        FragmentTransaction fragmentTransaction = context.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, crFragment);
        fragmentTransaction.addToBackStack("upperfragment");
        fragmentTransaction.commit();
    }



}
