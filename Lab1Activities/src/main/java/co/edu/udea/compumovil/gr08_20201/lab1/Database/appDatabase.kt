package co.edu.udea.compumovil.gr08_20201.lab1.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.edu.udea.compumovil.gr08_20201.lab1.Dao.POIDao
import co.edu.udea.compumovil.gr08_20201.lab1.Dao.UserDao
import co.edu.udea.compumovil.gr08_20201.lab1.Entities.POI
import co.edu.udea.compumovil.gr08_20201.lab1.Entities.User

@Database(entities = [User::class,POI::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun pointDao(): POIDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "places_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}