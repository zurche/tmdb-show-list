package com.az.tmdbshowlist.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.az.tmdbshowlist.ui.composables.TVSListScreen
import com.az.tmdbshowlist.ui.theme.TMDBShowListTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class TVSListActivity : ComponentActivity() {

    private val viewModel: TVSViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TMDBShowListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TVSListScreen()
                }
            }
        }
    }
}