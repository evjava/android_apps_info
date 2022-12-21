package com.evjava.apps_info.utils

import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.observable.asObservable
import com.badoo.reaktive.observable.observableOfEmpty
import com.badoo.reaktive.observable.toObservable

object RxUtils {
    inline val <T> T?.wrapObservable: Observable<T>
        get() = this?.toObservable() ?: observableOfEmpty()

    inline val <T> List<T>.wrapObservable: Observable<T>
        get() = when {
            isEmpty() -> observableOfEmpty()
            else -> this.asObservable()
        }
}