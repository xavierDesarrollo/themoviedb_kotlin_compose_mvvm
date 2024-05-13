package com.coderio.themoviedb.ui

import androidx.compose.runtime.staticCompositionLocalOf
import com.coderio.themoviedb.utils.DateFormatter
import com.coderio.themoviedb.utils.TimeFormatter

val LocalDateFormatter = staticCompositionLocalOf<DateFormatter> {
    error("DateFormatter not provided")
}

val LocalTimeFormatter = staticCompositionLocalOf<TimeFormatter> {
    error("TimeFormatter not provided")
}
