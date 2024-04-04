package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.ui.theme.ToDoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ToDoApp()
                }
            }
        }
    }
}

@Composable
fun ToDoApp() {
    var newTask by remember { mutableStateOf("") }
    var tasks by remember { mutableStateOf(listOf<String>()) }
    val focusManager = LocalFocusManager.current
    var taskCount by remember { mutableStateOf(0) }

    Column(modifier = Modifier.padding(16.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .background(Color.Cyan, RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = newTask,
                onValueChange = { newTask = it },
                label = { Text("Enter a task", color = Color.Gray) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    if (newTask.isNotBlank()) {
                        taskCount++
                        tasks = tasks + "$taskCount. $newTask"
                        newTask = ""
                        focusManager.clearFocus()
                    }
                }),
                modifier = Modifier.fillMaxWidth()
            )
        }

        LazyColumn {
            items(tasks) { task ->
                TaskItem(task = task) { taskToDelete ->
                    tasks = tasks - taskToDelete
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: String, onDelete: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Gray),
            color = Color.White
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = task,
                    modifier = Modifier
                        .weight(1f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                IconButton(onClick = { onDelete(task) }) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ToDoTheme {
        ToDoApp()
    }
}
