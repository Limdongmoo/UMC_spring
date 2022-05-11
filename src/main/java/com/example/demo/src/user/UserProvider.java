package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.user.model.GetUserFeedRes;
import com.example.demo.src.user.model.GetUserInfoRes;
import com.example.demo.src.user.model.GetUserPostsInfoRes;
import com.example.demo.src.user.model.GetUserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.USERS_EMPTY_USER_ID;

@Service
public class UserProvider {

    private final UserDao userDao;

    @Autowired
    public UserProvider(UserDao userDao) {
        this.userDao = userDao;
    }

    public GetUserRes getUsersByEmail(String email) throws BaseException{
        try{
            GetUserRes getUsersRes = userDao.getUsersByEmail(email);
            return getUsersRes;
        }
        catch(Exception exception){
            throw new BaseException(BaseResponseStatus.NOT_EXIST_IN_DATABASE);
        }
    }

    public GetUserFeedRes retrieveUserFeed(int userIdx) throws BaseException {
        try{
            GetUserInfoRes getUserInfoRes = userDao.selectUserInfoByUserIdx(userIdx);
            List<GetUserPostsInfoRes> getUserPostsInfoRes = userDao.selectUserPostInfoByUserIdx(userIdx);
            GetUserFeedRes getUserFeedRes = new GetUserFeedRes(getUserInfoRes,getUserPostsInfoRes);
            return getUserFeedRes;
        }
        catch(Exception e){
            throw new BaseException(USERS_EMPTY_USER_ID);
        }
    }

    public int checkEmail(String email) throws BaseException {
        try {
            return userDao.checkEmail(email);
        }
        catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_CONNECTION);
        }
    }


}


