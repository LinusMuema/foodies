package com.moose.foodies.domain.repositories

import com.moose.foodies.data.local.ItemsDao
import com.moose.foodies.data.local.UserDao
import com.moose.foodies.data.remote.RecipesService
import com.moose.foodies.data.remote.UsersService
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.util.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface FeedRepository {
    val profile: Flow<Profile>
    val chefs: Flow<List<Profile>>
    val recipes : Flow<List<Recipe>>

    suspend fun fetchData()
}

class FeedRepositoryImpl @Inject constructor(val userDao: UserDao, val itemsDao: ItemsDao, val preferences: Preferences): FeedRepository {
    override val chefs: Flow<List<Profile>>
        get() = userDao.getChefs()

    override val profile: Flow<Profile>
        get() = userDao.getProfile()

    override val recipes: Flow<List<Recipe>>
        get() = itemsDao.getFeedRecipes()

    override suspend fun fetchData() {
        val id = profile.first()._id
        val feed = RecipesService.getFeed()
        val chefs = UsersService.discoverUsers()

        userDao.updateChefs(id, chefs)
        itemsDao.updateFeedRecipes(feed)
    }
}
