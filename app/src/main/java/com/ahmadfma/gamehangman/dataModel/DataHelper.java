package com.ahmadfma.gamehangman.dataModel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "database_user.db";
    private static final int DATABASE_VERSION = 1;
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql1 = "create table score_FILM(id integer primary key, skor text null);";
        String sql2 = "create table score_SEJARAH(id integer primary key, skor text null);";
        String sql3 = "create table score_AKTOR(id integer primary key, skor text null);";
        String sql4 = "create table score_BENDA(id integer primary key, skor text null);";

        String sql5= "create table checkPoint_Level(id_category int primary key, skor text null);";

        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.execSQL(sql2);
        sqLiteDatabase.execSQL(sql3);
        sqLiteDatabase.execSQL(sql4);
        sqLiteDatabase.execSQL(sql5);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
