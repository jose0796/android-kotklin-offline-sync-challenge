package com.theempire.fielform.visit.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.theempire.fieldform.navigation.AddVisitScreen
import com.theempire.fielform.model.Visit

@Composable
fun VisitsListScreen(
    navController: NavController,
    viewModel : VisitsViewModel = hiltViewModel()
) {
    val uiState: VisitsUiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var showLoader by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                VisitsUiEffect.NetworkLostMessage -> snackbarHostState.showSnackbar("Network connection lost!")
                VisitsUiEffect.NetworkRestoredMessage -> snackbarHostState.showSnackbar("Network connection available!")
                VisitsUiEffect.NavigateToAddVisit -> { navController.navigate(AddVisitScreen) }
                VisitsUiEffect.HideLoading -> showLoader = false
                VisitsUiEffect.ShowLoading -> showLoader = true
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = {
            Text(
                "FieldForm",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.Red.copy(alpha = if (showLoader) 1f else 0f ),
            )

            Spacer(modifier= Modifier.width(8.dp))

            IconButton(onClick = { viewModel.onEvent(VisitsUiEvent.SyncVisits) }) {
                Icon(Icons.Default.Refresh, contentDescription = "")
            }
        }) },
        snackbarHost = { TopSnackbarHost(snackbarHostState) },
        containerColor = Color.White,
        floatingActionButton = {
            FloatingActionButton (onClick = {
                viewModel.onEvent(VisitsUiEvent.NavigateToAddVisit  )
            }) {
                Icon(Icons.Default.Add, contentDescription = "")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            ) {
                when(uiState) {
                    VisitsUiState.Empty -> EmptyView()
                    VisitsUiState.Error -> ErrorView()
                    is VisitsUiState.List -> {
                        val visits = (uiState as VisitsUiState.List).list.collectAsLazyPagingItems()
                        VisitsListView(visits)
                    }
                    VisitsUiState.Loading -> LoadingView()
                }
            }
    }

}

@Composable
fun TopAppBar(title : @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        title()
    }
}

@Composable
fun TopSnackbarHost(hostState: SnackbarHostState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        SnackbarHost(
            hostState = hostState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun VisitsListView(visits: LazyPagingItems<Visit>) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn (
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(visits.itemCount) { index ->
                visits[index]?.let { VisitRow(it) }
            }
        }
    }
}

@Composable
fun LoadingView() {
    Column {  }
}

@Composable
fun EmptyView() {
    Column {
        Text("Empty")
    }
}

@Composable
fun ErrorView() {
    Column {
        Text("Error")
    }
}