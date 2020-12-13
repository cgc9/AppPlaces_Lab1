package co.edu.udea.compumovil.gr08_20201.lab2.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import co.edu.udea.compumovil.gr08_20201.lab2.Fragments.RecyclerViewFragment
import co.edu.udea.compumovil.gr08_20201.lab2.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction()
        val fragment = RecyclerViewFragment()
        transaction.replace(R.id.mainFragment, fragment)
        transaction.commit()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return when (item.itemId){
            R.id.salirItem -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
           R.id.configItem->{
               //Toasty.info(this,"config",Toast.LENGTH_SHORT).show()
               val intent: Intent = Intent(this, Settings::class.java)
               startActivity(intent)
               true
           }
           else -> super.onOptionsItemSelected(item)
        }

    }



}