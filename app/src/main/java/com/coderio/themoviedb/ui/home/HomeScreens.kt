package com.coderio.themoviedb.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.coderio.themoviedb.R
import com.coderio.themoviedb.model.MovieSummary
import com.coderio.themoviedb.ui.LocalDateFormatter
import com.coderio.themoviedb.ui.TrendytAppFoundation
import com.coderio.themoviedb.ui.components.Rating
import com.coderio.themoviedb.ui.components.RetryScreen
import com.coderio.themoviedb.utils.DateFormatter
import java.util.*

@Composable
fun MovieFeedScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState.HasMovies,
    moviesLazyListState: LazyListState,
    onRefreshMovies: () -> Unit,
    onSelectMovie: (Int) -> Unit
) {
    HomeScaffold(
        modifier = modifier,
        lazyListState = moviesLazyListState
    ) { contentPadding ->
        SwipeRefresh(
            modifier = modifier.padding(
                WindowInsets.systemBars
                    .only(WindowInsetsSides.Bottom)
                    .add(WindowInsets(top = contentPadding.calculateTopPadding()))
                    .asPaddingValues()
            ),
            state = rememberSwipeRefreshState(uiState.isRefreshing),
            indicator = { state, refreshTriggerDistance ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = refreshTriggerDistance,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            },
            onRefresh = onRefreshMovies
        ) {
            LazyColumn(
                state = moviesLazyListState
            ) {
                itemsIndexed(items = uiState.moviesFeed) { _, item ->
                    MovieItem(movieSummary = item, onSelect = onSelectMovie)
                }
            }
        }
    }
}

@Composable
fun NoMoviesScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState.NoMovies,
    onRefreshMovies: () -> Unit
) {
    HomeScaffold(modifier = modifier) {
        when (uiState.hasError) {
            true -> {
                RetryScreen(
                    modifier = Modifier.fillMaxSize(),
                    messageStyle = MaterialTheme.typography.labelLarge
                ) {
                    onRefreshMovies()
                }
            }
            false -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    if (uiState.isRefreshing) {
                        LinearProgressIndicator()
                    } else {
                        EmptyNotice()
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(
    movieSummary: MovieSummary,
    onSelect: (Int) -> Unit,
    dateFormatter: DateFormatter = LocalDateFormatter.current
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp, 8.dp)
            .clickable {
                onSelect(movieSummary.id)
            }
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ratio = 1.78f)
                    .wrapContentHeight()
                    .background(color = MaterialTheme.colorScheme.secondaryContainer),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movieSummary.imageUrl)
                    .crossfade(500)
                    .build(),
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
            )

            Column(
                modifier = Modifier.padding(8.dp, 12.dp)
            ) {
                Text(
                    text = movieSummary.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = dateFormatter.formatDefaultDate(movieSummary.releaseDate),
                        style = MaterialTheme.typography.labelMedium
                    )

                    Rating(
                        modifier = Modifier.height(16.dp),
                        rating = movieSummary.rating,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyNotice(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.movie),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            style = MaterialTheme.typography.labelMedium,
            text = stringResource(id = R.string.no_movies_message),
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScaffold(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState? = null,
    content: @Composable (PaddingValues) -> Unit
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colorScheme.surface,
                elevation = if (!lazyListState.isScrolled) 0.dp else 4.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(Modifier.width(4.dp))
                }
            }
        },
        content = content
    )
}

val LazyListState?.isScrolled: Boolean
    get() = this?.let {
        firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0
    } ?: false

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMovieFeedScreen() {
    TrendytAppFoundation {
        MovieFeedScreen(
            uiState = HomeUiState.HasMovies(
                moviesFeed = (1..5).map { seed ->
                    MovieSummary(
                        id = seed,
                        title = "Movie $seed",
                        rating = 10f - seed,
                        releaseDate = GregorianCalendar(2022 - seed, seed, seed).time,
                        imageUrl = ""
                    )
                },
                isRefreshing = false,
                errorMessages = emptyList()
            ),
            moviesLazyListState = rememberLazyListState(),
            onRefreshMovies = {}
        ) {
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewNoMoviesScreenWithLoading() {
    TrendytAppFoundation {
        NoMoviesScreen(
            uiState = HomeUiState.NoMovies(
                isRefreshing = true,
                errorMessages = emptyList()
            )
        ) {
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewNoMoviesScreenWithError() {
    TrendytAppFoundation {
        NoMoviesScreen(
            uiState = HomeUiState.NoMovies(
                isRefreshing = false,
                errorMessages = listOf("some error")
            )
        ) {
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewHomeScaffold() {
    TrendytAppFoundation {
        HomeScaffold {
            Text(
                modifier = Modifier.padding(it),
                text = "Some content"
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMovieItem() {
    TrendytAppFoundation {
        MovieItem(
            movieSummary = MovieSummary(
                id = 1,
                title = "Title",
                rating = 8.5f,
                releaseDate = GregorianCalendar(2022, Calendar.MARCH, 1).time,
                imageUrl = ""
            ),
            onSelect = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewEmptyNotice() {
    TrendytAppFoundation {
        EmptyNotice()
    }
}
