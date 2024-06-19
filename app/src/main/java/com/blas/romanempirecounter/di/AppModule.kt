package com.blas.romanempirecounter.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


/**
 *  SingletonComponent lo que hace es que todas las depencencias tengan un tiempo de vida igual
 *  al de la app
 *
 *  En este proyecto por ahora no se va a usar -> lo dejo por seguir siempre los mismos procesos
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {}