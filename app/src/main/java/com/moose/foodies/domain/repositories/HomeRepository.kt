package com.moose.foodies.domain.repositories

import com.moose.foodies.data.local.ItemsDao
import com.moose.foodies.data.local.UserDao
import com.moose.foodies.data.remote.ApiEndpoints
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.util.Preferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface HomeRepository {
    val profile: Flow<Profile>
    val feed: Flow<List<Recipe>>
    val chefs: Flow<List<Profile>>

    suspend fun fetchData()
}

class HomeRepositoryImpl @Inject constructor(val api: ApiEndpoints, val userDao: UserDao, val itemsDao: ItemsDao, val preferences: Preferences): HomeRepository {
    override val chefs: Flow<List<Profile>>
        get() = userDao.getChefs()

    override val profile: Flow<Profile>
        get() = userDao.getProfile()

    override val feed: Flow<List<Recipe>>
        get() = itemsDao.getFeedRecipes()

    override suspend fun fetchData() {
        val feed = api.getFeed()
        val chefs = api.getChefs()
        val id = profile.first()._id

        userDao.updateChefs(id, chefs)
        itemsDao.updateFeedRecipes(feed)
    }
}
