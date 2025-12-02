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

public class Suggestion3Fragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_suggestion3, container, false);

        Button btnNeutral = view.findViewById(R.id.btnNeutral);
        Button btnBored = view.findViewById(R.id.btnBored);
        Button btnNumb = view.findViewById(R.id.btnNumb);
        Button btnSurprise = view.findViewById(R.id.btnSurprise);
        Button btnCuriosity = view.findViewById(R.id.btnCuriosity);
        Button btnMixed = view.findViewById(R.id.btnMixed);

        View.OnClickListener listener = v -> {
            viewModel.suggestion234Choice = ((Button) v).getText().toString();
            navigator.navigateToSuggestion5();
        };

        btnNeutral.setOnClickListener(listener);
        btnBored.setOnClickListener(listener);
        btnNumb.setOnClickListener(listener);
        btnSurprise.setOnClickListener(listener);
        btnCuriosity.setOnClickListener(listener);
        btnMixed.setOnClickListener(listener);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navigator = null;
    }
}
