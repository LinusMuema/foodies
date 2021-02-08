package com.moose.foodies.features.feature_recipe.domain

import com.moose.foodies.features.feature_home.domain.HomeData
import com.moose.foodies.features.feature_home.domain.Recipe
import io.reactivex.Flowable
import io.reactivex.Single

fun Flowable<HomeData>.toPresentation(id: Int): Single<Recipe> {
    return this.map { data -> data.recipes.first { it.id == id } }.firstOrError()!!
}

fun Single<List<Recipe>>.toPresentation(): Single<Boolean> {
    return this.map { it.isNotEmpty() }
}