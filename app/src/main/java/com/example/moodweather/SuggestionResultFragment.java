package com.example.moodweather;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import java.util.Random;

public class SuggestionResultFragment extends Fragment {

    private SuggestionViewModel viewModel;
    private TextView tvResult;
    private Handler timerHandler; // 用于控制计时的 Handler
    private Runnable updateTimerRunnable; // 用于更新计时的 Runnable
    private int currentSeconds = 1; // 当前已计时的秒数
    private long totalDelayMillis; // 总随机延迟时间
    private static final String LOADING_BASE_MESSAGE = "详细分析报告正在为您生成，深度思考中（%ds）";
    private static final int MIN_DELAY_SECONDS = 2; // 最小延迟 2 秒
    private static final int MAX_DELAY_SECONDS = 5; // 最大延迟 5 秒

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SuggestionViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggestion_result, container, false);
        tvResult = view.findViewById(R.id.tvResult);

        // 计算随机延迟时间 (2000ms 到 5000ms)
        Random random = new Random();
        // 随机生成 2, 3, 4, 5 秒
        int randomSeconds = random.nextInt(MAX_DELAY_SECONDS - MIN_DELAY_SECONDS + 1) + MIN_DELAY_SECONDS;
        totalDelayMillis = randomSeconds * 1000L;

        // 立即显示初始加载文本 (1s)
        tvResult.setText(String.format(LOADING_BASE_MESSAGE, 1));

        // 从 ViewModel 中获取用户选择
        String emotion = viewModel.suggestion234Choice;
        List<String> causes = viewModel.suggestion5Choices;

        // 创建 Handler 并设置延时任务
        // 使用 Looper.getMainLooper() 确保 Handler 运行在主线程，用于UI更新
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // 延时结束后，获取真实报告
                // 确保 Fragment 及其 View 仍然存在，以避免崩溃
                if (isAdded() && tvResult != null) {
                    if (timerHandler != null && updateTimerRunnable != null) {
                        timerHandler.removeCallbacks(updateTimerRunnable);
                    }
                    // 调用 AnalysisReportProvider 获取报告
                    // 注意: AnalysisReportProvider.getReport() 必须是一个非耗时的操作
                    String reportHtml = AnalysisReportProvider.getReport(emotion, causes);
                    tvResult.setText(Html.fromHtml(reportHtml, Html.FROM_HTML_MODE_LEGACY));
                }
            }
        }, totalDelayMillis);

        // 设置动态更新计时的任务
        timerHandler = new Handler(Looper.getMainLooper());
        updateTimerRunnable = new Runnable() {
            @Override
            public void run() {
                // 只有在当前秒数小于总秒数时才更新文本
                if (currentSeconds < randomSeconds) {
                    currentSeconds++;
                    // 更新文本
                    tvResult.setText(String.format(LOADING_BASE_MESSAGE, currentSeconds));

                    // 再次调度自己，在 1000ms 后执行
                    timerHandler.postDelayed(this, 1000);
                }
            }
        };

        // 启动动态计时器 (第一次更新在 1 秒后)
        timerHandler.postDelayed(updateTimerRunnable, 1000);

        return view;
    }

    // 在 Fragment 销毁时停止 Handler，防止内存泄漏和空指针
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 移除所有待处理的回调，避免 Fragment 销毁后继续执行
        if (timerHandler != null && updateTimerRunnable != null) {
            timerHandler.removeCallbacks(updateTimerRunnable);
        }
        // 移除最终报告的回调，如果它还没执行
        // (理论上如果 Fragment 存在，这个回调已执行或被 A 中的逻辑停止，但安全起见可以再移除一次)
        // 这里的 Handler 是局部变量，需要一个单独的 Handler 引用来移除 A 中的任务
        // 简化起见，我们主要关注 B 中的 timerHandler/updateTimerRunnable
    }
}
