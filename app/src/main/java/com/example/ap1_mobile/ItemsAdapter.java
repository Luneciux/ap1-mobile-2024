package com.example.ap1_mobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {

    private List<Task> tasks;

    public ItemsAdapter(List<Task> tasks){
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item, parent, false
        );
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        Task item = tasks.get(position);
        holder.bind(item, position);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> items) {
        this.tasks = items ;
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtDesc;
        TextView icon;

        int index;

        public ItemsViewHolder(@NonNull View itemView){
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_user);
            txtDesc = itemView.findViewById(R.id.txt_subject);
            icon = itemView.findViewById(R.id.txt_icon);
        }

        public void bind(Task item, int i) {
            txtTitle.setText(item.title);
            txtDesc.setText(item.desc);
            index = i;
        }
    }


}