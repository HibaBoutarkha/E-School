package com.miola.eschool.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.miola.eschool.Activity.TeacherHomeActivity;
import com.miola.eschool.R;
import com.miola.eschool.Repository.CR;

import java.util.List;

public class CRAdapter extends RecyclerView.Adapter<CRAdapter.CRViewHolder>{
    private AppCompatActivity context;
    private List<CR> crs;


    public CRAdapter(AppCompatActivity context, List<CR> crs) {
        this.context = context;
        this.crs = crs;
    }
    @NonNull
    @Override
    public CRViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cr_item_layout,parent,false);
        return new CRAdapter.CRViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CRViewHolder holder, int position) {
        CR cr= crs.get(position);
        holder.description.setText(cr.getDescription());
        holder.matiere.setText(cr.getMatière());
        holder.salle.setText(cr.getSalle());
    }

    @Override
    public int getItemCount() {
        return crs.size();
    }

    public static class CRViewHolder extends RecyclerView.ViewHolder{
        TextView description;
        TextView matiere;
        TextView salle;
        public CRViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description);
            matiere = itemView.findViewById(R.id.matiere);
            salle= itemView.findViewById(R.id.salle);
        }
    }
}
