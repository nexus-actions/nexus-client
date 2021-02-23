package com.github.nexus.actions.agent

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable


@Serializable
data class Data<T>(val data: List<T>)

@Serializable
data class Profile(val id: String, val name: String)