package com.example.studenttodoapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studenttodoapp.databasehelper.DatabaseHelper;
import com.example.studenttodoapp.model.Task;

import java.util.Calendar;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studenttodoapp.databasehelper.DatabaseHelper;
import com.example.studenttodoapp.model.Task;

import java.util.Calendar;

public class TaskActivity extends AppCompatActivity {
    private EditText editTextName, editTextDueDate;
    private CheckBox checkBoxCompleted;
    private Button btnSave;
    private Spinner spinnerPriority;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_form);

        editTextName = findViewById(R.id.editText_task_name);
        editTextDueDate = findViewById(R.id.editText_due_date);
        checkBoxCompleted = findViewById(R.id.checkBox_complete);
        btnSave = findViewById(R.id.button_save_task);
        spinnerPriority = findViewById(R.id.spinner_priority);

        databaseHelper = DatabaseHelper.getInstance(this);

        editTextDueDate.setOnClickListener(v -> showDatePickerDialog());
        btnSave.setOnClickListener(v -> saveTask());
    }

    private void showDatePickerDialog() {
        Calendar cal = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            // Set the due date EditText to the chosen date
            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            editTextDueDate.setText(selectedDate);
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void saveTask() {
        String name = editTextName.getText().toString();
        String dueDate = editTextDueDate.getText().toString();
        String priority = spinnerPriority.getSelectedItem().toString();
        boolean isCompleted = checkBoxCompleted.isChecked();

        if (name.isEmpty() || dueDate.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Task task = new Task(0, name, dueDate, priority, isCompleted);
        databaseHelper.addTask(task);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Task saved successfully", Toast.LENGTH_SHORT).show();
        finish(); // Close the activity and return to MainActivity
    }
}