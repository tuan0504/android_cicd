package com.nntuan.myapplication.features.main.di;

import com.nntuan.myapplication.features.main.repository.MainRepository;
import com.nntuan.myapplication.features.main.repository.MainRepositoryImp;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.scopes.ViewModelScoped;

@Module
@InstallIn(ViewModelComponent.class)
final class MainViewModelModule {

    @Provides
    @ViewModelScoped
    static MainRepository provideMainRepository(MainRepositoryImp repo) {
        return repo;
    }
}
