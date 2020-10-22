package com.example.ch6_dependencyinjection.di.qualifier

import javax.inject.Qualifier

//커스텀 어노테이션
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppHash
