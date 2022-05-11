package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.post.model.GetPostListRes;
import com.example.demo.src.post.model.GetPostRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostProvider {

    private final PostDao postDao;

    @Autowired
    public PostProvider(PostDao postDao) {
        this.postDao = postDao;
    }

    public GetPostListRes retrievePostList(int userIdx) throws BaseException {
        try {
            if (postDao.checkUserExist(userIdx) == 0) {
                throw new BaseException(BaseResponseStatus.USERS_EMPTY_USER_ID);
            }
            List<GetPostRes> getPostRes = postDao.selectPostListByUserIdx(userIdx);
            GetPostListRes getPostListRes = new GetPostListRes(getPostRes);
            return getPostListRes;
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_CONNECTION);
        }
    }
}
