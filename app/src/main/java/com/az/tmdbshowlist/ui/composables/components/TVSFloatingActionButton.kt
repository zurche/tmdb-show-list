package com.az.tmdbshowlist.ui.composables.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.az.tmdbshowlist.R

@Composable
@Preview
fun TVSFloatingActionButton(onSortClicked: () -> Unit = {}) {
    Box(modifier = Modifier.padding(15.dp), contentAlignment = Alignment.BottomEnd) {
        FloatingActionButton(
            modifier = Modifier
                .size(56.dp),
            onClick = { onSortClicked() },
            containerColor = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(16.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.sort_ic),
                contentDescription = "Sort List FAB",
                tint = Color.White,
            )
        }
    }
}
