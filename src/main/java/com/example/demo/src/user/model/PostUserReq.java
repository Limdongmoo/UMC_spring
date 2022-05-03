package com.example.demo.src.user.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@JsonAutoDetect
public class PostUserReq {
    public PostUserReq(@JsonProperty("name") String name,
                       @JsonProperty("nickName")String nickName,
                       @JsonProperty("phone")String phone,
                       @JsonProperty("email")String email,
                       @JsonProperty("password")String password) {
        this.name = name;
        this.nickName = nickName;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    private String name;
    private String nickName;
    private String phone;
    private String email;
    private String password;



}
