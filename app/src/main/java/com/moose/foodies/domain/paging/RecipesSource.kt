package com.moose.foodies.domain.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.domain.models.SearchData
import com.moose.foodies.domain.repositories.RecipeRepository

class RecipesSource(val data: SearchData, val repository: RecipeRepository): PagingSource<Int, Recipe>() {

    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        Log.d("Page source", "getRefreshKey: getting the anchor key")
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        Log.d("Page source", "load: Page source is called")
        return try {
            val nextPage = params.key ?: 1
            val result = repository.searchRecipe(nextPage, data)

            LoadResult.Page(
                data = result.recipes,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (result.recipes.isEmpty()) null else nextPage + 1
            )
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}