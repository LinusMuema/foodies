package com.moose.foodies.util.extensions

import com.moose.foodies.features.feature_home.domain.Item

fun String.largeImage(): String = this.replace("312x231", "636x393")

fun String.mediumImage(): String = this.replace("312x231", "240x150")

fun List<Item>.clean(): List<Item> = this.toSet().toList()