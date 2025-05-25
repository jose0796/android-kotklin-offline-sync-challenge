package com.theempire.fielform.visit.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.theempire.fielform.model.Visit

@Composable
fun VisitRow(visit: Visit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.LightGray.copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ){
            Text(
                text = visit.siteName,
                modifier = Modifier
                    .padding(4.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Text(
                text = visit.agentName,
                modifier = Modifier
                    .padding(4.dp),
                fontWeight = FontWeight.Light,
                fontSize = 16.sp
            )
        }


        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = visit.status.string(),
            modifier = Modifier
                .padding(4.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}
