package com.theempire.fieldform.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.theempire.fielform.visit.add_visits.AddVisitsScreen
import com.theempire.fielform.visit.list.VisitsListScreen

@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = VisitsListScreen) {
        composable<VisitsListScreen> { VisitsListScreen(navController) }
        composable<AddVisitScreen> { AddVisitsScreen(navController) }
    }
}
