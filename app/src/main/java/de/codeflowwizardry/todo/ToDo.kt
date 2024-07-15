package de.codeflowwizardry.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator
import java.time.LocalDate

class ToDo {
    var id: String
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var dueDate by mutableStateOf(LocalDate.now())
    var done by mutableStateOf(false)

    constructor(title: String, description: String, dueDate: LocalDate?, done: Boolean) {
        this.id = UUIDGenerator().generateId(this).toString()
        this.title = title
        this.description = description
        this.dueDate = dueDate
        this.done = done
    }

    constructor() {
        this.id = UUIDGenerator().generateId(this).toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ToDo

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}