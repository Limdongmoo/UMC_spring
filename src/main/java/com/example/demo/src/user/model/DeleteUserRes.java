package com.example.demo.src.user.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class DeleteUserRes {
    private String name;
    private String nickName;
    private String phone;
    private String email;
    private String password;

}
