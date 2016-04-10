package com.alexilyin.android.planyourchallenge.m;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alexilyin.android.planyourchallenge.m.model.Task;

/**
 * Created by user on 27.03.16.
 */
public class DBHelper {

    private Context context;
    private static DBHelper instance;
    private static DBOpenHelper dbOpenHelper;

    private DBHelper(Context context) {
        dbOpenHelper = DBOpenHelper.getInstance(context);
        this.context = context;
    }

    public static DBHelper getInstance(Context context) {
        if (instance == null)
            instance = new DBHelper(context);
        return instance;
    }

    // CRUD for Task

    public void insertTask(Task task) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            db.insert(
                    DBContract.TaskTable.TABLE_NAME,
                    null,
                    DBContract.TaskTable.getContentValues(task));
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public Task queryTask(long id) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DBContract.TaskTable.TABLE_NAME,
                null,
                "_id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);

        if (cursor != null & cursor.getCount() == 1) {
            cursor.moveToFirst();
            return DBContract.TaskTable.getObject(cursor);
        } else {
            return null;
        }
    }

    public Cursor queryTaskCursor() {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DBContract.TaskTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        return cursor;
    }

    public void updateTask(Task task) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            db.update(
                    DBContract.TaskTable.TABLE_NAME,
                    DBContract.TaskTable.getContentValues(task),
                    "_id = ?",
                    new String[]{String.valueOf(task._id)});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void deleteTask(Task task) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            db.delete(
                    DBContract.TaskTable.TABLE_NAME,
                    "_id = ?",
                    new String[]{String.valueOf(task._id)});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

}
