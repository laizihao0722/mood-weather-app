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

public class Suggestion4Fragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_suggestion4, container, false);

        Button btnSad = view.findViewById(R.id.btnSad);
        Button btnAnger = view.findViewById(R.id.btnAnger);
        Button btnFear = view.findViewById(R.id.btnFear);
        Button btnAnxiety = view.findViewById(R.id.btnAnxiety);
        Button btnStress = view.findViewById(R.id.btnStress);
        Button btnDisgust = view.findViewById(R.id.btnDisgust);
        Button btnGuilt = view.findViewById(R.id.btnGuilt);
        Button btnEnvy = view.findViewById(R.id.btnEnvy);

        View.OnClickListener listener = v -> {
            viewModel.suggestion234Choice = ((Button) v).getText().toString();
            navigator.navigateToSuggestion5();
        };

        btnSad.setOnClickListener(listener);
        btnAnger.setOnClickListener(listener);
        btnFear.setOnClickListener(listener);
        btnAnxiety.setOnClickListener(listener);
        btnStress.setOnClickListener(listener);
        btnDisgust.setOnClickListener(listener);
        btnGuilt.setOnClickListener(listener);
        btnEnvy.setOnClickListener(listener);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navigator = null;
    }
}
