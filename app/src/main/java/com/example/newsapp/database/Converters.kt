package com.example.newsapp.database

import androidx.room.TypeConverter
import com.example.newsapp.utils.model.Source

class Converters {
    @TypeConverter
    fun fromsource(source: Source):String{
        return source.name
    }
    @TypeConverter
    fun toSource(name:String): Source {
        return Source(name,name)
    }
}