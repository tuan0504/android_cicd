package com.nntuan.myapplication.features.main.model;

import com.nntuan.myapplication.core.Status;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URLDecoder;

import static java.nio.charset.StandardCharsets.UTF_8;

public class LinkUrl {
    public String url;
    public String title;

    public LinkUrl(String url) {
        this.url = url;
        this.title = getTitleFromUrl(url);
    }

    private String getTitleFromUrl(String url) {
        try {
            return Jsoup.connect(url).get().title();
        } catch (IOException e) {
            e.printStackTrace();
            return Status.ERROR.name();
        }
    }
}
