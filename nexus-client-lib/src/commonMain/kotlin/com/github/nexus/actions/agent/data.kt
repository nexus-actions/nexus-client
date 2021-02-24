package com.github.nexus.actions.agent

import kotlinx.serialization.Serializable


@Serializable
public data class Data<T>(val data: List<T>)

@Serializable
public data class Profile(val id: String, val name: String)