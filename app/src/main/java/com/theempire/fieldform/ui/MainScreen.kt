package com.theempire.fieldform.ui

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.work.*
import com.theempire.fieldform.data.local.Visit
import com.theempire.fieldform.workers.SyncWorker
import java.util.concurrent.TimeUnit


@Composable
fun MainScreen(viewModel: VisitViewModel = viewModel()) {
    val context = LocalContext.current
    val visits by viewModel.visits.observeAsState(emptyList())

    var site by remember { mutableStateOf("") }
    var agent by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val workRequest = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "sync_visits",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Formulario de Visita", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = site,
            onValueChange = { site = it },
            label = { Text("Nombre del sitio") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = agent,
            onValueChange = { agent = it },
            label = { Text("Nombre del agente") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            label = { Text("Comentario") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (site.isNotBlank() && agent.isNotBlank()) {
                    viewModel.saveVisit(
                        Visit(siteName = site, agentName = agent, comment = comment)
                    )
                    site = ""; agent = ""; comment = ""
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Guardar Visita")
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Visitas Guardadas", style = MaterialTheme.typography.titleMedium)

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(visits) { visit ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Sitio: ${visit.siteName}", style = MaterialTheme.typography.bodyLarge)
                        Text("Agente: ${visit.agentName}")
                        Text("Estado: ${visit.status}", color = when (visit.status) {
                            "SENT" -> Color.Green
                            "ERROR" -> Color.Red
                            else -> Color.Gray
                        })
                    }
                }
            }
        }
    }
}
