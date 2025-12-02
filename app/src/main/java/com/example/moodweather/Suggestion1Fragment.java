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

public class Suggestion1Fragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_suggestion1, container, false);

        Button btnPositive = view.findViewById(R.id.btnPositive);
        Button btnNeutral = view.findViewById(R.id.btnNeutral);
        Button btnNegative = view.findViewById(R.id.btnNegative);

        btnPositive.setOnClickListener(v -> {
            viewModel.suggestion1Choice = btnPositive.getText().toString();
            navigator.navigateToSuggestion2();
        });

        btnNeutral.setOnClickListener(v -> {
            viewModel.suggestion1Choice = btnNeutral.getText().toString();
            navigator.navigateToSuggestion3();
        });

        btnNegative.setOnClickListener(v -> {
            viewModel.suggestion1Choice = btnNegative.getText().toString();
            navigator.navigateToSuggestion4();
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navigator = null;
    }
}
