package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.user.model.GetUserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public GetUserRes getUsersByIdx(int userIdx) throws BaseException {
        try{
            GetUserRes getUserRes = userDao.getUserByIdx(userIdx);
            return getUserRes;
        }
        catch(Exception exception){
            throw new BaseException(BaseResponseStatus.NOT_EXIST_IN_DATABASE);
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


