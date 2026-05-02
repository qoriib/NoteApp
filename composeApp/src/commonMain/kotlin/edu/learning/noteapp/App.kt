package edu.learning.noteapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.learning.noteapp.platform.DeviceInfo
import edu.learning.noteapp.platform.NetworkMonitor
import edu.learning.noteapp.ui.AIUiState
import edu.learning.noteapp.ui.AIViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    KoinContext {
        MaterialTheme {
            var selectedTab by remember { mutableStateOf(0) }

            Scaffold(
                topBar = {
                    val networkMonitor = koinInject<NetworkMonitor>()
                    val isConnected by networkMonitor.isConnected.collectAsState(true)
                    
                    CenterAlignedTopAppBar(
                        title = { Text("Notes App AI") },
                        actions = {
                            StatusIndicator(isConnected)
                        }
                    )
                },
                bottomBar = {
                    NavigationBar {
                        NavigationBarItem(
                            selected = selectedTab == 0,
                            onClick = { selectedTab = 0 },
                            label = { Text("Notes") },
                            icon = { Icon(Icons.Default.List, contentDescription = null) }
                        )
                        NavigationBarItem(
                            selected = selectedTab == 1,
                            onClick = { selectedTab = 1 },
                            label = { Text("AI Assistant") },
                            icon = { Icon(Icons.Default.AutoAwesome, contentDescription = null) }
                        )
                        NavigationBarItem(
                            selected = selectedTab == 2,
                            onClick = { selectedTab = 2 },
                            label = { Text("Settings") },
                            icon = { Icon(Icons.Default.Settings, contentDescription = null) }
                        )
                    }
                }
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                    when (selectedTab) {
                        0 -> MainScreen()
                        1 -> AIScreen()
                        2 -> SettingsScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun AIScreen() {
    val viewModel = koinViewModel<AIViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    var userPrompt by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<Pair<String, Boolean>>() } // Text to isUser

    LaunchedEffect(uiState) {
        if (uiState is AIUiState.Success) {
            messages.add((uiState as AIUiState.Success).response to false)
            viewModel.reset()
        } else if (uiState is AIUiState.Error) {
            messages.add("Error: ${(uiState as AIUiState.Error).message}" to false)
            viewModel.reset()
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        LazyColumn(modifier = Modifier.weight(1f).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(messages) { (text, isUser) ->
                ChatBubble(text, isUser)
            }
            if (uiState is AIUiState.Loading) {
                item {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp).align(Alignment.CenterHorizontally))
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = userPrompt,
                onValueChange = { userPrompt = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Ask AI something...") },
                enabled = uiState !is AIUiState.Loading
            )
            IconButton(
                onClick = {
                    if (userPrompt.isNotBlank()) {
                        messages.add(userPrompt to true)
                        viewModel.askAI(userPrompt)
                        userPrompt = ""
                    }
                },
                enabled = uiState !is AIUiState.Loading && userPrompt.isNotBlank()
            ) {
                Icon(Icons.Default.Send, contentDescription = "Send")
            }
        }
    }
}

@Composable
fun ChatBubble(text: String, isUser: Boolean) {
    val alignment = if (isUser) Alignment.End else Alignment.Start
    val color = if (isUser) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer
    
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = alignment) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = color,
            tonalElevation = 2.dp
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun StatusIndicator(isConnected: Boolean) {
    val color = if (isConnected) Color.Green else Color.Red
    val text = if (isConnected) "Online" else "Offline"
    
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = 16.dp)
    ) {
        Surface(
            shape = MaterialTheme.shapes.small,
            color = color,
            modifier = Modifier.size(10.dp)
        ) {}
        Spacer(Modifier.width(4.dp))
        Text(text, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Your Notes will appear here", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun SettingsScreen() {
    val deviceInfo = koinInject<DeviceInfo>()
    
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Device Information", style = MaterialTheme.typography.headlineSmall)
        
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                InfoRow(label = "Platform", value = deviceInfo.platform)
                InfoRow(label = "Model", value = deviceInfo.model)
                InfoRow(label = "OS Version", value = deviceInfo.osVersion)
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.secondary)
        Text(value, style = MaterialTheme.typography.bodyLarge)
    }
}
