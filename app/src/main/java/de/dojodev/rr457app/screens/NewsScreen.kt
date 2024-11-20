package de.dojodev.rr457app.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.dojodev.rr457app.data.model.News

@Composable
fun NewsScreen(viewModel: NewsScreenViewModel = hiltViewModel()) {
    val news by viewModel.news.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.load()
    }

    NewsScreen(news)
}

@Composable
fun NewsScreen(news: List<News>) {

}