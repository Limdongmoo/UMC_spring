package com.example.demo.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor

public class GetPostRes {
    private String nickName;
    private int userIdx;
    private int postIdx;
    private String content;
    private String updatedAt;
    private String ImgUrl;
    private int goodCount;
    private int commentCount;
    private List<GetPostImg> getPostImg;

}
