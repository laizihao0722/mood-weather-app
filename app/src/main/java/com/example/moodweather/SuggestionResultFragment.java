package com.example.moodweather;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import java.util.List;

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

        // 从 ViewModel 中获取用户选择
        String emotion = viewModel.suggestion234Choice;
        List<String> causes = viewModel.suggestion5Choices;

        // 调用 AnalysisReportProvider 获取报告
        String reportHtml = AnalysisReportProvider.getReport(emotion, causes);

        // 使用 Html.fromHtml() 来渲染 HTML 格式的报告
        tvResult.setText(Html.fromHtml(reportHtml, Html.FROM_HTML_MODE_LEGACY));

        return view;
    }
}
