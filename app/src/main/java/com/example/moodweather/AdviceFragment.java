package com.example.moodweather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class AdviceFragment extends Fragment {

    public AdviceFragment() {
        // 必需的空构造函数
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 加载空的布局文件
        return inflater.inflate(R.layout.fragment_advice, container, false);
    }
}