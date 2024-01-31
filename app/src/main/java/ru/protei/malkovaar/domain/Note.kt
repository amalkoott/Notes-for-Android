package ru.protei.malkovaar.domain

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Note (
    var title: String,
    var text: String,
    var remoteId: Long?
){
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}
