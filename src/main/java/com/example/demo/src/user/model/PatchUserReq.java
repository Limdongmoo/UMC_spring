package com.example.demo.src.user.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@JsonAutoDetect
public class PatchUserReq {
    private int userIdx;
    private String nickName;

    public PatchUserReq(@JsonProperty("userIdx") int userIdx,
                        @JsonProperty("nickName") String nickName) {

        this.userIdx = userIdx;
        this.nickName = nickName;
    }

}
