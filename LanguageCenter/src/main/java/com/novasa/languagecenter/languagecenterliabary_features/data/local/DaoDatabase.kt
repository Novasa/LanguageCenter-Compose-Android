package com.novasa.languagecenter.languagecenterliabary_features.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.DaoStringModel

@Database(entities = [DaoStringModel::class], version = 3, exportSchema = false)
abstract class DaoDatabase : RoomDatabase(){

    abstract fun getDao(): DaoFunctions
}