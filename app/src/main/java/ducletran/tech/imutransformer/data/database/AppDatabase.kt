package ducletran.tech.imutransformer.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ducletran.tech.imutransformer.data.database.dao.LabelDao
import ducletran.tech.imutransformer.data.database.entity.Label

@Database(entities = [Label::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun labelDao(): LabelDao

    companion object {
        private const val DATABASE_NAME = "imu-db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }
}