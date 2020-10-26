package co.edu.udea.compumovil.gr08_20201.lab1.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import co.edu.udea.compumovil.gr08_20201.lab1.R

class PrefFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        //super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences)
    }
}