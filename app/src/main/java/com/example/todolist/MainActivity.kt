package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolist.ui.theme.ToDoListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListAppTheme {
                ToDoListScreen()
            }
        }
    }
}

@Composable
fun ToDoListScreen() {
    var todoItems by remember { mutableStateOf(listOf<String>()) }
    var newTask by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("To-Do List", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = newTask,
                onValueChange = { newTask = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    if (newTask.isNotBlank()) {
                        todoItems = todoItems + newTask
                        newTask = ""
                        focusManager.clearFocus()
                    }
                }),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .border(1.dp, Color.Gray)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                if (newTask.isNotBlank()) {
                    todoItems = todoItems + newTask
                    newTask = ""
                    focusManager.clearFocus()
                }
            }) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        todoItems.forEach { task ->
            ToDoItem(task)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ToDoItem(task: String) {
    Surface(
        color = Color(0xFFE0E0E0),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = task,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ToDoListPreview() {
    ToDoListAppTheme {
        ToDoListScreen()
    }
}