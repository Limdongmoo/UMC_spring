package com.example.demo.src.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetUserInfoRes {
    private String name;
    private String nickName;
    private String profileImgUrl;
    private String Introduce;
    private String website;
    private int postCount;
    private int followerCount;
    private int followeeCount;

    public GetUserInfoRes(String name, String nickName, String profileImgUrl, String introduce, String website, int postCount, int followerCount, int followeeCount) {
        this.name = name;
        this.nickName = nickName;
        this.profileImgUrl = profileImgUrl;
        Introduce = introduce;
        this.website = website;
        this.postCount = postCount;
        this.followerCount = followerCount;
        this.followeeCount = followeeCount;
    }
}
