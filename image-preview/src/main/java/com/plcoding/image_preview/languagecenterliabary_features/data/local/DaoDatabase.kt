package com.example.languagecenterliabary.languagecenterliabary_features.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.languagecenterliabary.languagecenterliabary_features.domain.DaoModel

@Database(entities = [DaoModel::class], version = 2, exportSchema = false)
abstract class DaoDatabase : RoomDatabase(){

    abstract fun getDao(): DaoFunctions

}