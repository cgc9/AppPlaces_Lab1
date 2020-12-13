package co.edu.udea.compumovil.gr08_20201.lab2.Activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import co.edu.udea.compumovil.gr08_20201.lab2.Entities.User
import co.edu.udea.compumovil.gr08_20201.lab2.R
import co.edu.udea.compumovil.gr08_20201.lab2.ViewModel.UserViewModel
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern


class RegisterActivity : AppCompatActivity() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        this.setTitle(R.string.registarse)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        registrarseButton.setOnClickListener {

            insertDataToDatabase()


        }

    }

    private fun insertDataToDatabase() {
        val name = nameEditText.editText?.text.toString()
        val lastName = lastNameEditText.editText?.text.toString()
        val email = rEmailEditText.editText?.text.toString()
        val password = rPasswordEditText.editText?.text.toString()

        if (inputCheck(name, lastName, email, password)) {
            if(validateEmail(email)){
                var user = User(0, name, lastName, email, password)
                mUserViewModel.addUser(user)
                Toasty.success(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                val intent: Intent = Intent(this, LoginActivity::class.java)
                   // .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
                finish()
            }else{
                Toasty.error(this, "Por favor ingrese un correo valido", Toast.LENGTH_LONG).show()
            }

        } else {
            Toasty.error(this, "Por favor ingresar todos los campos", Toast.LENGTH_LONG).show()

        }

    }

    private fun validateEmail(email: String):Boolean{
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    private fun inputCheck(
        name: String,
        lastName: String,
        email: String,
        password: String
    ): Boolean {

        return !(TextUtils.isEmpty(name) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(
            password
        ))

    }

}