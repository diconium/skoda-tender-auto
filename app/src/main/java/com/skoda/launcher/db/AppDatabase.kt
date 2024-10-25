package com.skoda.launcher.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.skoda.launcher.db.dao.UserDao
import com.skoda.launcher.db.entities.User

@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}