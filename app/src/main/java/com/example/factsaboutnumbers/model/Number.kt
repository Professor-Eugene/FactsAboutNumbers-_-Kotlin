package com.example.factsaboutnumbers.model

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "numbers_table", indices = [Index(value = arrayOf("fact"), unique = true)])
class Number {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    val number: Int

    val fact: String

    constructor(number: Int, fact: String) {
        this.number = number
        this.fact = fact
    }


    override fun toString(): String {
        return "Number: id = $id, number = $number, fact = $fact"
    }
}
