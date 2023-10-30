package com.example.bai1.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.bai1.Fragment.KhoanFragment;
import com.example.bai1.Fragment.LoaiFragment;
import com.example.bai1.model.Khoan;

public class ViewPager2Adapter extends FragmentStateAdapter {
    private int trangthai;
    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("trangthai", trangthai);
        Fragment fragment;
        if(position == 0){
            fragment =  new LoaiFragment();
        }else{
            fragment =  new KhoanFragment();
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
    public void setTrangThai(int trangthai){
        this.trangthai = trangthai;
    }
}
