package co.edu.udea.compumovil.gr08_20201.lab2.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.edu.udea.compumovil.gr08_20201.lab2.Fragments.PrefFragment

import co.edu.udea.compumovil.gr08_20201.lab2.R

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setTitle(R.string.configuracion)
        setContentView(R.layout.activity_settings)
        supportFragmentManager.beginTransaction().replace(R.id.contSettings, PrefFragment()).commit()

    }
}