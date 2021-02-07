package com.moose.foodies.features.home

import com.moose.foodies.db.FoodiesDao
import com.moose.foodies.network.ApiEndpoints
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

interface HomeRepository {

    fun getRemoteData(): Single<HomeData>

    fun getLocalData(): Flowable<List<HomeData>>

    fun updateLocalData(data: HomeData): Completable

}

class HomeRepositoryImpl @Inject constructor(
    private val api: ApiEndpoints,
    private val dao: FoodiesDao
): HomeRepository {

    override fun getRemoteData(): Single<HomeData> = api.getRecipes()

    override fun getLocalData(): Flowable<List<HomeData>> = dao.getHomeData()

    override fun updateLocalData(data: HomeData): Completable = dao.updateHomeData(data)
}