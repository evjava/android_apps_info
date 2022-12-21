package com.evjava.apps_info.api

import com.badoo.reaktive.observable.Observable

interface SearchControllerI {
    val search: Observable<SearchState>

    fun onSearchNews(newSearch: SearchState)
}