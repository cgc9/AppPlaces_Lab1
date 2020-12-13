package co.edu.udea.compumovil.gr08_20201.lab2.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.edu.udea.compumovil.gr08_20201.lab2.Entities.City
import co.edu.udea.compumovil.gr08_20201.lab2.Entities.Comment

@Dao
interface CommentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addComment(comment: Comment)

    @Query("SELECT * FROM comment_table ORDER BY id ASC")
    fun readAllComments(): LiveData<List<Comment>>
}