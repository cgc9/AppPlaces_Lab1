package co.edu.udea.compumovil.gr08_20201.lab1.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.edu.udea.compumovil.gr08_20201.lab1.Entities.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table WHERE email = :email")
    fun findUserByEmail(email:String) :LiveData<User>

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE email LIKE :email")
    fun findUserBy(email:String) :User

    @Query("SELECT COUNT (*) FROM user_table")
    fun countUser(): LiveData<Int>



}