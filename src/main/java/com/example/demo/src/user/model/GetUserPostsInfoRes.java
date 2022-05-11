package com.example.demo.src.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserPostsInfoRes {
    private int postIdx;
    private String postImgUrl;

    public GetUserPostsInfoRes(int postIdx, String postImgUrl) {
        this.postIdx = postIdx;
        this.postImgUrl = postImgUrl;
    }
}
