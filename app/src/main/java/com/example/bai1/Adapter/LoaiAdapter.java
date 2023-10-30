package com.example.bai1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bai1.Database.DAOLoai;
import com.example.bai1.R;
import com.example.bai1.model.Loai;

import java.util.ArrayList;

public class LoaiAdapter extends RecyclerView.Adapter<LoaiAdapter.ViewHolder>{
    private ArrayList<Loai> list;
    private Context context;
    private int trangThai;
    DAOLoai daoLoai;

    public LoaiAdapter(ArrayList<Loai> list, Context context, int trangThai, DAOLoai daoLoai) {
        this.list = list;
        this.context = context;
        this.trangThai = trangThai;
        this.daoLoai = daoLoai;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loai, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.txtMaLoai.setText(list.get(position).getIdLoai() + "");
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
            showDailogXoa(list.get(holder.getAdapterPosition()).getIdLoai());
        }
    });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
    TextView txtMaLoai, txtTenLoai;
    Button btnXoa, btnSua;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaLoai = itemView.findViewById(R.id.txtMaLoai);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            btnXoa = itemView.findViewById(R.id.btnXoa);
            btnSua = itemView.findViewById(R.id.btnSua);
        }
    }
    private void showDailogSua(Loai loai){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dailog_add, null);

        builder.setView(view);

        AlertDialog alertDialog = builder.create();

        alertDialog.setCancelable(false);
        alertDialog.show();
        //Ánh xạ
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        EditText etName = view.findViewById(R.id.etName);
        Button btnSua = view.findViewById(R.id.btnThem);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        if(trangThai == 0 ){
            txtTitle.setText("CHỈNH SỬA LOẠI THU");
            btnSua.setText("Sửa");
        }else{
            txtTitle.setText("CHỈNH SỬA LOẠI CHI");
            btnSua.setText("Sửa");
        }
        etName.setText(loai.getTenLoai());
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                boolean check = daoLoai.chinhSuaLoai(loai.getIdLoai(), name);
                if(check){
                    if (name.isEmpty()){
                        Toast.makeText(context, "Nhập dữ liệu", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(context, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                    alertDialog.cancel();
                    loadData();
                    //Load danh sách

                }else{
                    Toast.makeText(context, "Chỉnh sửa thất bại", Toast.LENGTH_SHORT).show();
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
    private void loadData(){
        list = daoLoai.getDS(trangThai);
        notifyDataSetChanged(); //cập nhât dữ liệu mới
    }
    private void showDailogXoa(int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo!");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setIcon(R.drawable.warning);

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //-1:đụng khóa, 0: không xóa được, 1: xóa được
                int check = daoLoai.xoaLoai(id);
                switch (check){
                    case -1:
                        Toast.makeText(context, "Dính KHOẢN: Thu & Chi", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        break;
                }
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
