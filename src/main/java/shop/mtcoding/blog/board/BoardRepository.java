package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;

    public int count(){
        Query query = em.createNativeQuery("select count(*) from board_tb");
        BigInteger count = (BigInteger) query.getSingleResult();
        return count.intValue();
    }


    public List<Board> findAll(int page){ // 모든 걸 다 조회
        final int COUNT = 3;
        int value = page*COUNT;

        Query query = em.createNativeQuery("select * from board_tb order by id desc limit ?,?", Board.class);
        query.setParameter(1, value);
        query.setParameter(2, COUNT);

        List<Board> boardList = query.getResultList(); //여러건
        return boardList;


        // 전체 아이템 수 조회

    }
//    public int totalc(){
//        Query countQuery = em.createNativeQuery("SELECT COUNT(*) FROM board_tb");
//        int totalCount = ((Number)countQuery.getSingleResult()).intValue();
//        System.out.println("전체 아이템 수: " + totalCount);
//        return totalCount;
//
//    }



}


