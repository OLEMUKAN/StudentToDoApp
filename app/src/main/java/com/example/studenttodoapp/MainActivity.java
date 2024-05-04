package com.example.studenttodoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studenttodoapp.adapter.TasksAdapter;
import com.example.studenttodoapp.databasehelper.DatabaseHelper;
import com.example.studenttodoapp.model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TasksAdapter.OnTaskClickListener {

    private RecyclerView recyclerView;
    private TasksAdapter tasksAdapter;
    private List<Task> taskList;
    private FloatingActionButton fabAddTask;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fabAddTask = findViewById(R.id.fab_add_task);  // Get the reference to the button

        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the TaskActivity to add a new task
                Intent intent = new Intent(MainActivity.this, TaskActivity.class);
                startActivity(intent);
            }
        });


        taskList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);

        loadTasks();
    }

    @SuppressLint("Range")
    private void loadTasks() {
        taskList.clear();

        try (Cursor cursor = databaseHelper.getAllTasks()) {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String dueDate = cursor.getString(cursor.getColumnIndex("dueDate"));
                    String priority = cursor.getString(cursor.getColumnIndex("priority"));
                    boolean isCompleted = cursor.getInt(cursor.getColumnIndex("isCompleted")) == 1;
                    taskList.add(new Task(id, name, dueDate, priority, isCompleted));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        tasksAdapter = new TasksAdapter(taskList, this);
        recyclerView.setAdapter(tasksAdapter);
    }

    @Override
    public void onEditTaskClick(Task task) {
        // Create an AlertDialog to edit the task name
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Task");

        // Set up the input
        final EditText input = new EditText(this);
        input.setText(task.getName());
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newTaskName = String.valueOf(input.getText());
                task.setName(newTaskName);
                databaseHelper.updateTask(task);
                loadTasks();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public void onDeleteTaskClick(Task task) {
        // Create an AlertDialog to confirm the delete action
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Task");
        builder.setMessage("Are you sure you want to delete this task?");

        // Set up the buttons
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseHelper.deleteTask(task.getId());
                loadTasks();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}