package com.theempire.fielform.visit.add_visits

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.theempire.fielform.visit.list.TopAppBar
import com.theempire.fielform.visit.list.VisitsUiEvent

@Composable
fun AddVisitsScreen(navController: NavController, viewModel: AddVisitViewModel = hiltViewModel()) {
    val uiState: AddVisitUiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                AddVisitUiEffect.PopUpBackstack -> navController.popBackStack()
            }
        }
    }

    Scaffold(
        topBar = { Text(
            text = "Registrar visita",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp))
                 },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
            InputText("Nombre del sitio", uiState.siteName) {
                viewModel.onEvent(AddVisitUiEvent.UpdateSiteName(it))
            }

            InputText("Nombre del agente", uiState.agentName) {
                viewModel.onEvent(AddVisitUiEvent.UpdateAgentName(it))
            }

            InputText("Comentario", uiState.comment) {
                viewModel.onEvent(AddVisitUiEvent.UpdateComment(it))
            }

            Button(
                onClick = {
                    viewModel.onEvent(AddVisitUiEvent.Submit)
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Guardar visita")
            }
        }

    }


}

@Composable
fun InputText(label: String, value: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        maxLines = 5
    )
}