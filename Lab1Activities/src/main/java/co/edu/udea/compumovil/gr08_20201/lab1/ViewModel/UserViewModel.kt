package co.edu.udea.compumovil.gr08_20201.lab1.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import co.edu.udea.compumovil.gr08_20201.lab1.Database.AppDatabase
import co.edu.udea.compumovil.gr08_20201.lab1.Entities.User
import co.edu.udea.compumovil.gr08_20201.lab1.Repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<User>>
    var userL = MutableLiveData<User>()
    val count: LiveData<Int>
    private val repository: UserRepository

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
        count= repository.count
    }

    fun findUserByEmail(email: String): LiveData<User> {
        return repository.findUserByEmail(email)
    }

   fun findUserBy(email: String): User {
      lateinit var user:User
       viewModelScope.launch(Dispatchers.IO) {
           user= repository.findUserBy(email)
       }
        return user
    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }

    }







}