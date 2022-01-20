package com.nntuan.myapplication.features.main.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.nntuan.myapplication.core.AppExecutors;
import com.nntuan.myapplication.core.Resource;
import com.nntuan.myapplication.features.main.model.LinkUrl;
import com.nntuan.myapplication.features.main.model.UserComments;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class MainRepositoryImp implements MainRepository {
    private MediatorLiveData<Resource<String>> result = new MediatorLiveData<Resource<String>>();

    Pattern pUrl = Pattern.compile("http[s]?:\\/\\/(www\\.)?[^\\s]?\\/?[^\\s]*");
    Pattern pMentions = Pattern.compile("@[^\\s]*");

    AppExecutors executors;
    Context context;

    @Inject
    public MainRepositoryImp(AppExecutors executors, @ApplicationContext Context context){
        this.executors = executors;
        this.context = context;
    }

    @Override
    public LiveData<Resource<String>> getJson(String value) {
        result.setValue(new Resource.Initial<>());
        executors.diskIO().execute(() -> {
            final LiveData<Resource<String>> data = processInput(value);
            executors.mainThread().execute(() -> {
                result.setValue(data.getValue());
            });
        });
        return result;
    }

    private LiveData<Resource<String>> processInput(String value) {
        List<String> mentions = getMentionsFromInput(value);
        List<LinkUrl> linkUrls = getlinkUrlFromInput(value);

        UserComments comments = new UserComments(mentions, linkUrls);
        String output = (new Gson()).toJson(comments);

        Resource.Success<String> data = new Resource.Success<String>(output);
        return new MutableLiveData<Resource<String>>(data);
    }

    private List<String> getMentionsFromInput(String value) {
        Matcher matcher = pMentions.matcher(value);
        ArrayList<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group().substring(1));
        }
        return list;
    }
    private List<LinkUrl> getlinkUrlFromInput(String value) {
        Matcher matcher = pUrl.matcher(value);
        ArrayList<LinkUrl> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(new LinkUrl(matcher.group()));
        }
        return list;
    }
}
