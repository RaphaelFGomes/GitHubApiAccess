package com.gomes.ferreira.raphael.githubapiaccess.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.gomes.ferreira.raphael.githubapiaccess.Model.Repository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DBAdapter {
    public static final String REPOSITORY_TABLE_NAME = "repositories";
    public static final String REPOSITORY_COLUMN_JSON = "json";
    public static final String REPOSITORY_COLUMN_PAGE = "page";
    private SQLiteHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBAdapter(Context c) {
        context = c;
    }

    public DBAdapter open() throws SQLException {
        dbHelper = new SQLiteHelper(context);
        database = dbHelper.getWritableDatabase();
        dbHelper.onCreate(database);
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insertRepositories (List<Repository> repoList, int page) {
        boolean result = false;

        try {
            Gson gson = new Gson();
            String jsonInString = gson.toJson(repoList);
            ContentValues contentValues = new ContentValues();
            contentValues.put(REPOSITORY_COLUMN_JSON, jsonInString);
            contentValues.put(REPOSITORY_COLUMN_PAGE, Integer.toString(page));
            database.insert(REPOSITORY_TABLE_NAME, null, contentValues);
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public List<Repository> getAllRepositoriesByPage(int page) {
        List<Repository> repoList = new ArrayList<>();
        try {
            Cursor res = database.rawQuery("select * from repositories where page = " + 1, null);
            res.moveToFirst();

            if ((res != null) && (res.getCount() > 0)) {
                String jsonString = res.getString(res.getColumnIndex("json"));
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Repository>>() {
                }.getType();
                repoList = gson.fromJson(jsonString, listType);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return repoList;
    }

    public void clearDatabase() {
        String clearDBQuery = "DELETE FROM " + REPOSITORY_TABLE_NAME;
        database.execSQL(clearDBQuery);
    }
}
