package com.coderio.themoviedb.ui

import com.coderio.themoviedb.rules.MainDispatcherRule
import org.junit.Rule

open class BaseViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
}
