package shop.mtcoding.blog.board;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;

@Data // getter, setter, toString
@Entity
@Table(name = "board_tb")
public class Board {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto
    private int id;
    private String title;
    private String content;
    private int userId; //폴링키에 제약조건은 안넣는게 좋음

    @CreationTimestamp
    private LocalDateTime createdAt;
}