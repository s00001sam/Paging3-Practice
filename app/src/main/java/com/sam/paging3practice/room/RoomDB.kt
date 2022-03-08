package com.sam.paging3practice.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sam.paging3practice.data.Attraction
import com.sam.paging3practice.data.RoomKey

@Database(
    entities = [Attraction::class, RoomKey::class],
    version = 1,
    exportSchema = false
)
abstract class RoomDB: RoomDatabase() {
    abstract fun attractionDao(): AttractionDao
    abstract fun keyDao(): KeyDao

    companion object {
        @Volatile
        private var instance: RoomDB? = null
        private val LOCK = Any()

        fun getInstance(context: Context) = instance
            ?: synchronized(LOCK) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RoomDB::class.java,
                "sam_paging3_room_db"
            ).fallbackToDestructiveMigration()
                .build()
    }
}