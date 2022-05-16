package com.example.demo.src.post.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter

public class PostPostsReq {
    private int userIdx;
    private String content;
    private List<PostImgUrls> postImgUrls;

    public PostPostsReq() {
    }

    public PostPostsReq(int userIdx, String content, List<PostImgUrls> postImgUrls) {
        this.userIdx = userIdx;
        this.content = content;
        this.postImgUrls = postImgUrls;
    }
}
