package com.kretek.android.warungq;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DataHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "warungq.db";
    private static final String TAG = DataHelper.class.getSimpleName();
    private static final int DB_VER = 1;
    static final String no = "no";
    static final String naBar = "nb";
    static final String naLap = "nl";
    private String q = "CREATE TABLE data(no INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nb TEXT NOT NULL, hrg TEXT NOT NULL, jb TEXT NOT NULL);";
    private String p = "CREATE TABLE laporan(no INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nl TEXT NOT NULL,jl TEXT NOT NULL, tot TEXT NOT NULL, dt DATETIME DEFAULT CURRENT_TIMESTAMP);";

    public DataHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(p);
        db.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0,int arg1,int arg2){
        Log.d(TAG, String.format("SQLiteDatabase.onUpgrade(%d -> %d)", arg1,arg2));

        arg0.execSQL("DROP TABLE IF EXISTS " + q);
        arg0.execSQL("DROP TABLE IF EXISTS " + p);

        onCreate(arg0);
    }
}
