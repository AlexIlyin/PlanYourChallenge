package com.alexilyin.android.planyourchallenge;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.alexilyin.android.planyourchallenge.model.Task;

/**
 * Created by user on 27.03.16.
 */
public final class DBContract {

    private DBContract() {
    }

    public static final String DB_NAME = "default.db";
    public static final int DB_VERSION = 1;

    public static abstract class TaskTable implements BaseColumns {
        public static final String TABLE_NAME = "Task";

//        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_TITLE = "Title";
        public static final String COLUMN_NAME_IS_DONE = "IsDone";

        public static final String SQL_QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                COLUMN_NAME_TITLE + " TEXT NOT NULL ON CONFLICT ROLLBACK , " +
                COLUMN_NAME_IS_DONE + " INTEGER NOT NULL ON CONFLICT ROLLBACK " +
                ")";

        public static final String SQL_QUERY_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final ContentValues getContentValues(Task task) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME_TITLE, task.title);
            contentValues.put(COLUMN_NAME_IS_DONE, (task.isDone) ? 1 : 0);
            return contentValues;
        }

        public static final Task getObject(Cursor cursor) {
            int columnNoId = cursor.getColumnIndex(_ID);
            int columnNoTitle = cursor.getColumnIndex(COLUMN_NAME_TITLE);
            int columnNoIsDone = cursor.getColumnIndex(COLUMN_NAME_IS_DONE);

            Task task = new Task();
            task._id = cursor.getLong(columnNoId);
            task.title = cursor.getString(columnNoTitle);
            task.isDone = (cursor.getInt(columnNoIsDone) == 1) ? Boolean.TRUE : Boolean.FALSE;

            return task;
        }


    }

}
