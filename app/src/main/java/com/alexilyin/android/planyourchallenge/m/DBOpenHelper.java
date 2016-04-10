package com.alexilyin.android.planyourchallenge.m;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 28.03.16.
 */
class DBOpenHelper extends SQLiteOpenHelper {

    private static DBOpenHelper instance;
    private static Context context;

    private DBOpenHelper(Context context) {
        super(context, DBContract.DB_NAME, null, DBContract.DB_VERSION);
        DBOpenHelper.context = context;
    }

    public static DBOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBOpenHelper(context);
        }
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
}
