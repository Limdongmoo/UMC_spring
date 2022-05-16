package com.example.demo.config;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청성공

     */
    SUCCESS(true,1000,"요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */
    //Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요"),

    //users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요"),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),

    // [POST} / posts
    TOO_LONG_CONTENT(false, 2030, "게시물의 길이가 450을 초과합니다."),
    MUST_INSERT_IMAGE(false, 2031, "게시물 이미지를 추가해주십시오"),
    TOO_MANY_IMAGE(false, 2032, "게시물의 이미지가 너무 많습니다."),
    INSERT_IMAGE_ERROR(false, 2033, "게시물 이미지 삽입에 실패하였습니다."),
    INSERT_POST_ERROR(false, 2034, "게시물 생성에 실패하였습니다."),
    NOT_MODIFIED(false, 2035, "게시물이 수정되지 않았습니다."),


    /**
     * 3000 : Response 오류
     */

    //Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패했습니다."),


    // [POST] /users
    DUPLICATED_EMAIL(false, 3010, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false, 3011, "없는 아이디거나 비밀번호가 틀렸습니다."),
    //보안을 위해 POST로

    /**
     * 4000 : Database, Server 오류
     */

    DATABASE_CONNECTION(false, 4000, "데이터베이스 연결에 실패했습니다."),
    NOT_EXIST_IN_DATABASE(false, 4001, "검색결과가 존재하지 않습니다."),

    MODIFY_FAIL_USERNAME(false,4002, "닉네임변경에 실패했습니다."),
    DELETE_FAIL_USERNAME(false, 4003, "데이터삭제에 실패했습니다."),
    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
