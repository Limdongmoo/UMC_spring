package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;


    public UserController(UserProvider userProvider, UserService userService) {
        this.userProvider = userProvider;
        this.userService = userService;
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse<GetUserFeedRes> getUserFeed(@RequestParam("userIdx") int userIdx) {
        try{
            GetUserFeedRes getUserFeedRes= userProvider.retrieveUserFeed(userIdx);
            return new BaseResponse<>(getUserFeedRes);
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }

    }

    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        if (postUserReq.getEmail() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_EMPTY_EMAIL);
        }

        if (!isRegexEmail(postUserReq.getEmail())) {
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_EMAIL);
        }
        try {
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/{userIdx}") // (PATCH) 127.0.0.1:9000/users/:userIdx
    public BaseResponse<String> modifyUserName(@PathVariable("userIdx") int userIdx, @RequestBody User user) {
        try {

            PatchUserReq patchUserReq = new PatchUserReq(userIdx, user.getNickName());
            userService.modifyUserName(patchUserReq);

            return new BaseResponse<>("닉네임 변경 성공");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/{userIdx}/status")
    public BaseResponse<String> deleteUser(@PathVariable("userIdx") int userIdx) {
        try {
            DeleteUserReq deleteUserReq = new DeleteUserReq(userIdx);
            userService.deleteUser(deleteUserReq);
            return new BaseResponse<>("데이서 삭제(status=INACTIVE) 성공");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


}
