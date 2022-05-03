package com.example.demo.src.user;

import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.src.user.model.PostUserReq;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Locale;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers() {
        String getUsersQuery = "select userIdx,name,nickName,Email from User";
        return this.jdbcTemplate.query(getUsersQuery,
                UserRowMapper());
    }


    public GetUserRes getUsersByEmail(String email) {
        String getUsersByEmailQuery = "select userIdx,name,nickname,Email from User where Email=?";
        String getUsersByEmailParam = email;
        return this.jdbcTemplate.queryForObject(getUsersByEmailQuery,
                UserRowMapper(), getUsersByEmailParam);
    }

    public GetUserRes getUserByIdx(int userIdx){
        String getUsersByIdxQuery = "select userIdx,name,nickName,Email from User where userIdx = ?";
        int getUsersByIdxParam = userIdx;
        return this.jdbcTemplate.queryForObject(getUsersByIdxQuery,
                UserRowMapper(),getUsersByIdxParam);
    }

    public int createUser(PostUserReq postUserReq) {
        String createUserQuery = "insert into User(name,nickName,phone,Email,password) VALUE(?,?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getName(), postUserReq.getNickName(),postUserReq.getPhone(), postUserReq.getEmail(), postUserReq.getPassword()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int checkEmail(String email) {
        String checkEmailQuery = "select exists(select Email from User where Email=?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class, checkEmailParams);
    }
    public int modifyUserName(PatchUserReq patchUserReq){
        String modifyUserNameQuery = "update User set nickName = ? where userIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getNickName(), patchUserReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }


    @NotNull
    private RowMapper<GetUserRes> UserRowMapper() {
        return (rs, rowNum) -> new GetUserRes(
                rs.getInt("userIdx"),
                rs.getString("name"),
                rs.getString("nickName"),
                rs.getString("Email")
        );
    }
}
