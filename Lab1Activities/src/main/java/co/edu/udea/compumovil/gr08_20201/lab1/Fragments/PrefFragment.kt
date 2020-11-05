package co.edu.udea.compumovil.gr08_20201.lab1.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import co.edu.udea.compumovil.gr08_20201.lab1.Activities.LoginActivity
import co.edu.udea.compumovil.gr08_20201.lab1.R
import es.dmoral.toasty.Toasty

class PrefFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        //super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences)
        var switch: SwitchPreference? = findPreference<SwitchPreference>("switchPreference1")
        var pref: Preference? =findPreference<Preference>("cerrar")

        pref?.setOnPreferenceClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent)
            true
        }
    }
}