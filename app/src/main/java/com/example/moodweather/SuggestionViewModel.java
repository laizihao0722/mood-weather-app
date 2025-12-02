package com.example.moodweather;

import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class SuggestionViewModel extends ViewModel {
    public String suggestion1Choice;
    public String suggestion234Choice;
    public final List<String> suggestion5Choices = new ArrayList<>();
    public String suggestion5Details;

    public void clearSuggestion5() {
        suggestion5Choices.clear();
        suggestion5Details = null;
    }
}
