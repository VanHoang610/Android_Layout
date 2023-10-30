package com.example.bai1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bai1.Adapter.LoaiAdapter;
import com.example.bai1.Database.DAOLoai;
import com.example.bai1.R;
import com.example.bai1.model.Loai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LoaiFragment extends Fragment {
    int trangthai;
    DAOLoai daoLoai;
    RecyclerView recyclerLoai;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_loai, container, false);
        recyclerLoai = view.findViewById(R.id.recyclerLoai);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd);

        //lấy trạng thái
            Bundle bundle = getArguments();
        trangthai = bundle.getInt("trangthai");

        daoLoai = new DAOLoai(getContext());
        loadData();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show dailog_add
                showDailogAdd();
            }
        });

        return view;
    }

    private void loadData(){
        ArrayList<Loai> list = daoLoai.getDS(trangthai);

        //adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerLoai.setLayoutManager(linearLayoutManager);
        LoaiAdapter adapter = new LoaiAdapter(list, getContext(), trangthai, daoLoai);
        recyclerLoai.setAdapter(adapter);
    }
    private void showDailogAdd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dailog_add, null);
        //Xử lý code trong dailogAdd
        TextView txtTitle  = view.findViewById(R.id.txtTitle);
        EditText etName = view.findViewById(R.id.etName);
        Button btnThem = view.findViewById(R.id.btnThem);
        Button btnHuy = view.findViewById(R.id.btnHuy);


        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        //thay đổi tiêu đề
        if(trangthai == 0){
            txtTitle.setText("THÊM LOẠI THU");
        }else{
            txtTitle.setText("THÊM LOẠI CHI");
        }


        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lấy nội dung người dùng nhập vào
                String name = etName.getText().toString();
                boolean check = daoLoai.themLoai(name, trangthai);
                if(check){
                    if(name.isEmpty()){
                        Toast.makeText(getContext(), "Nhập thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    //tắt dailog
                    alertDialog.cancel();
                    loadData();
                }else{
                    Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });


    }
}
