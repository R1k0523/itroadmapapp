package ru.boringowl.itroadmap.data.network.base

import kotlinx.serialization.Serializable

@Serializable
class ListResponse<T>(val items: List<T>)
