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

public class Suggestion5Fragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_suggestion5, container, false);

        CheckBox chkWorkStress = view.findViewById(R.id.chkWorkStress);
        CheckBox chkFailure = view.findViewById(R.id.chkFailure);
        CheckBox chkConflict = view.findViewById(R.id.chkConflict);
        CheckBox chkRelationship = view.findViewById(R.id.chkRelationship);
        CheckBox chkLoneliness = view.findViewById(R.id.chkLoneliness);
        CheckBox chkUncertainty = view.findViewById(R.id.chkUncertainty);
        CheckBox chkSelfDoubt = view.findViewById(R.id.chkSelfDoubt);
        CheckBox chkPhysicalState = view.findViewById(R.id.chkPhysicalState);

        List<CheckBox> checkBoxes = Arrays.asList(
                chkWorkStress, chkFailure, chkConflict, chkRelationship,
                chkLoneliness, chkUncertainty, chkSelfDoubt, chkPhysicalState
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
