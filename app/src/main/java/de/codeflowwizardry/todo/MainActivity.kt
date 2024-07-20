package de.codeflowwizardry.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import de.codeflowwizardry.todo.ui.theme.ToDoListeTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Base()
                }
            }
        }
    }
}

@Composable
fun Base(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val todosManager = remember { TodosManager(context) }
    var onlyUnfinished by remember { mutableStateOf(false) }
    var todos by remember { mutableStateOf(todosManager.getTodos()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Header(
            onNameChange = { name ->
                run {
                    todosManager.addToDo(name)
                    todos = todosManager.getTodos()
                }
            },
            onlyUnfinished,
            onToggleUnfinished = { onlyUnfinished = !onlyUnfinished })
        Todos(todos, onlyUnfinished, update = {
            run {
                todosManager.finishToDo(it)
            }
        }, delete = {
            run {
                todosManager.deleteToDo(it)
                todos = todosManager.getTodos()
            }
        })
    }
}

@Composable
fun Todos(
    todos: List<ToDo>,
    onlyUnfinished: Boolean,
    update: (id: String) -> Unit,
    delete: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var shownTodos = todos
    if (onlyUnfinished) {
        shownTodos = shownTodos.filter { x -> !x.done }
    }

    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(items = shownTodos) { todo ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = todo.done, onCheckedChange = {
                    run {
                        todo.done = !todo.done
                        update(todo.id)
                    }
                }, enabled = !todo.done)

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .width(150.dp)
                ) {
                    Text(
                        text = todo.title
                    )
                    Text(
                        text = todo.description
                    )
                }

                Row {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                    Text(
                        text = todo.dueDate.toString()
                    )
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .size(18.dp)
                            .align(Alignment.CenterVertically)
                            .clickable { delete(todo.id) }
                    )
                }
            }

            HorizontalDivider()
        }
    }
}

@Composable
fun CreateDialog(
    onDismissRequest: () -> Unit,
    onSubmitRequest: (todo: ToDo) -> Unit,
    modifier: Modifier = Modifier
) {
    val todo by remember { mutableStateOf(ToDo()) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Here you will create a ToDo",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                OutlinedTextField(
                    value = todo.title,
                    onValueChange = { todo.title = it },
                    label = { Text(text = "Title") }
                )

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = todo.description,
                    onValueChange = { todo.description = it },
                    label = { Text(text = "Description") }
                )

                Spacer(Modifier.height(16.dp))

                CustomDateField(value = todo.dueDate, onValueChanged = { todo.dueDate = it })

                Spacer(
                    Modifier
                        .height(16.dp)
                        .weight(1f)
                )

                Row(
                    Modifier
                        .align(Alignment.End)
                        .padding(top = 16.dp)
                ) {
                    Button(
                        onClick = {
                            onDismissRequest()
                        },
                    ) {
                        Text(text = "Cancel")
                    }

                    Button(
                        onClick = {
                            onSubmitRequest(todo)
                        },
                    ) {
                        Text(text = "Submit")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Todos unfinished")
@Composable
fun TodosPreview() {
    ToDoListeTheme {
        Todos(
            todos = listOf(
                ToDo("Title", "Desc", LocalDate.now(), false),
                ToDo("Title2", "Desc2", LocalDate.now(), false)
            ), onlyUnfinished = false,
            delete = {},
            update = {}
        )
    }
}

@Preview(showBackground = true, name = "Dialog Preview")
@Composable
fun BasePreview() {
    ToDoListeTheme {
        CreateDialog(onDismissRequest = {}, onSubmitRequest = {})
    }
}