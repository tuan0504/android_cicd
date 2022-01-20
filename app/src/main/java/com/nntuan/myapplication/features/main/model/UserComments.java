package com.nntuan.myapplication.features.main.model;

import java.util.List;

public class UserComments {
    public List<String> mentions;
    public List<LinkUrl> links;

    public UserComments(List<String> mentions, List<LinkUrl> links) {
        this.mentions = mentions;
        this.links = links;
    }
}
