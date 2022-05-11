package com.example.demo.src.post;
import com.example.demo.src.post.model.GetPostImg;
import com.example.demo.src.post.model.GetPostRes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDao {

    private final JdbcTemplate jdbcTemplate;
    private List<GetPostImg> getPostImg;

    public PostDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<GetPostRes> selectPostListByUserIdx(int userIdx) {
        String selectPostListQuery = "SELECT u.nickName,p.userIdx,p.postIdx,p.content,p.updatedAt, pu1.ImgUrl,\n" +
                "       IF(numGood IS NULL , 0 , numGood) as goodCount,\n" +
                "       IF(commentCount IS NULL,0,commentCount) as commentCount\n" +
                "FROM Post p\n" +
                "    JOIN(SELECT pu.postIdx,pu.ImgUrl\n" +
                "         FROM PostingUrl as pu\n" +
                "         WHERE pu.status = 'ACTIVE'\n" +
                "         GROUP BY pu.postIdx) pu1 on pu1.postIdx = p.postIdx\n" +
                "    JOIN(SELECT postIdx, COUNT(lgtmIdx) as numGood\n" +
                "         FROM LGTM\n" +
                "         GROUP BY LGTM.postIdx) lg on lg.postIdx = p. postIdx\n" +
                "    JOIN(SELECT userIdx, nickName\n" +
                "         FROM User\n" +
                "         WHERE status='ACTIVE') u on u.userIdx = p.userIdx\n" +
                "    LEFT JOIN(SELECT postIdx, commentsIdx,COUNT(commentsIdx) as commentCount\n" +
                "            FROM Comment\n" +
                "            WHERE status = 'ACTIVE'\n" +
                "            GROUP BY postIdx) c on c.postIdx = p.postIdx\n" +
                "WHERE p.status = 'ACTIVE' and (p.userIdx IN (SELECT f.followeeIdx\n" +
                "FROM User as u\n" +
                "    JOIN(SELECT followerIdx,followeeIdx\n" +
                "         FROM Follow\n" +
                "         WHERE status = 'ACTIVE'\n" +
                "         ) f on f.followerIdx=u.userIdx\n" +
                "WHERE u.userIdx =?) or u.userIdx)\n" +
                "order by updatedAt desc";
        int selectPostListParam = userIdx;
        return jdbcTemplate.query(selectPostListQuery,
                (rs, rowNum) -> new GetPostRes(
                        rs.getString("nickName"),
                        rs.getInt("userIdx"),
                        rs.getInt("postIdx"),
                        rs.getString("content"),
                        rs.getString("updatedAt"),
                        rs.getString("ImgUrl"),
                        rs.getInt("goodCount"),
                        rs.getInt("commentCount"),
                        getPostImg = this.jdbcTemplate.query("SELECT pi.postingUrlIdx,pi.ImgUrl\n" +
                                        "FROM PostingUrl as pi\n" +
                                        "    JOIN Post as p on p.postIdx = pi.postIdx\n" +
                                        "WHERE pi.status = 'ACTIVE' and p.postIdx = ? order by p.createdAt asc",
                                (rk, rowNum2) -> new GetPostImg(
                                        rk.getInt("postingUrlIdx"),
                                        rk.getString("ImgUrl")
                                ), rs.getInt("postIdx"))
                ), selectPostListParam);
    }

    public int checkUserExist(int userIdx){
        String checkUserExistQuery = "select exists(select userIdx from User where userIdx=?)";
        int checkUserExistParam = userIdx;
        return jdbcTemplate.queryForObject(checkUserExistQuery, int.class,checkUserExistParam);
    }
}
