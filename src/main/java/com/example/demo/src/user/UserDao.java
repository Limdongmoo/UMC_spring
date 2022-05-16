package com.example.demo.src.user;

import com.example.demo.src.user.model.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public GetUserRes getUsersByEmail(String email) {
        String getUsersByEmailQuery = "select userIdx,name,nickname,Email from User where Email=?";
        String getUsersByEmailParam = email;
        return this.jdbcTemplate.queryForObject(getUsersByEmailQuery,
                UserRowMapper(), getUsersByEmailParam);
    }

    public GetUserInfoRes selectUserInfoByUserIdx(int userIdx){

        String getUserInfoResQuery = "SELECT User.userIdx,name,nickName,Introduce,website,\n" +
                "       IF(profileImgUrl IS NULL,'BASIC IMG',profileImgUrl) as profileImgUrl,\n" +
                "       IF(postCount IS NULL,0,postCount) as postCount,\n" +
                "       IF(followerCount IS NULL,0,followerCount) as followerCount,\n" +
                "       IF(followeeCount IS NULL,0,followeeCount) as followeeCount\n" +
                "FROM User\n" +
                "    LEFT JOIN(SELECT userIdx,COUNT(postIdx) as postCount\n" +
                "    FROM Post\n" +
                "    WHERE status = 'ACTIVE'\n" +
                "    group by userIdx) p on p.userIdx=User.userIdx\n" +
                "\n" +
                "    LEFT JOIN(SELECT followerIdx,COUNT(followIdx) as followerCount\n" +
                "    FROM Follow\n" +
                "    WHERE status = 'ACTIVE'\n" +
                "    GROUP BY followerIdx) f on f.followerIdx = User.userIdx\n" +
                "\n" +
                "    LEFT JOIN(SELECT followeeIdx,COUNT(followIdx) as followeeCount\n" +
                "    FROM Follow\n" +
                "    WHERE status = 'ACTIVE'\n" +
                "    GROUP BY followeeIdx) f1 on f1.followeeIdx = User.userIdx\n" +
                "WHERE User.userIdx =?;\n";

        int getUserInfoResParam = userIdx;

        return jdbcTemplate.queryForObject(getUserInfoResQuery,
                (rs, rowNum) -> new GetUserInfoRes(
                        rs.getInt("userIdx"),
                        rs.getString("name"),
                        rs.getString("nickName"),
                        rs.getString("profileImgUrl"),
                        rs.getString("Introduce"),
                        rs.getString("website"),
                        rs.getInt("postCount"),
                        rs.getInt("followerCount"),
                        rs.getInt("followeeCount")
                ), getUserInfoResParam);
    }

    public List<GetUserPostsInfoRes> selectUserPostInfoByUserIdx(int userIdx) {
        String getUserPostInfoQuery = "SELECT p.postIdx,pi.ImgUrl as postImgUrl\n" +
                "FROM Post as p\n" +
                "    JOIN User as u on u.userIdx = p.userIdx\n" +
                "    JOIN PostingUrl as pi on pi.postIdx = p.postIdx and pi.status ='ACTIVE'\n" +
                "WHERE p.status = 'ACTIVE' and p.userIdx =?\n" +
                "GROUP BY p.postIdx\n" +
                "order by p.createdAt desc";
        int getUserPostInfoParam = userIdx;

        return jdbcTemplate.query(getUserPostInfoQuery,
                (rs, rowNum) -> new GetUserPostsInfoRes(
                        rs.getInt("postIdx"),
                        rs.getString("postImgUrl")
                )
                , getUserPostInfoParam);
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

    public int deleteUser(DeleteUserReq deleteUserReq) {
        String deleteUserQuery = "update User set status = 'INACTIVE' where userIdx =? ";
        int userIdx = deleteUserReq.getUserIdx();

        return this.jdbcTemplate.update(deleteUserQuery,userIdx);
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
