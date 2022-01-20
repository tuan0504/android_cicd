package com.nntuan.myapplication.features.main.model;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LinkUrlTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
    }

    @Test
    public void createInstance() {
        String url = "https://www.linkedin.com/";
        String title = "LinkedIn: Log In or Sign Up";
        LinkUrl data = new LinkUrl(url);
        assertEquals(data.title, title);
    }
}
