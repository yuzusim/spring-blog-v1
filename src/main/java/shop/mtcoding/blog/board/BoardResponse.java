package shop.mtcoding.blog.board;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

public class BoardResponse {
    // bt.id, bt.title, bt.content, bt.created_at, bt.user_id, ut.username
    @AllArgsConstructor
    @Data
    public static  class DetaillDTO{
        private Integer id;
        private String title;
        private String content;
        private Timestamp createdAT;
        private Integer userId;
        private String username;


    }
}

//