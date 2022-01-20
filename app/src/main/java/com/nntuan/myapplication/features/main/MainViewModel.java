package com.nntuan.myapplication.features.main;

import android.content.Context;
import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.nntuan.myapplication.R;
import com.nntuan.myapplication.core.Status;
import com.nntuan.myapplication.features.main.repository.MainRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private final MainRepository repo;
    private final Context context;

    @Inject MainViewModel(MainRepository repo, @ApplicationContext Context context){
        this.repo = repo;
        this.context = context;
    }

    private MutableLiveData<String> processInput = new MutableLiveData<>();
    public final LiveData<String> formatJson = Transformations
            .switchMap(Transformations.distinctUntilChanged(processInput),
            new Function<String, LiveData<String>>() {
                @Override
                public LiveData<String> apply(String input) {
                    return Transformations.map(repo.getJson(input), ouput -> {
                        if(ouput.getStatus() != Status.SUCCESS) {
                            return context.getString(R.string.process_user_comments) ;
                        } else {
                            return ouput.getData();
                        }
                    });
                }
            });


    public void requestJsonFromInput(String input) {
        processInput.setValue(input);
    }
}