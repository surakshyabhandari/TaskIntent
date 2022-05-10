package com.example.taskintent;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RowActivity extends RecyclerView.Adapter<RowActivity.ViewHolder>{

    private List<Task> task_data = new ArrayList<>();
    private OnItemClickListener listener;

    public void setTaskData(List<Task> tasksData) {
        this.task_data.clear();
        this.task_data = tasksData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View rowtask = layoutInflater.inflate(R.layout.activity_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(rowtask);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final Task data = task_data.get(position);
        holder.Title.setText(data.getTitle());
        holder.Description.setText(data.getDescription());
    }

    @Override
    public int getItemCount() {
        return task_data.size();
    }

    public int getPosition() {
        return getPosition();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView Title;
        public TextView Description;
        public RelativeLayout row_container;
        public ImageView deleteicon;

        public ViewHolder(View view) {
            super(view);

            this.Title = view.findViewById(R.id.Title);
            this.Description = view.findViewById(R.id.Description);
            this.row_container = view.findViewById(R.id.taskrow);
            this.deleteicon = view.findViewById(R.id.deleteicon);
            boolean undo = false;
            deleteicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    TaskActivity.deleteNote(task_data.get(position));
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(task_data.get(position));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = (OnItemClickListener) listener;
    }

}