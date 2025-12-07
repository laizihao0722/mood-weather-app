package com.example.moodweather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FeedbackFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        EditText etFeedback = view.findViewById(R.id.et_feedback_input);
        Button btnSubmit = view.findViewById(R.id.btn_submit_feedback);
        ImageButton btnBack = view.findViewById(R.id.btn_back_to_settings);

        btnBack.setOnClickListener(v -> {
            // 返回上一个 Fragment
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });

        btnSubmit.setOnClickListener(v -> {
            String feedback = etFeedback.getText().toString().trim();
            if (feedback.isEmpty()) {
                Toast.makeText(getContext(), "请输入反馈内容后再提交哦。", Toast.LENGTH_SHORT).show();
            } else {
                // 实际应用中：这里应该将 feedback 发送到服务器或发送邮件

                Toast.makeText(getContext(), "感谢您的宝贵意见！我们已收到。", Toast.LENGTH_LONG).show();
                etFeedback.setText(""); // 清空输入

                // 提交后返回设置页
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });




        return view;

    }
}