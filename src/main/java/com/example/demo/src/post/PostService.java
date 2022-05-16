package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.src.post.model.PatchPostsReq;
import com.example.demo.src.post.model.PostImgUrls;
import com.example.demo.src.post.model.PostPostsReq;
import com.example.demo.src.post.model.PostPostsRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class PostService {

    private final PostDao postDao;

    @Autowired
    public PostService(PostDao postDao) {
        this.postDao = postDao;
    }

    public PostPostsRes createPost(int userIdx, PostPostsReq postPostsReq) throws BaseException {
        try {

            int postIdx = postDao.createPost(userIdx,postPostsReq.getContent());
            if(postIdx==0){
                throw new BaseException(INSERT_POST_ERROR);
            }

            for(PostImgUrls e : postPostsReq.getPostImgUrls()) {
                if (postDao.insertImgs(postIdx,e) == 0) {
                    throw new BaseException(INSERT_IMAGE_ERROR);
                }
            }
            return new PostPostsRes(postIdx);
        } catch (Exception e) {
            throw new BaseException(DATABASE_CONNECTION);
        }
    }

    public int modifyPost(int userIdx, int postIdx, PatchPostsReq patchPostsReq) throws BaseException{
        if (patchPostsReq.getContent().length() > 450) {
            throw new BaseException(TOO_LONG_CONTENT);
        }
        //수정 내용 없는 경우 validation
        if (checkPostContentModified(patchPostsReq.getContent(), postIdx)) {
            throw new BaseException(NOT_MODIFIED);
        }
        try {
            return postDao.updatePostContent(postIdx, patchPostsReq);
        } catch (Exception e) {
            System.out.println("checkPostContentModified(patchPostsReq.getContent(), postIdx) = " + checkPostContentModified(patchPostsReq.getContent(), postIdx));
            throw new BaseException(DATABASE_CONNECTION);
        }
    }

    public int deletePost(int postIdx) throws BaseException {
        try {
            return postDao.updatePostStatus(postIdx);
        } catch (Exception e) {
            throw new BaseException(DATABASE_CONNECTION);
        }
    }
    public boolean checkPostContentModified(String content,int postIdx){
        String existContent = postDao.checkPostContentModified(postIdx);
        return existContent.equals(content);
    }
}
