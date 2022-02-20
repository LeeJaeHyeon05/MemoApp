package com.example.memoapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MemoEntity::class], version = 1, exportSchema = false)

abstract class MemoDatabase : RoomDatabase() {
    abstract fun memoDAO() : MemoDAO


    companion object {
        @Volatile

        private var INSTANCE : MemoDatabase? = null

        fun getInstance(context : Context) : MemoDatabase? {
            if (INSTANCE == null) {

                synchronized(MemoDatabase) {
                    INSTANCE = Room.databaseBuilder(context, MemoDatabase::class.java, "memo.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}


