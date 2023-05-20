package ru.boringowl.itroadmap.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoAuthClient


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthClient