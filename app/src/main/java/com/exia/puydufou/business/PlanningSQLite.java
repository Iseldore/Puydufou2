package com.exia.puydufou.business;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.exia.puydufou.data.SQLiteCommunicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iseldore on 17/06/2015.
 */
public class PlanningSQLite {
    private SQLiteCommunicator sql;

    public PlanningSQLite(Context context){
        sql = new SQLiteCommunicator(context);
    }

    public void insert(String nom, Integer position) {
        SQLiteDatabase db = sql.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nom_spectacle", nom);
        values.put("position", position);

        db.insert("planning_stockage", null, values);
        db.close(); // Closing database connection
    }

    public List<String> getPlanning() {
        SQLiteDatabase db = sql.getReadableDatabase();
        String selectQuery =  "SELECT nom_spectacle FROM planning_stockage ORDER BY position ASC";

        List<String> planning = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                planning.add(cursor.getString(cursor.getColumnIndex("nom_spectacle")));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return planning;
    }

    public void supprimerTable(){

    }

    public void viderTable(){
        SQLiteDatabase db = sql.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS planning_stockage");
        db.execSQL("CREATE TABLE planning_stockage (id INTEGER PRIMARY KEY AUTOINCREMENT, nom_spectacle TEXT, position INTEGER)");
        db.close();
    }

}
