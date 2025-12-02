package com.example.moodweather;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class SuggestionResultFragment extends Fragment {

    private SuggestionViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SuggestionViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggestion_result, container, false);
        TextView tvResult = view.findViewById(R.id.tvResult);

        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("你的选择是: ").append(viewModel.suggestion1Choice).append("\n");
        resultBuilder.append("具体情绪: ").append(viewModel.suggestion234Choice).append("\n");
        resultBuilder.append("诱因: ").append(TextUtils.join(", ", viewModel.suggestion5Choices)).append("\n");
        resultBuilder.append("具体情况: ").append(viewModel.suggestion5Details).append("\n");

        tvResult.setText(resultBuilder.toString());

        return view;
    }
}
