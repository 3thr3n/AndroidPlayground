package de.codeflowwizardry.todo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.codeflowwizardry.todo.ui.theme.ToDoListeTheme

@Composable
fun Header(
    onNameChange: (ToDo) -> Unit,
    onlyUnfinished: Boolean,
    onToggleUnfinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    var openCreateDialog by remember { mutableStateOf(false) }
    val buttonColor = if (onlyUnfinished) Color.Red else Color.Green

    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.Done,
            contentDescription = null,
            tint = buttonColor,
            modifier = Modifier
                .padding(12.dp)
                .clickable {
                    onToggleUnfinished()
                })

        Spacer(
            modifier = Modifier
                .width(16.dp)
                .weight(1f)
        )

        Button(
            onClick = {
                openCreateDialog = true
            },
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }

    when {
        openCreateDialog -> {
            CreateDialog(
                onDismissRequest = { openCreateDialog = false },
                onSubmitRequest = {
                    onNameChange(it)
                    openCreateDialog = false
                })
        }
    }
}

@Preview(showBackground = true, name = "Header only unfinished")
@Composable
fun HeaderPreview() {
    ToDoListeTheme {
        Header(onNameChange = {}, onlyUnfinished = true, onToggleUnfinished = {})
    }
}

@Preview(showBackground = true, name = "Header all")
@Composable
fun HeaderAllPreview() {
    ToDoListeTheme {
        Header(onNameChange = {}, onlyUnfinished = false, onToggleUnfinished = {})
    }
}