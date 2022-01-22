package com.nntuan.myapplication.features.main.model;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.nntuan.myapplication.core.Status;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class LinkUrlTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
    }

    @Test
    public void createInstance() {
        String url = "https://www.linkedin.com/";
        LinkUrl data = new LinkUrl(url);
        assertNotEquals("", data.title);
        assertNotEquals(Status.ERROR.name(), data.title);
    }
}
