package com.moose.foodies.features.feature_favorites.domain

import com.moose.foodies.features.feature_home.domain.Recipe
import kotlinx.serialization.Serializable

@Serializable
data class Backup(val recipes: List<Recipe>)

@Serializable
data class BackupStatus(val status: String)
