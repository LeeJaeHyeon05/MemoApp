package com.example.memoapp

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface MemoDAO {

    @Insert(onConflict = REPLACE)
    fun insert(memo : MemoEntity)

    @Query("SELECT * FROM memo")
    fun getAll() : List<MemoEntity>

    @Delete
    fun delete(memo: MemoEntity)

    @Update
    fun update(memo: MemoEntity)

}