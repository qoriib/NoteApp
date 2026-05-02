package edu.learning.noteapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.learning.noteapp.platform.DeviceInfo
import edu.learning.noteapp.platform.NetworkMonitor
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

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
                        title = { Text("Notes App") },
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
                            label = { Text("Settings") },
                            icon = { Icon(Icons.Default.Settings, contentDescription = null) }
                        )
                    }
                }
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                    when (selectedTab) {
                        0 -> MainScreen()
                        1 -> SettingsScreen()
                    }
                }
            }
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
