package com.coderio.themoviedb.ui.home

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.coderio.themoviedb.data.movies.TestMoviesRepository
import com.coderio.themoviedb.ui.MainActivity
import com.coderio.themoviedb.ui.TrendytApp
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeTest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    private val movieList = TestMoviesRepository.popularMoviesList

    @Before
    fun setup() {
        hiltTestRule.inject()
        composeTestRule.activity.setContent {
            TrendytApp()
        }
    }

    @Test
    fun popularMoviesDisplayed() {
        //assert all items are displayed
        movieList.forEach { movie ->
            val node = composeTestRule.onNodeWithText(
                text = movie.title
            )

            node.assertExists()
            node.performTouchInput {
                swipeUp()
            }
        }
    }
}
