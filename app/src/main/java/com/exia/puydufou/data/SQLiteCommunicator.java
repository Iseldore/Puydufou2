package com.exia.puydufou.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Iseldore on 17/06/2015.
 */
public class SQLiteCommunicator extends SQLiteOpenHelper {
    //version number to upgrade database version

    private static final int DATABASE_VERSION = 5;

    // Database Name
    private static final String DATABASE_NAME = "planning_stockage.db";

    public SQLiteCommunicator(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table = "CREATE TABLE planning_stockage (id INTEGER PRIMARY KEY AUTOINCREMENT, nom_spectacle TEXT, position INTEGER)";
        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS planning_stockage");
        onCreate(db);
    }
}
