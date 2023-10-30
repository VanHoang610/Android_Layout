package com.example.bai1.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bai1.Adapter.CustomSpinerAdapter;
import com.example.bai1.Adapter.KhoanAdapter;
import com.example.bai1.Database.DAOKhoan;
import com.example.bai1.Database.DAOLoai;
import com.example.bai1.R;
import com.example.bai1.model.Khoan;
import com.example.bai1.model.Loai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class KhoanFragment extends Fragment {
    RecyclerView recyclerKhoan;
    DAOKhoan daoKhoan;
    int trangthai;
    int idLoai = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_khoan, container, false);
        recyclerKhoan = view.findViewById(R.id.recyclerKhoan);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd);

        Bundle bundle = getArguments();
        trangthai = bundle.getInt("trangthai");
        daoKhoan = new DAOKhoan(getContext());
        loadData();
        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDailogAdd();
            }
        });

        return view;

    }
    private  void loadData(){
        ArrayList<Khoan> list = daoKhoan.getKhoan(trangthai);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerKhoan.setLayoutManager(linearLayoutManager);

        KhoanAdapter khoanAdapter = new KhoanAdapter(getContext(), list, trangthai, daoKhoan);
        recyclerKhoan.setAdapter(khoanAdapter);
    }
    private void showDailogAdd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dailog_khoan, null);
        builder.setView(view);

        TextView txtTitle = view.findViewById(R.id.txtTitle);
        EditText etName = view.findViewById(R.id.etName);
        EditText etPrice = view.findViewById(R.id.etPrice);
        EditText etDay = view.findViewById(R.id.etDay);
        Spinner spnCate = view.findViewById(R.id.spnCate);
        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        //set Tiêu đề
        if(trangthai == 0){
            txtTitle.setText("THÊM MỚI KHOẢN THU");
        }else{
            txtTitle.setText("THÊM MỚI KHOẢN CHI");
        }

        //nhất etDay show datepuckerdailog
        etDay.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                showDateDailog(etDay);
            }
        });

        //hiển thị loại trên spiner
        loadDataSpiner(spnCate);
        //lấy idLoai của Spiner
        spnCate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idLoai = ((Loai) adapterView.getAdapter().getItem(i)).getIdLoai();
                Toast.makeText(getContext(), "" + idLoai, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, day;
                int price;
                //lấy tất cả giá trị
                if(etName.getText().toString().length() > 0 && etPrice.getText().toString().length() > 0 && etDay.getText().toString().length() > 0){
                    name = etName.getText().toString();
                    price = Integer.parseInt(etPrice.getText().toString());
                    day = etDay.getText().toString();

                    Khoan khoan = new Khoan(name, price, day, idLoai);
                    boolean check = daoKhoan.themKhoan(khoan);
                    if(check){
                        if (name.isEmpty() && day.isEmpty()){
                            Toast.makeText(getContext(), "Nhập thông tin", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(getContext(), "Thêm Khoản thành công", Toast.LENGTH_SHORT).show();
                        alertDialog.cancel();
                        loadData();
                    }else{
                        Toast.makeText(getContext(), "Thêm Khoản thất bại", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Nhập thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
    }

    private void showDateDailog(EditText etDay){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        int month = i1 + 1;
                        etDay.setText(i2 + " / " + month + " / " + i);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    datePickerDialog.show();
    }
    private void loadDataSpiner(Spinner spnCate){
        DAOLoai daoLoai = new DAOLoai(getContext());
        ArrayList<Loai> list = daoLoai.getDS(trangthai);
        CustomSpinerAdapter adapter = new CustomSpinerAdapter(getContext(), list);
        spnCate.setAdapter(adapter);
    }
}
