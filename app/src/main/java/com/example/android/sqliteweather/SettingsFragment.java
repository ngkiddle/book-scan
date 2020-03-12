package com.example.android.sqliteweather;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.android.sqliteweather.data.ForecastLocation;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SavedCitiesViewModel mSavedCitiesViewModel;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.prefs);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditTextPreference locationPref = (EditTextPreference)findPreference(getString(R.string.pref_location_key));
        locationPref.setSummary(locationPref.getText());


        mSavedCitiesViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(
                        getActivity().getApplication()
                )
        ).get(SavedCitiesViewModel.class);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_location_key))) {
            EditTextPreference locationPref = (EditTextPreference)findPreference(key);
            locationPref.setSummary(locationPref.getText());
            mSavedCitiesViewModel.insertLocation(
                    new ForecastLocation(locationPref.getText())
            );
        }
    }

    @Override
    public void onResume() {
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }
}
