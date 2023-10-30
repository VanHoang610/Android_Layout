package com.example.bai1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bai1.Database.DAOKhoan;
import com.example.bai1.Database.DAOLoai;
import com.example.bai1.R;
import com.example.bai1.model.Khoan;
import com.example.bai1.model.Loai;

import java.util.ArrayList;

public class KhoanAdapter extends RecyclerView.Adapter<KhoanAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Khoan> list;
    private int trangthai;
    private DAOKhoan daoKhoan;
    private ArrayList<Loai> listLoai;
    private int idLoai = -1;
    AlertDialog alertDialog;



    public KhoanAdapter(Context context, ArrayList<Khoan> list, int trangthai, DAOKhoan daoKhoan) {
        this.context = context;
        this.list = list;
        this.trangthai = trangthai;
        this.daoKhoan = daoKhoan;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_khoan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaKhoan.setText(list.get(position).getIdKhoan()+ "");
        holder.txtTenKhoan.setText(list.get(position).getTenKhoan());
        holder.txtTien.setText(list.get(position).getTien() + "");
        holder.txtNgay.setText(list.get(position).getNgay());
        holder.txtTenLoai.setText(list.get(position).getTenLoai());

        holder.btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDailogSua(list.get(holder.getAdapterPosition()));
            }
        });
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDailogXoa(list.get(holder.getAdapterPosition()).getIdKhoan());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaKhoan, txtTenKhoan, txtTien, txtNgay, txtTenLoai;
        Button btnSua, btnXoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaKhoan = itemView.findViewById(R.id.txtMaKhoan);
            txtTenKhoan = itemView.findViewById(R.id.txtTenKhoan);
            txtTien = itemView.findViewById(R.id.txtTien);
            txtNgay = itemView.findViewById(R.id.txtNgay);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            btnSua = itemView.findViewById(R.id.btnSua);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }
    private void showDailogXoa(int idKhoan){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        Toast.makeText(context, "xóa", Toast.LENGTH_SHORT).show();
        builder.setTitle("Thông báo!");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setIcon(R.drawable.warning);



        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean check = daoKhoan.xoaKhoan(idKhoan);
                if(check){
                    Toast.makeText(context, "Xóa Khoản thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                }else{
                    Toast.makeText(context, "Xóa Khoản thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.cancel();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();

    }
    private void showDailogSua(Khoan khoan){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dailog_khoan, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        //Ánh xạ
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        EditText etName = view.findViewById(R.id.etName);
        EditText etPrice = view.findViewById(R.id.etPrice);
        EditText etDay = view.findViewById(R.id.etDay);
        Spinner spnCate = view.findViewById(R.id.spnCate);
        Button btnSua = view.findViewById(R.id.btnSave);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        if(trangthai == 0){
            txtTitle.setText("SỬA KHOẢN THU");
            btnSua.setText("Sửa");
        }else{
            txtTitle.setText("SỬA KHOẢN CHI");
            btnSua.setText("Sửa");
        }
        etName.setText(khoan.getTenKhoan());
        etPrice.setText(khoan.getTien() + "");
        etDay.setText(khoan.getNgay());
        loadDataSpiner(spnCate);


        int position = -1;
        for(int i = 0; i < listLoai.size(); i++){
            if(listLoai.get(i).getIdLoai() == khoan.getIdLoai()){
                position = i;
                break;
            }
        }
        spnCate.setSelection(position);
        spnCate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idLoai = ((Loai) adapterView.getAdapter().getItem(i)).getIdLoai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                int price;
                String day = etDay.getText().toString();
                if (name.length() > 0 && day.length() > 0 && etPrice.getText().toString().length() > 0){
                    price = Integer.parseInt(etPrice.getText().toString());
                    Khoan khoan1 = new Khoan(name, price, day, idLoai);
                    boolean check = daoKhoan.suaKhoan(khoan1, khoan.getIdKhoan());
                    if(check){
                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        alertDialog.cancel();
                        loadData();
                    }else{
                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "vui lòng nhập đủ", Toast.LENGTH_SHORT).show();
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
    private void loadDataSpiner(Spinner spnCate){
        DAOLoai daoLoai = new DAOLoai(context);
        listLoai = daoLoai.getDS(trangthai);

        CustomSpinerAdapter adapter = new CustomSpinerAdapter(context, listLoai);
        spnCate.setAdapter(adapter);
    }
    private void loadData(){
        list = daoKhoan.getKhoan(trangthai);
        notifyDataSetChanged(); //cập nhât dữ liệu mới
    }
}
