package co.edu.udea.compumovil.gr08_20201.lab1.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.edu.udea.compumovil.gr08_20201.lab1.Entities.POI
import co.edu.udea.compumovil.gr08_20201.lab1.Entities.User
import co.edu.udea.compumovil.gr08_20201.lab1.R
import co.edu.udea.compumovil.gr08_20201.lab1.ViewModel.UserViewModel
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mUserViewModel: UserViewModel
    var userP: ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        this.setTitle(R.string.actIngreso)

        registroButton.setOnClickListener {
            val intent: Intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val userObserver = Observer<List<User>> {
            for (user in it) {
                userP.add(user)
            }
        }

        mUserViewModel.readAllData.observe(this, userObserver)

        loginButton.setOnClickListener {

            mUserViewModel.count.observe(this, Observer { cont->
                if(cont==0){
                   Toasty.info(this,"No hay usuarios registrados",Toast.LENGTH_SHORT ).show()
                }
                else{
                    val email = emailEditText.editText?.text.toString()
                    val password = passwordEditText.editText?.text.toString()
                    if(TextUtils.isEmpty(email)||TextUtils.isEmpty(email)) {
                        Toasty.info(this,"Ingrese todos los campos",Toast.LENGTH_SHORT)
                    }
                    else{
                        Log.d("email",email)
                        ingresar(email, password)
                    }

                }
            })



        }
    }

    private fun ingresar(email:String, password: String){
        var cont:Int=0
        for (i: Int in 0 until userP.size) {
            if (userP[i].email == email) {
                cont+=1
                if (userP[i].password == password) {
                    Toasty.success(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                    val intent: Intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    break
                } else {
                    Toasty.error(this, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show()
                    break
                }
            }
        }

        if(cont==0){
            Toasty.error(this, "Contraseña o Correo Incorrecto", Toast.LENGTH_SHORT).show()
        }

    }
}