package com.alexilyin.android.planyourchallenge.m.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.alexilyin.android.planyourchallenge.m.DBContract;

/**
 * Created by user on 27.03.16.
 */
public class Task {

    public long _id;
    public String title;
    public Boolean isDone;

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBContract.TaskTable.COLUMN_NAME_TITLE, title);
        contentValues.put(DBContract.TaskTable.COLUMN_NAME_IS_DONE, (isDone) ? 1 : 0);
        return contentValues;
    }

}
