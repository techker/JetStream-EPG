/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.jetstream.presentation.screens.shows

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.rememberTvLazyListState
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import com.google.jetstream.data.entities.Movie
import com.google.jetstream.data.entities.MovieList
import com.google.jetstream.data.util.StringConstants
import com.google.jetstream.presentation.common.MoviesRow
import com.google.jetstream.presentation.screens.dashboard.rememberChildPadding
import com.google.jetstream.presentation.screens.movies.MoviesScreenMovieList

@Composable
fun ShowsScreen(
    onTVShowClick: (movie: Movie) -> Unit,
    onScroll: (isTopBarVisible: Boolean) -> Unit,
    isTopBarVisible: Boolean,
    showScreenViewModel: ShowScreenViewModel = hiltViewModel(),
) {
    val uiState = showScreenViewModel.uiState.collectAsStateWithLifecycle()
    when (val currentState = uiState.value) {
        is ShowScreenUiState.Loading -> {
            Loading()
        }

        is ShowScreenUiState.Ready -> {
            Catalog(
                tvShowList = currentState.tvShowList,
                bingeWatchDramaList = currentState.bingeWatchDramaList,
                onTVShowClick = onTVShowClick,
                onScroll = onScroll,
                isTopBarVisible = isTopBarVisible,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun Catalog(
    tvShowList: MovieList,
    bingeWatchDramaList: MovieList,
    onTVShowClick: (movie: Movie) -> Unit,
    onScroll: (isTopBarVisible: Boolean) -> Unit,
    isTopBarVisible: Boolean,
    modifier: Modifier = Modifier
) {
    val childPadding = rememberChildPadding()
    val tvLazyListState = rememberTvLazyListState()
    val shouldShowTopBar by remember {
        derivedStateOf {
            tvLazyListState.firstVisibleItemIndex == 0 &&
                    tvLazyListState.firstVisibleItemScrollOffset == 0
        }
    }

    LaunchedEffect(shouldShowTopBar) {
        onScroll(shouldShowTopBar)
    }
    LaunchedEffect(isTopBarVisible) {
        if (isTopBarVisible) tvLazyListState.animateScrollToItem(0)
    }

    TvLazyColumn(
        modifier = modifier,
        state = tvLazyListState,
        contentPadding = PaddingValues(top = childPadding.top, bottom = 104.dp)
    ) {
        item {
            MoviesScreenMovieList(
                movieList = tvShowList,
                onMovieClick = onTVShowClick
            )
        }
        item {
            MoviesRow(
                modifier = Modifier.padding(top = childPadding.top),
                title = StringConstants.Composable.BingeWatchDramasTitle,
                movies = bingeWatchDramaList,
                onMovieClick = onTVShowClick
            )
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun Loading(modifier: Modifier = Modifier) {
    Text(text = "Loading...", modifier = modifier)
}