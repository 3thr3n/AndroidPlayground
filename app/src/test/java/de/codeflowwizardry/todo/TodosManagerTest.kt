package de.codeflowwizardry.todo

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class TodosManagerTest {
    private lateinit var todosManager: TodosManager

    @get:Rule
    val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setup() {
        todosManager = TodosManager(context)
    }

    @Test
    fun shouldSaveASingleDataWhichCanBeReadAgain() {
        // given
        val todo = ToDo("Title", "Deswc", LocalDate.now(), false);

        // when
        todosManager.saveSingleData(todo)

        // then
        val savedData = todosManager.getData()

        assertEquals(1, savedData.size)
        assertEquals(todo, savedData[0])
    }

    @Test
    fun shouldAddASingleDataWhichCanBeReadAgain() {
        // given
        val todo = ToDo("Title", "Deswc", LocalDate.now(), false);
        todosManager.saveSingleData(todo)

        val toAddTodo = ToDo("Second", "Second description", LocalDate.now(), true);

        // when
        todosManager.saveSingleData(toAddTodo)

        // then
        val savedData = todosManager.getData()

        assertEquals(2, savedData.size)
        assertEquals(todo, savedData[0])
        assertEquals(toAddTodo, savedData[1])
    }

    @Test
    fun shouldDeleteWithId() {
        // given
        val todo = ToDo("Title", "Deswc", LocalDate.now(), false);
        todosManager.saveSingleData(todo)

        // when
        todosManager.deleteOne(todo.id)

        // then
        val savedData = todosManager.getData()

        assertEquals(0, savedData.size)
    }
}