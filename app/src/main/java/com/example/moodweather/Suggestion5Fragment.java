package com.example.moodweather;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

        EditText edtTriggerDetail = view.findViewById(R.id.edtTriggerDetail);

        Button btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> {
            viewModel.clearSuggestion5();

            if (chkWorkStress.isChecked()) {
                viewModel.suggestion5Choices.add(chkWorkStress.getText().toString());
            }
            if (chkFailure.isChecked()) {
                viewModel.suggestion5Choices.add(chkFailure.getText().toString());
            }
            if (chkConflict.isChecked()) {
                viewModel.suggestion5Choices.add(chkConflict.getText().toString());
            }
            if (chkRelationship.isChecked()) {
                viewModel.suggestion5Choices.add(chkRelationship.getText().toString());
            }
            if (chkLoneliness.isChecked()) {
                viewModel.suggestion5Choices.add(chkLoneliness.getText().toString());
            }
            if (chkUncertainty.isChecked()) {
                viewModel.suggestion5Choices.add(chkUncertainty.getText().toString());
            }
            if (chkSelfDoubt.isChecked()) {
                viewModel.suggestion5Choices.add(chkSelfDoubt.getText().toString());
            }
            if (chkPhysicalState.isChecked()) {
                viewModel.suggestion5Choices.add(chkPhysicalState.getText().toString());
            }

            viewModel.suggestion5Details = edtTriggerDetail.getText().toString();

            navigator.navigateToResult();
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navigator = null;
    }
}
