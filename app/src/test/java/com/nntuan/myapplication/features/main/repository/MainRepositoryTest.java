package com.nntuan.myapplication.features.main.repository;

import android.content.Context;

import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.nntuan.myapplication.core.AppExecutors;
import com.nntuan.myapplication.core.Resource;
import com.nntuan.myapplication.core.Status;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class MainRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    Context context;

    public MainRepository repository;

    @Before
    public void setup() {
        AppExecutors executors = Mockito.mock(AppExecutors.class);
        Mockito.when(executors.diskIO()).thenReturn(ArchTaskExecutor.getIOThreadExecutor());
        Mockito.when(executors.mainThread()).thenReturn(ArchTaskExecutor.getIOThreadExecutor());
        repository = new MainRepositoryImp(executors, context);
    }

    @Test
    public void getJson_succcess() {
        Observer<Resource<String>> observer =Mockito.spy(new Observer<Resource<String>>() {
            @Override
            public void onChanged(Resource<String> stringResource) {
            }
        });
        String input = "https://www.linkedin.com/\n@test 3reewr";
        String output= "{\"mentions\":[\"test\"],\"links\":[{\"url\":\"https://www.linkedin.com/\",\"title\":\"LinkedIn: Log In or Sign Up\"}]}";

        LiveData<Resource<String>> liveData = repository.getJson(input);
        liveData.observeForever(observer);
        assertEquals(liveData.getValue().getStatus(), Status.SUCCESS);
        assertEquals(liveData.getValue().getData(), output);
    }
}
