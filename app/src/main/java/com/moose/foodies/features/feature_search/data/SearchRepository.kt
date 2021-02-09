package com.moose.foodies.features.feature_search.data

import com.moose.foodies.features.feature_search.domain.SearchResults
import com.moose.foodies.remote.ApiEndpoints
import io.reactivex.Single
import javax.inject.Inject

interface SearchRepository {
    fun searchRecipe(query: String): Single<SearchResults>
}

class SearchRepositoryImpl @Inject constructor(private val api: ApiEndpoints): SearchRepository{

    override fun searchRecipe(query: String): Single<SearchResults> = api.searchRecipes(query)

}