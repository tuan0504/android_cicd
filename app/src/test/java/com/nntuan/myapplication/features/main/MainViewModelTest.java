package com.nntuan.myapplication.features.main;

import android.app.Application;
import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.nntuan.myapplication.R;
import com.nntuan.myapplication.core.Resource;
import com.nntuan.myapplication.features.main.repository.MainRepository;
import com.nntuan.myapplication.features.main.repository.MainRepositoryImp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class MainViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    MainRepository repository = Mockito.mock(MainRepositoryImp.class);
    Context context = Mockito.mock(Application.class);

    MainViewModel model;
    String processing = "Processing...";

    @Before
    public void setup() {
        when(context.getString(R.string.process_user_comments)).thenReturn(processing);
        model = spy(new MainViewModel(repository, context));
    }

    @Test
    public void test_request_success() {
        String input = "https://www.linkedin.com/\n@test 3reewr";
        String output= "{\"mentions\":[\"test\"],\"links\":[{\"url\":\"https://www.linkedin.com/\",\"title\":\"LinkedIn: Log In or Sign Up\"}]}";

        MutableLiveData<Resource<String>> liveData = new MutableLiveData<>(new Resource.Success<>(output));
        liveData.observeForever(new Observer<Resource<String>>() {
            @Override
            public void onChanged(Resource<String> stringResource) {
            }
        });

        Mockito.when(repository.getJson(input)).thenReturn(liveData);
        model.formatJson.observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
            }
        });

        model.requestJsonFromInput(input);
        assertEquals(model.formatJson.getValue(), output);
    }

    @Test
    public void test_request_processing() {
        String input = "https://www.linkedin.com/\n@test 3reewr";
        String output = "";
        MutableLiveData<Resource<String>> liveData = new MutableLiveData<>(new Resource.Loading<>(output));
        liveData.observeForever(new Observer<Resource<String>>() {
            @Override
            public void onChanged(Resource<String> stringResource) {
            }
        });

        Mockito.when(repository.getJson(input)).thenReturn(liveData);
        model.formatJson.observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
            }
        });

        model.requestJsonFromInput(input);
        assertEquals(model.formatJson.getValue(), processing);
    }
}
