package com.example.playuser.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.playuser.utilities.DATABASE_NAME
import com.example.playuser.utilities.USER_DATA_FILENAME
import com.example.playuser.workers.SeedDatabaseWorker

@Database(
    version = 1,
    entities = [User::class],
    //autoMigrations = [AutoMigration (from = 1, to = 2, spec = AppDatabase.MyAutoMigration::class)],
    exportSchema = true)
abstract class AppDatabase: RoomDatabase() {
    /*@RenameTable(fromTableName = "user", toTableName = "AppUser")
    class MyAutoMigration : AutoMigrationSpec*/
    abstract fun UserDao(): UserDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
                                .setInputData(workDataOf(SeedDatabaseWorker.KEY_FILENAME to USER_DATA_FILENAME))
                                .build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                )
                .build()
        }
    }

}

