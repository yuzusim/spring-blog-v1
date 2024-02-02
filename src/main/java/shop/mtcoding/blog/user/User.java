package shop.mtcoding.blog.user;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_tb")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String username;

    @Column(length = 60, nullable = false)
    private String password;
    private String email;

    private String hobby;
    // 유저가 취미가 여러개이면 스칼라가 아니고 오브젝트이기 때문에 테이블을 쪼개야 한다.


    @CreationTimestamp
    private LocalDateTime createdAt;
}
