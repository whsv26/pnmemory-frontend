package org.whsv26.pnmemory.di.CustomModule

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.whsv26.pnmemory.api.PnmemoryService
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

  @Singleton
  @Provides
  fun providePnmemoryService(): PnmemoryService = PnmemoryService()

}