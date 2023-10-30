package com.example.bai1.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, "QLThuChi", null , 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String qLoai = "Create table Loai(" +
                    "idLoai integer primary key autoincrement," +
                "tenLoai text, " +
                "trangThai integer)";
        sqLiteDatabase.execSQL(qLoai);

        String qKhoan = "Create table Khoan(" +
                "idKhoan integer primary key autoincrement," +
                "tenKhoan text," +
                "tien integer, " +
                "ngay text," +
                "idLoai integer references Loai(idLoai))";
        sqLiteDatabase.execSQL(qKhoan);

        String dLoai = "INSERT INTO Loai values(1, 'tiền thưởng', 0), (2, 'tiền xăng', 1)";
        sqLiteDatabase.execSQL(dLoai);
        //data mẫu
        String dKhoan = "INSERT INTO Khoan values(1, 'tiền lương', 5000, '22/03/2023', 1)";
        sqLiteDatabase.execSQL(dKhoan);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i != i1){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Loai");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Khoan");
            onCreate(sqLiteDatabase);
        }
    }
}
