package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.user.model.DeleteUserReq;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.src.user.model.PostUserReq;
import com.example.demo.src.user.model.PostUserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class UserService {

    private final UserDao userDao;
    private final UserProvider userProvider;


    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider) {
        this.userDao = userDao;
        this.userProvider = userProvider;

    }


    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {

        if(userProvider.checkEmail(postUserReq.getEmail())==1){
            System.out.println("==>postUserReq1 = " + postUserReq.getEmail());
            throw new BaseException(BaseResponseStatus.POST_USERS_EXISTS_EMAIL);
        }
        try{

            int userIdx = userDao.createUser(postUserReq);
            return new PostUserRes(postUserReq.getPassword(), userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_CONNECTION);
        }
    }

    public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
        try{
            int result = userDao.modifyUserName(patchUserReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_CONNECTION);
        }
    }

    public void deleteUser(DeleteUserReq deleteUserReq) throws BaseException {
        try {
            int result = userDao.deleteUser(deleteUserReq);
            if (result == 0) {
                throw new BaseException(DELETE_FAIL_USERNAME);
            }
        } catch (Exception e) {
            throw new BaseException(DATABASE_CONNECTION);
        }
    }
}
