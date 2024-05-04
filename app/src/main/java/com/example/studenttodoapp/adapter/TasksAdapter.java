package com.example.studenttodoapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studenttodoapp.R;
import com.example.studenttodoapp.model.Task;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
    private List<Task> taskList;

    private OnTaskClickListener onTaskClickListener;

    public TasksAdapter(List<Task> taskList, OnTaskClickListener onTaskClickListener) {
        this.taskList = taskList;
        this.onTaskClickListener = onTaskClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (taskList!= null && taskList.size() > position) {
            Task task = taskList.get(position);
            holder.textViewTaskName.setText(task.getName());
            holder.textViewDueDate.setText(task.getDueDate());
            holder.textViewPriority.setText(task.getPriority());
            holder.checkBoxCompleted.setChecked(task.isCompleted());

            holder.buttonEditTask.setOnClickListener(v -> onTaskClickListener.onEditTaskClick(task));
            holder.buttonDeleteTask.setOnClickListener(v -> onTaskClickListener.onDeleteTaskClick(task));

        }
    }

    @Override
    public int getItemCount() {
        return taskList!= null? taskList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTaskName, textViewDueDate, textViewPriority;
        public CheckBox checkBoxCompleted;
        public ImageButton buttonEditTask, buttonDeleteTask;

        public ViewHolder(View view) {
            super(view);
            textViewTaskName = view.findViewById(R.id.textView_task_name);
            textViewDueDate = view.findViewById(R.id.textView_due_date);
            textViewPriority = view.findViewById(R.id.textView_priority);
            checkBoxCompleted = view.findViewById(R.id.checkBox_complete);
            buttonEditTask = view.findViewById(R.id.button_edit_task);
            buttonDeleteTask = view.findViewById(R.id.button_delete_task);
        }
    }

    public interface OnTaskClickListener {
        void onEditTaskClick(Task task);
        void onDeleteTaskClick(Task task);
    }
}