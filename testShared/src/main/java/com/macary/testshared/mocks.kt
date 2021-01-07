package com.macary.testshared

import com.macary.domain.News

/**
 * Created by Carlos Farfan on 7/01/2021.
 */
val fakeNews = News(
    1,
    "Donate to Signal",
    "gbil - 32m",
    "https://signal.org/donate/",
    false
)

val fakeNewsList = listOf(
    fakeNews.copy(1),
    fakeNews.copy(2),
    fakeNews.copy(3),
    fakeNews.copy(4)
)