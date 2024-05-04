package com.example.studenttodoapp.databasehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.studenttodoapp.model.Task;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tasksDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DUE_DATE = "dueDate";
    private static final String COLUMN_PRIORITY = "priority";
    private static final String COLUMN_IS_COMPLETED = "isCompleted";

    private static DatabaseHelper instance;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DUE_DATE + " TEXT,"
                + COLUMN_PRIORITY + " TEXT,"
                + COLUMN_IS_COMPLETED + " INTEGER)";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, task.getName());
            values.put(COLUMN_DUE_DATE, task.getDueDate());
            values.put(COLUMN_PRIORITY, task.getPriority());
            values.put(COLUMN_IS_COMPLETED, task.isCompleted()? 1 : 0);

            db.insert(TABLE_TASKS, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cursor getAllTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TASKS, null);
    }

    public void updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, task.getName());
            values.put(COLUMN_DUE_DATE, task.getDueDate());
            values.put(COLUMN_PRIORITY, task.getPriority());
            values.put(COLUMN_IS_COMPLETED, task.isCompleted()? 1 : 0);

            db.update(TABLE_TASKS, values, COLUMN_ID + " =?", new String[]{String.valueOf(task.getId())});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_TASKS, COLUMN_ID + " =?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}