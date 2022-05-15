package com.miola.eschool.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.miola.eschool.Activity.TeacherHomeActivity;
import com.miola.eschool.Fragments.GroupStudentsFragment;
import com.miola.eschool.Fragments.UpdateGroupFragment;
import com.miola.eschool.R;
import com.miola.eschool.Repository.Group;

import java.util.List;

public class GroupItemAdapter extends RecyclerView.Adapter<GroupItemAdapter.GroupViewHolder> {
    List<Double> selected;
    List<Group> groups;
    TeacherHomeActivity context;

    public GroupItemAdapter(List<Group> groups, Context context, List<Double> selected) {
        this.groups = groups;
        this.context = (TeacherHomeActivity)context;
        this.selected=selected;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.group_item_layout,parent,false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Group group = groups.get(position);
        holder.nom.setText(group.getNom());
        holder.description.setText(group.getDescription());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                view.setSelected(!view.isSelected());
                int position = holder.getAdapterPosition();
                if(view.isSelected()) selected.add(groups.get(position).getId());
                else selected.remove(groups.get(position).getId());
                return false;
            }
        });
        holder.updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = context.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.placeholder, new UpdateGroupFragment(group.getId()));
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
            }
        });

        holder.nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = context.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.placeholder, new GroupStudentsFragment(group.getId()));
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
            }
        });

    }

    @Override
    public int getItemCount() {
        return groups.size();
    }



    public static class GroupViewHolder extends RecyclerView.ViewHolder {
        ImageView nextbutton;
        ImageView updatebutton;
        TextView nom;
        TextView description;
        public GroupViewHolder(@NonNull View view) {
            super(view);
            nextbutton= view.findViewById(R.id.nextbutton);
            updatebutton= view.findViewById(R.id.updatebutton);

            nom=view.findViewById(R.id.groupId);
            description= view.findViewById(R.id.groupDescription);
            view.setSelected(false);
        }

    }
}
