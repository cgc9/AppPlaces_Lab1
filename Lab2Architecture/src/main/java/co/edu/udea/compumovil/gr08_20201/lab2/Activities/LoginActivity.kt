package co.edu.udea.compumovil.gr08_20201.lab2.Activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.edu.udea.compumovil.gr08_20201.lab2.Activities.MainActivity
import co.edu.udea.compumovil.gr08_20201.lab2.Entities.User
import co.edu.udea.compumovil.gr08_20201.lab2.R
import co.edu.udea.compumovil.gr08_20201.lab2.Service.PoiService
import co.edu.udea.compumovil.gr08_20201.lab2.Service.UserService
import co.edu.udea.compumovil.gr08_20201.lab2.ViewModel.UserViewModel
import es.dmoral.toasty.Toasty
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private var disposable: Disposable? = null
    var email: String = ""
    var password: String = ""
    private val userApiServe by lazy {
        UserService.create()
    }

    private lateinit var mUserViewModel: UserViewModel
    var userP: ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        this.setTitle(R.string.actIngreso)

        registroButton.setOnClickListener {

            val intent= Intent(this, RegisterActivity::class.java)
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
            mUserViewModel.count.observe(this, Observer { cont ->
                val email = emailEditText.editText?.text.toString()
                val password = passwordEditText.editText?.text.toString()
                if (cont == 0) {
                    getUser(email, password)
                    //Toasty.info(this, "No hay usuarios registrados", Toast.LENGTH_SHORT).show()
                } else {
                    //val email = emailEditText.editText?.text.toString()
                    //val password = passwordEditText.editText?.text.toString()
                    if (TextUtils.isEmpty(email) || TextUtils.isEmpty(email)) {
                        Toasty.info(this, "Ingrese todos los campos", Toast.LENGTH_SHORT)
                    } else {
                        getUsers(email, password)
                        // ingresar(email, password)
                    }

                }
           })

        }
    }

    private fun ingresar(email: String, password: String) {
        var cont: Int = 0
        for (i: Int in 0 until userP.size) {
            if (userP[i].email == email) {
                cont += 1
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

        if (cont == 0) {
            Toasty.error(this, "Contraseña o Correo Incorrecto", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getUser(email: String, password: String) {
        disposable = userApiServe.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    var userIn=false
                    for (i: Int in result.indices) {
                        if (result[i].email == email) {
                            userIn=true
                            if (result[i].password == password) {
                                Toasty.success(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                                val intent: Intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                break
                            } else {
                                Toasty.error(this, "Contraseña  Incorrecta", Toast.LENGTH_SHORT).show()
                                break
                            }
                        }
                    }

                    if(!userIn){
                            Toasty.error(this, "Correo o contraseña incorrecta", Toast.LENGTH_SHORT)
                                .show()
                    }
                },
                { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                    error.message?.let { Log.d("error", it) }
                }
            )
    }

    private fun getUsers(email: String, password: String) {
        var userExist = false
        disposable = userApiServe.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    for (i: Int in result.indices) {
                        if (result[i].email == email) {
                            if (result[i].password == password) {
                                Toasty.success(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                                val intent: Intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                userExist = true
                                break
                            } else {
                               // Toasty.error(this, "Contraseña Incorrecta", Toast.LENGTH_SHORT) .show()
                                break
                            }
                        }

                    }

                    if (!userExist) {
                        var userIn=false
                        for (i: Int in 0 until userP.size) {
                            if (userP[i].email == email) {
                                userIn=true
                                // cont+=1
                                if (userP[i].password == password) {
                                    userIn=true
                                    Toasty.success(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                                    val intent: Intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                    break
                                } else {
                                    userIn=true
                                    Toasty.error(this, "Contraseña Incorrecta", Toast.LENGTH_SHORT)
                                        .show()
                                    break
                                }
                            }
                        }
                        Log.d("booelan",userIn.toString())
                        if(!userIn){
                               Toasty.error(this, "Correo o contraseña incorrecta", Toast.LENGTH_SHORT)
                                    .show()
                        }
                    }

                    //  if(cont==0){
                    //    Toasty.error(this, "Contraseña o Correo Incorrecto", Toast.LENGTH_SHORT).show()
                    //}

                },
                { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                    error.message?.let { Log.d("error", it) }
                }
            )
    }


}
