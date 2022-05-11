package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.post.model.GetPostListRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostDao postDao;
    private final PostProvider postProvider;
    private final PostService postService;

    @Autowired
    public PostController(PostDao postDao, PostProvider postProvider, PostService postService) {
        this.postDao = postDao;
        this.postProvider = postProvider;
        this.postService = postService;
    }

    @GetMapping("")
    @ResponseBody
    public BaseResponse<GetPostListRes> GetPostList(@RequestParam("userIdx") int userIdx){
        try {
            GetPostListRes getPostListRes = postProvider.retrievePostList(userIdx);
            return new BaseResponse<>(getPostListRes);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
