package com.example.moodweather;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Suggestion2Fragment extends Fragment {

    private SuggestionNavigator navigator;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggestion2, container, false);

        view.findViewById(R.id.btnJoy).setOnClickListener(v -> navigator.navigateToSuggestion5());
        view.findViewById(R.id.btnCalm).setOnClickListener(v -> navigator.navigateToSuggestion5());
        view.findViewById(R.id.btnContent).setOnClickListener(v -> navigator.navigateToSuggestion5());
        view.findViewById(R.id.btnPride).setOnClickListener(v -> navigator.navigateToSuggestion5());
        view.findViewById(R.id.btnHope).setOnClickListener(v -> navigator.navigateToSuggestion5());
        view.findViewById(R.id.btnLove).setOnClickListener(v -> navigator.navigateToSuggestion5());
        view.findViewById(R.id.btnGratitude).setOnClickListener(v -> navigator.navigateToSuggestion5());

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navigator = null;
    }
}
