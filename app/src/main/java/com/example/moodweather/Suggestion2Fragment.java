package com.example.moodweather;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class Suggestion2Fragment extends Fragment {

    private SuggestionNavigator navigator;
    private SuggestionViewModel viewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SuggestionNavigator) {
            navigator = (SuggestionNavigator) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SuggestionNavigator");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SuggestionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggestion2, container, false);

        Button btnJoy = view.findViewById(R.id.btnJoy);
        Button btnCalm = view.findViewById(R.id.btnCalm);
        Button btnContent = view.findViewById(R.id.btnContent);
        Button btnPride = view.findViewById(R.id.btnPride);
        Button btnHope = view.findViewById(R.id.btnHope);
        Button btnLove = view.findViewById(R.id.btnLove);
        Button btnGratitude = view.findViewById(R.id.btnGratitude);

        View.OnClickListener listener = v -> {
            viewModel.suggestion234Choice = ((Button) v).getText().toString();
            navigator.navigateToSuggestion6();
        };

        btnJoy.setOnClickListener(listener);
        btnCalm.setOnClickListener(listener);
        btnContent.setOnClickListener(listener);
        btnPride.setOnClickListener(listener);
        btnHope.setOnClickListener(listener);
        btnLove.setOnClickListener(listener);
        btnGratitude.setOnClickListener(listener);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navigator = null;
    }
}
