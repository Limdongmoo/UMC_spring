package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.post.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.*;

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
    public BaseResponse<GetPostListRes> GetPostList(@RequestParam("userIdx") int userIdx) {
        try {
            GetPostListRes getPostListRes = postProvider.retrievePostList(userIdx);
            return new BaseResponse<>(getPostListRes);

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("")
    @ResponseBody
    public BaseResponse<PostPostsRes> createPost(@RequestBody PostPostsReq postPostsReq) {
        try {
            if (postPostsReq.getContent().length() > 450) {
                return new BaseResponse<>(TOO_LONG_CONTENT);
            }
            if (postPostsReq.getPostImgUrls().size() == 0) {
                return new BaseResponse<>(MUST_INSERT_IMAGE);
            }
            if (postPostsReq.getPostImgUrls().size() > 10) {
                return new BaseResponse<>(TOO_MANY_IMAGE);
            }

            PostPostsRes postPostsRes = postService.createPost(postPostsReq.getUserIdx(), postPostsReq);
            return new BaseResponse<>(postPostsRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PatchMapping("/{postIdx}")
    @ResponseBody
    public BaseResponse<String> modifyPost(@PathVariable("postIdx") int postIdx, @RequestBody PatchPostsReq patchPostsReq) {
        try {
            postService.modifyPost(patchPostsReq.getUserIdx(), postIdx, patchPostsReq);
            return new BaseResponse<>("게시물 정보 수정에 성공하였습니다.");

        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PatchMapping("/{postIdx}/status")
    @ResponseBody
    public BaseResponse<String> deletePost(@PathVariable("postIdx") int postIdx) {
        try {
            postService.deletePost(postIdx);
            return new BaseResponse<>("게시물이 삭제( status = INACTIVE ) 되었습니다.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
