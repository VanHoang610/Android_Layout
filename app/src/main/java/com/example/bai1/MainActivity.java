package com.example.bai1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bai1.Database.DAOKhoan;
import com.example.bai1.Database.DAOLoai;
import com.example.bai1.model.Khoan;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    CheckBox chkRemember;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        chkRemember = findViewById(R.id.chkRemmber);
        login = findViewById(R.id.login);


        //Check giá trị isRemember đã lưu
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean("isRemember", false);
        if(check){
            username.setText(sharedPreferences.getString("user", ""));
            password.setText(sharedPreferences.getString("pass", ""));
            chkRemember.setChecked(check);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                
                if(user.length() > 0 && pass.length() > 0){
                    if(user.equals("levanhoang") && (pass.equals("fptPolytechnic"))){
                        //lấy giá trị của checkbox
                        boolean isRemember = chkRemember.isChecked();
                        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isRemember", isRemember);
                        editor.putString("user", user);
                        editor.putString("pass", pass);
                        editor.apply();
                        // qua màn hình tiếp theo
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                    
                }else{
                    Toast.makeText(MainActivity.this, "Nhập thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}