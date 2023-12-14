package com.example.newsappmyself.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.newsappmyself.models.Source


class Converter {

    @TypeConverter

    fun from(source:Source):String{

        return source.name

    }

    @TypeConverter
    fun toSource(name:String):Source{

        return Source(name,name)

    }

}