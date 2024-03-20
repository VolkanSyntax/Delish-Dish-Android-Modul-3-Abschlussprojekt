package de.syntax.androidabschluss.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    var title: String,
    var text: String




)
