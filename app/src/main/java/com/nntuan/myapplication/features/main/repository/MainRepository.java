package com.nntuan.myapplication.features.main.repository;

import androidx.lifecycle.LiveData;

import com.nntuan.myapplication.core.Resource;

public interface MainRepository {
    LiveData<Resource<String>> getJson(String value);
}