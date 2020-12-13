package co.edu.udea.compumovil.gr08_20201.lab2.Repositories

import androidx.lifecycle.LiveData
import co.edu.udea.compumovil.gr08_20201.lab2.Dao.UserDao
import co.edu.udea.compumovil.gr08_20201.lab2.Entities.User

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()
    lateinit var readUser: LiveData<User>
    val count: LiveData<Int> = userDao.countUser()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }
   fun findUserBy(email: String): User {
       return userDao.findUserBy(email)
    }

    fun findUserByEmail(userName: String): LiveData<User> {
        return userDao.findUserByEmail(userName)
    }

}