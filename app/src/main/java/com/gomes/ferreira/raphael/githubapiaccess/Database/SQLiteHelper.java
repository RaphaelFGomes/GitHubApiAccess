package com.gomes.ferreira.raphael.githubapiaccess.Database;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "repositoriesMySQLite.db";
    public static final String REPOSITORY_TABLE_NAME = "repositories";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("create table " + REPOSITORY_TABLE_NAME + " (json text, page text)");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }
}
