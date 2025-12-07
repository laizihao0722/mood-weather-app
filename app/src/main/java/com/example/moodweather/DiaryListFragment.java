package com.example.moodweather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DiaryListFragment extends Fragment {
    private HistoryViewModel historyViewModel;
    private RecyclerView recyclerView;
    private DiaryEntryAdapter adapter;
    private TextView tvEmptyState;
    private ImageButton btnBack;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化 ViewModel
        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 加载布局
        View view = inflater.inflate(R.layout.fragment_diary_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewDiaryEntries);
        tvEmptyState = view.findViewById(R.id.tvEmptyState);
        btnBack = view.findViewById(R.id.btnBack);

        // 初始化 RecyclerView
        adapter = new DiaryEntryAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        //返回监听
        btnBack.setOnClickListener(v -> {
            if (getParentFragmentManager().getBackStackEntryCount() > 0) {
                getParentFragmentManager().popBackStack();
            }
        });

        // 观察数据库中的所有日记条目
        historyViewModel.getAllEntries().observe(getViewLifecycleOwner(), moodEntries -> {
            // 更新适配器的数据
            adapter.setEntries(moodEntries);

            // 处理空状态显示
            if (moodEntries != null && !moodEntries.isEmpty()) {
                tvEmptyState.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                tvEmptyState.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });

        return view;
    }
}