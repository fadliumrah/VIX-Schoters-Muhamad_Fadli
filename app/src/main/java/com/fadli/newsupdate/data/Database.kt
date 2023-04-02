package com.fadli.newsupdate.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [NewsArticle::class, BreakingNews::class],
    version = 2
)
abstract class Database : RoomDatabase() {
    abstract fun newsArticleDao(): Dao
}