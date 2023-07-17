package com.stevenusanto_202102330.projectindividu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBName = "project_individu.db";

    public DBHelper(Context context) {
        super(context, "project_individu.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table users(username TEXT primary key, password TEXT)");
        sqLiteDatabase.execSQL("create table pasien(no_antri TEXT primary key, nama TEXT, jeniskelamin TEXT, alamat TEXT, email TEXT)");
        sqLiteDatabase.execSQL("create table jadwal(poliklinik TEXT primary key, dokter TEXT, hari TEXT, jam TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists users");
        sqLiteDatabase.execSQL("drop table if exists pasien");
        sqLiteDatabase.execSQL("drop table if exists jadwal");
    }

    public Boolean insertData(String username, String password) {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);

        long result = db.insert("users", null, values);
        if (result ==0) return false;
        else
            return true;
    }

    public Boolean insertDataPasien (String no_antri,String nama, String jeniskelamin, String alamat, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("no_antri",no_antri);
        values.put("nama",nama);
        values.put("jeniskelamin", jeniskelamin);
        values.put("alamat", alamat);
        values.put("email", email);
        long result = db.insert("pasien", null,values);
        if (result==0) return false;
        else
            return true;
    }

    public Cursor tampilDataPsn(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from pasien", null);
        return cursor;
    }

    public boolean hapusDataPsn(String no_antri){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from pasien where no_antri=?", new String[]{no_antri});
        if (cursor.getCount()>0) {
            long result = db.delete("pasien", "no_antri =?", new String[]{no_antri});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }   else {
            return false;
        }
    }

    public Boolean editDataPsn (String no_antri,String nama, String jeniskelamin, String alamat, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nama",nama);
        values.put("jeniskelamin", jeniskelamin);
        values.put("alamat", alamat);
        values.put("email", email);
        Cursor cursor = db.rawQuery("Select * from pasien where no_antri=?", new String[]{no_antri});
        if (cursor.getCount()>0) {
            long result = db.update("pasien", values, "no_antri=?", new String[]{no_antri});
            if (result==1) {
                return false;
            } else {
                return true;
            }
        }   else {
            return false;
        }
    }

    public Boolean checkkodepasien (String no_antri){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from pasien where no_antri=?", new String[] {no_antri});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean insertDataJwl (String poliklinik,String dokter, String hari, String jam){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("poliklinik",poliklinik);
        values.put("dokter",dokter);
        values.put("hari", hari);
        values.put("jam", jam);
        long result = db.insert("jadwal", null,values);
        if (result==0) return false;
        else
            return true;
    }

    public Cursor tampilDataJwl(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from jadwal", null);
        return cursor;
    }

    public boolean hapusDataJwl(String poliklinik){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from jadwal where poliklinik=?", new String[]{poliklinik});
        if (cursor.getCount()>0) {
            long result = db.delete("jadwal", "poliklinik =?", new String[]{poliklinik});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }   else {
            return false;
        }
    }

    public Boolean editDataJwl (String poliklinik,String dokter, String hari, String jam){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("dokter",dokter);
        values.put("hari", hari);
        values.put("jam", jam);
        Cursor cursor = db.rawQuery("Select * from jadwal where poliklinik=?", new String[]{poliklinik});
        if (cursor.getCount()>0) {
            long result = db.update("jadwal", values, "poliklinik=?", new String[]{poliklinik});
            if (result==1) {
                return false;
            } else {
                return true;
            }
        }   else {
            return false;
        }
    }

    public Boolean checkkodejwl (String poliklinik){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from jadwal where poliklinik=?", new String[] {poliklinik});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    //check username function
    public Boolean checkusername(String username){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=?", new String[] {username});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    //check username Password function
    public Boolean checkusernamepassword    (String username, String password){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=? and password=?", new String[]{username, password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
}