package com.seuprojeto.blocodenotas

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seuprojeto.blocodenotas.ui.theme.BlocoDeNotasTheme
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlocoDeNotasTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("NotePrefs", Context.MODE_PRIVATE)
    var note by remember { mutableStateOf(sharedPreferences.getString("NOTE", "") ?: "") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                TextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Digite sua nota aqui") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        thread {
                            sharedPreferences.edit().putString("NOTE", note).apply()
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Salvar")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    BlocoDeNotasTheme {
        MainScreen()
    }
}
