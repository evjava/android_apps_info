package com.evjava.apps_info.api

import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.subject.publish.PublishSubject

interface ScreenControllerI : ApperContextI {
    val title: Observable<String>
    val news: PublishSubject<Message>

    fun onNews(news: Message)
}