package com.example.shoppinglist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [fruits::class], version = 1, exportSchema = false)
abstract class FDataBase: RoomDatabase() {

    abstract fun fruitsDao(): fruitsDao
    companion object{

        @Volatile
        private var INSTANCE: FDataBase?= null
        fun getDataBase(context: Context): FDataBase{
            val tempInstance= INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FDataBase::class.java,
                    "fruits_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}