package com.team.asuper.textdetector;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

public class MySettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        // Load the Preferences from the XML file
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

}
