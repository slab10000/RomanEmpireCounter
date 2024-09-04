package com.blas.romanempirecounter.di

import android.app.Application
import androidx.room.Room
import com.blas.romanempirecounter.data.local.database.AppDatabase
import com.blas.romanempirecounter.data.local.database.AppDatabase.Companion.DATABASE_NAME
import com.blas.romanempirecounter.data.repository.DayRepositoryImpl
import com.blas.romanempirecounter.domain.repository.DayRepository
import com.blas.romanempirecounter.domain.usecase.GetLastSevenDaysUseCase
import com.blas.romanempirecounter.domain.usecase.GetLastSevenDaysUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 *  SingletonComponent lo que hace es que todas las depencencias tengan un tiempo de vida igual
 *  al de la app
 *
 *  En este proyecto por ahora no se va a usar -> lo dejo por seguir siempre los mismos procesos
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDayDataBase(app: Application): AppDatabase{
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesDayRepository(db: AppDatabase): DayRepository{ //en lugar de pasarle el DAO le pasamos la base de datos directamente
        return DayRepositoryImpl(db.dayDao)
    }

    @Provides
    @Singleton
    fun provideGetLastSevenDaysUseCase(repository: DayRepository): GetLastSevenDaysUseCase {
        return GetLastSevenDaysUseCaseImpl(repository)
    }

}