package ducletran.tech.imutransformer.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ducletran.tech.imutransformer.data.database.AppDatabase
import ducletran.tech.imutransformer.data.database.dao.LabelDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideLabelDao(appDatabase: AppDatabase): LabelDao {
        return appDatabase.labelDao()
    }
}