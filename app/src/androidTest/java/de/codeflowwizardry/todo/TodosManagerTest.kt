package de.codeflowwizardry.todo

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class TodosManagerTest {
    private lateinit var todosManager: TodosManager

    @Before
    fun setup() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        todosManager = TodosManager(appContext)
    }

    @Test
    fun shouldSaveASingleDataWhichCanBeReadAgain() {
        // given
        val todo = ToDo("Title", "Deswc", LocalDate.now(), false);

        // when
        todosManager.addToDo(todo)

        // then
        val savedData = todosManager.getTodos()

        assertEquals(1, savedData.size)
        assertEquals(todo, savedData[0])
    }

    @Test
    fun shouldAddASingleDataWhichCanBeReadAgain() {
        // given
        val todo = ToDo("Title", "Deswc", LocalDate.now(), false);
        todosManager.addToDo(todo)

        val toAddTodo = ToDo("Second", "Second description", LocalDate.now(), true);

        // when
        todosManager.addToDo(toAddTodo)

        // then
        val savedData = todosManager.getTodos()

        assertEquals(2, savedData.size)
        MatcherAssert.assertThat(savedData, containsInAnyOrder(todo, toAddTodo))
    }

    @Test
    fun shouldDeleteWithId() {
        // given
        val todo = ToDo("Title", "Deswc", LocalDate.now(), false);
        todosManager.addToDo(todo)

        // when
        todosManager.deleteToDo(todo.id)

        // then
        val savedData = todosManager.getTodos()

        assertEquals(0, savedData.size)
    }

    @Test
    fun shouldUpdateStatus() {
        // given
        val todo = ToDo("Title", "Deswc", LocalDate.now(), false);
        todosManager.addToDo(todo)

        // when
        todosManager.finishToDo(todo.id)

        // then
        val savedData = todosManager.getTodos()

        assertEquals(1, savedData.size)
        assertEquals(true, savedData[0].done)
    }

    @Test
    fun shouldFailUpdateStatus() {
        // when
        assertThrows(
            IllegalArgumentException::class.java
        ) { todosManager.finishToDo("NotAvailableInMap") }
    }

    @Test
    fun shouldFailDelete() {
        // when
        assertThrows(
            IllegalArgumentException::class.java
        ) { todosManager.deleteToDo("NotAvailableInMap") }
    }

    @After
    fun cleanup() {
        todosManager.clean();
    }
}