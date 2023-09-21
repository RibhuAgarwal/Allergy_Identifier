package com.example.allergyidentifier;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "demo_db";
    private static final int DATABASE_VERSION = 1;

    //constructor
    //It automatically create database
    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE register(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,email TEXT ,password TEXT,gender TEXT,allergens TEXT )";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop means delete
        db.execSQL("DROP  TABLE IF EXISTS register ");
        onCreate(db);
    }


    public boolean registerUserHelper(String name1, String email1, String password1, String gender1, String allergens1) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name1);
        contentValues.put("email", email1);
        contentValues.put("password", password1);
        contentValues.put("gender", gender1);
        contentValues.put("allergens", allergens1);

        long l = sqLiteDatabase.insert("register", "null", contentValues);
        sqLiteDatabase.close();
        if (l > 0) {
            return true;
        } else {
            return false;
        }

    }

    boolean loggin;

    public boolean login(String email1, String pass1) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT*FROM register WHERE email='" + email1 + "'AND password='" + pass1 + "'", null);
        if (cursor.moveToFirst()) {
            loggin = true;
        } else {
            loggin = false;

        }
        return loggin;
    }

    public ArrayList <UserModel>getLoggedDetails(String email1){
        ArrayList<UserModel>al=new ArrayList<>();

        SQLiteDatabase sqLiteDatabase= this.getReadableDatabase();
        String query="SELECT*FROM register WHERE email='" + email1 + "'";
        Cursor cursor= sqLiteDatabase.rawQuery(query,null);

        if (cursor.moveToFirst()){
            String name = cursor.getString(1);
            String email = cursor.getString(2);
            String gender = cursor.getString(4);
            String allergen = cursor.getString(5);

            UserModel userModel=new UserModel();
            userModel.setName(name);
            userModel.setEmail(email);
            userModel.setGender(gender);
            userModel.setAllergen(allergen);
            al.add(userModel);
        }
        return al;
    }
}

