package co.edu.udea.compumovil.gr08_20201.lab2.Repositories

import androidx.lifecycle.LiveData
import co.edu.udea.compumovil.gr08_20201.lab2.Dao.CommentDao
import co.edu.udea.compumovil.gr08_20201.lab2.Entities.Comment

class CommentRepository (private val commentDao: CommentDao) {
    val readAllComments: LiveData<List<Comment>> = commentDao.readAllComments()
    lateinit var readComment: LiveData<Comment>

    suspend fun addComment(comment: Comment){
        commentDao.addComment(comment)
    }
}