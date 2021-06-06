package com.angelica.myfavs.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite (
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
)