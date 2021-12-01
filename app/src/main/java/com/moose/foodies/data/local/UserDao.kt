package com.moose.foodies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moose.foodies.domain.models.Profile
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("select * from profile limit 1")
    fun getProfile(): Flow<Profile>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProfile(profile: Profile)

    @Query("delete from profile")
    suspend fun nukeProfile()
}