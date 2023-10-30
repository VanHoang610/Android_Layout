package com.example.bai1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bai1.model.Loai;

import java.util.ArrayList;

public class CustomSpinerAdapter extends BaseAdapter {
    Context context;
    ArrayList<Loai> list;

    public CustomSpinerAdapter(Context context, ArrayList<Loai> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        view = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);

        TextView txtName = view.findViewById(android.R.id.text1);
        txtName.setText(list.get(i).getTenLoai());
        return view;
    }
}
