package ru.protei.malkovaar.domain

import androidx.compose.runtime.MutableState

class Note (
    var title: String,
    var text: String
) : MutableState<Note?> {
    override var value: Note?
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun component1(): Note? {
        TODO("Not yet implemented")
    }

    override fun component2(): (Note?) -> Unit {
        TODO("Not yet implemented")
    }
}