package com.plcoding.image_preview

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.languagecenterliabary.languagecenterliabary_features.data.local.DaoFunctions
import com.example.languagecenterliabary.languagecenterliabary_features.domain.DaoModel

@Database(entities = [DaoModel::class], version = 2, exportSchema = false)
abstract class DaoDatabase : RoomDatabase(){

    abstract fun getDao(): DaoFunctions

    companion object {
        @Volatile
        private var INSTANCE: DaoDatabase? = null

        fun getDatabase(context: Context): DaoDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DaoDatabase::class.java,
                    "dao_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}