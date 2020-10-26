package co.edu.udea.compumovil.gr08_20201.lab1.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import co.edu.udea.compumovil.gr08_20201.lab1.Entities.User
import co.edu.udea.compumovil.gr08_20201.lab1.R
import co.edu.udea.compumovil.gr08_20201.lab1.ViewModel.UserViewModel
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var  mUserViewModel:UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        this.setTitle(R.string.registarse)

        mUserViewModel= ViewModelProvider(this).get(UserViewModel::class.java)

        registrarseButton.setOnClickListener {

            insertDataToDatabase()

        }

    }

    private fun insertDataToDatabase() {
        val name=nameEditText.editText?.text.toString()
        val lastName= lastNameEditText.editText?.text.toString()
        val email= rEmailEditText.editText?.text.toString()
        val password= rPasswordEditText.editText?.text.toString()

        if(inputCheck(name,lastName, email,password)){
            var user= User(0,name,lastName, email,password)
            mUserViewModel.addUser(user)
            Toasty.success(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
            val intent: Intent = Intent(this, LoginActivity::class.java )
            startActivity(intent)
        }else{
            Toasty.error(this, "Por favor ingresar todos los campos", Toast.LENGTH_LONG).show()

        }

    }

    private fun inputCheck (name:String, lastName: String,email:String, password:String) : Boolean {

        return !(TextUtils.isEmpty(name)|| TextUtils.isEmpty(lastName)|| TextUtils.isEmpty(email)|| TextUtils.isEmpty(password))

    }

}