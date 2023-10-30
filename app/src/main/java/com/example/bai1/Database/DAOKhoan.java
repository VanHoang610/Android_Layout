package com.example.bai1.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bai1.model.Khoan;

import java.util.ArrayList;

public class DAOKhoan {
    DbHelper helper;

    public DAOKhoan(Context context){
        helper = new DbHelper(context);
    }
    public ArrayList<Khoan> getKhoan(int trangThai){
//        trangthai 1:chi, 0: thu
        ArrayList<Khoan> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Loai WHERE trangThai = ?", new String[]{String.valueOf(trangThai)});
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                int idLoai = cursor.getInt(0);

                //lấy danh sách khoản
                Cursor cursorKhoan = sqLiteDatabase.rawQuery("SELECT k.*, tenLoai FROM Khoan k, Loai l WHERE k.idLoai = l.idLoai and k.idLoai = ?", new String[]{String.valueOf(idLoai)});
                if(cursorKhoan.getCount() > 0){
                    cursorKhoan.moveToFirst();
                    do{
                        list.add(new Khoan(cursorKhoan.getInt(0),
                                cursorKhoan.getString(1),
                                cursorKhoan.getInt(2),
                                cursorKhoan.getString(3),
                                cursorKhoan.getInt(4),
                                cursorKhoan.getString(5)));

                    }while (cursorKhoan.moveToNext());
                }
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean themKhoan(Khoan khoan){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenKhoan", khoan.getTenKhoan());
        values.put("tien", khoan.getTien());
        values.put("ngay", khoan.getNgay());
        values.put("idLoai", khoan.getIdLoai());

        long check = sqLiteDatabase.insert("Khoan", null, values);
        if(check == -1){
            return false;
        }return true;
    }

    public boolean suaKhoan(Khoan khoan, int idKhoanCanSua){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenKhoan", khoan.getTenKhoan());
        values.put("tien", khoan.getTien());
        values.put("ngay", khoan.getNgay());
        values.put("idLoai", khoan.getIdLoai());
        long check = sqLiteDatabase.update("Khoan", values, "idKhoan = ?", new String[]{String.valueOf(idKhoanCanSua)});
        if(check == -1){
            return false;
        }return true;
    }

    public boolean xoaKhoan(int idKhoan){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        long check = sqLiteDatabase.delete("Khoan", "idKhoan = ? ", new String[]{String.valueOf(idKhoan)});
        if(check == -1){
            return false;
        }return true;
    }
}
