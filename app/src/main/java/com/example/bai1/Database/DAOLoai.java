package com.example.bai1.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bai1.model.Loai;

import java.util.ArrayList;

public class DAOLoai {
    private DbHelper dbHelper;

    public DAOLoai(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<Loai> getDS(int trangThai){
        ArrayList<Loai> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Loai Where trangThai =?", new String[]{String.valueOf(trangThai)});
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                list.add(new Loai(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean themLoai(String tenLoai, int trangThai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLoai", tenLoai);
        values.put("trangThai", trangThai);

        long check = sqLiteDatabase.insert("Loai", null, values);
        if(check == -1){
            return false;
        }return true;
    }

    public boolean chinhSuaLoai(int idLoai, String tenLoai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLoai", tenLoai);
        long check = sqLiteDatabase.update("Loai", values, "idLoai = ?", new String[]{String.valueOf(idLoai)});
        if(check == -1){
            return false;
        }return true;
    }

    public int xoaLoai(int idLoai){
        //1: thành công, 0: thất bại, -1: ràng buộc khóa ngoại
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Khoan WHERE idLoai = ?", new String[]{String.valueOf(idLoai)});
        if(cursor.getCount() > 0 ){
            // có dính ràng buộc
            return -1;
        }else{
            long check = sqLiteDatabase.delete("Loai", "idLoai = ?", new String[]{String.valueOf(idLoai)});
            if(check == -1){
                return 0;
            }return 1;
        }
    }
}
