package com.alexilyin.android.planyourchallenge;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alexilyin.android.planyourchallenge.model.Task;

/**
 * Created by user on 27.03.16.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper instance;

    private DBHelper(Context context) {
        super(context, DBContract.DB_NAME, null, DBContract.DB_VERSION);
    }

    public static final DBHelper getInstance(Context context) {
        if (instance == null)
            instance = new DBHelper(context);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBContract.TaskTable.SQL_QUERY_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBContract.TaskTable.SQL_QUERY_DROP_TABLE);
        onCreate(db);
    }

    // CRUD

    public void insertTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();
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
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                DBContract.TaskTable.TABLE_NAME,
                null,
                "_id = ?",
                new String[] { String.valueOf(id) },
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

    public Cursor queryTaskListCursor() {
        SQLiteDatabase db = getReadableDatabase();
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
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            db.update(
                    DBContract.TaskTable.TABLE_NAME,
                    DBContract.TaskTable.getContentValues(task),
                    "_id = ?",
                    new String[]{ String.valueOf(task._id) });
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void deleteTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            db.delete(
                    DBContract.TaskTable.TABLE_NAME,
                    "_id = ?",
                    new String[]{ String.valueOf(task._id) });
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
}
