package com.example.bai1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bai1.Adapter.ViewPager2Adapter;
import com.example.bai1.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Thu_Chi_Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thu_chi, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager2);

        //Lấy giá trị của trạng thái
        Bundle bundle = getArguments();
        int trangthai = bundle.getInt("trangthai");


        ViewPager2Adapter adapter = new ViewPager2Adapter(getActivity());
        adapter.setTrangThai(trangthai);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 0){
                    if(trangthai == 0){
                        tab.setText("Loại thu");
                    }else{
                        tab.setText("Loại chi");
                    }
                }else{
                    if(trangthai == 0){
                        tab.setText("Khoản thu");
                    }else{
                        tab.setText("Khoản chi");
                    }
                }
            }
        }).attach();

        return view;
    }
}
