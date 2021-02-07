package com.moose.foodies.features.feature_home

import com.moose.foodies.local.FoodiesDao
import com.moose.foodies.remote.ApiEndpoints
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

interface HomeRepository {

    fun getRemoteData(): Single<HomeData>

    fun getLocalData(): Flowable<HomeData>

    fun updateLocalData(data: HomeData): Completable

}

class HomeRepositoryImpl @Inject constructor(
    private val api: ApiEndpoints,
    private val dao: FoodiesDao
): HomeRepository {

    override fun getRemoteData(): Single<HomeData> = api.getRecipes()

    override fun getLocalData(): Flowable<HomeData> = dao.getHomeData()

    override fun updateLocalData(data: HomeData): Completable = dao.updateHomeData(data)
}