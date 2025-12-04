package com.example.moodweather;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Arrays;
import java.util.List;

public class Suggestion7Fragment extends Fragment {

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggestion7, container, false);

        CheckBox chkSustainedBalance = view.findViewById(R.id.chkSustainedBalance);
        CheckBox chkRepetitiveActions = view.findViewById(R.id.chkRepetitiveActions);
        CheckBox chkDiminishingReturns = view.findViewById(R.id.chkDiminishingReturns);
        CheckBox chkPoliteInteraction = view.findViewById(R.id.chkPoliteInteraction);
        CheckBox chkStableProtection = view.findViewById(R.id.chkStableProtection);
        CheckBox chkUnproductiveSolitude = view.findViewById(R.id.chkUnproductiveSolitude);
        CheckBox chkRigidSelfAwareness = view.findViewById(R.id.chkRigidSelfAwareness);
        CheckBox chkInertialComfort = view.findViewById(R.id.chkInertialComfort);

        List<CheckBox> checkBoxes = Arrays.asList(
                chkSustainedBalance, chkRepetitiveActions, chkDiminishingReturns,
                chkPoliteInteraction, chkStableProtection, chkUnproductiveSolitude,
                chkRigidSelfAwareness, chkInertialComfort
        );

        setupCheckboxGroup(checkBoxes);

        Button btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> {
            viewModel.clearSuggestion5();

            for (CheckBox checkBox : checkBoxes) {
                if (checkBox.isChecked()) {
                    viewModel.suggestion5Choices.add(checkBox.getText().toString());
                    break; // Since it's single choice
                }
            }

            viewModel.suggestion5Details = ""; // Clear details

            navigator.navigateToResult();
        });

        return view;
    }

    private void setupCheckboxGroup(List<CheckBox> checkBoxes) {
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setOnClickListener(v -> {
                for (CheckBox cb : checkBoxes) {
                    cb.setChecked(false);
                }
                checkBox.setChecked(true);
            });
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navigator = null;
    }
}
