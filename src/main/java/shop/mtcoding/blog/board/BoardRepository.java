package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;


import java.math.BigInteger;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;

    public int count(){
        Query query = em.createNativeQuery("select count(*) from board_tb");
        Long count = (long) query.getSingleResult();
        return count.intValue();
    }
    

    public List<Board> findAll(int page){ // 모든 걸 다 조회
        final int COUNT = 3;
        int value = page*COUNT;

        Query query = em.createNativeQuery("select * from board_tb order by id desc limit ?,?", Board.class);
        query.setParameter(1, value);
        query.setParameter(2, COUNT);
        // query.setParameter(2, constant.PAGING_COUNT);

        List<Board> boardList = query.getResultList(); // 여러건
        return boardList;



    }

    public BoardResponse.DetaillDTO findById(int id) {// 리턴 responseDTO 받았으니까 void 자리 BoardResponse.DetaillDTO 변경
        // Entity가 아닌것은 JPA가 파싱 해주지 않는다. -> 조인했으니까
        Query query = em.createNativeQuery("select bt.id, bt.title, bt.content, bt.created_at, bt.user_id, ut.username from board_tb bt inner join user_tb ut  on bt.user_id = ut.id  where bt.id=?;");
        query.setParameter(1, id);
        JpaResultMapper rm = new JpaResultMapper();
        BoardResponse.DetaillDTO responseDTO = rm.uniqueResult(query, BoardResponse.DetaillDTO.class);
        return responseDTO; // 스프링 입장에서 DB랑 통신 리스펀스 DTO


        //query.getSingleResult();

    }

}


//    public int totalc(){
//        Query countQuery = em.createNativeQuery("SELECT COUNT(*) FROM board_tb");
//        int totalCount = ((Number)countQuery.getSingleResult()).intValue();
//        System.out.println("전체 아이템 수: " + totalCount);
//        return totalCount;
//
//    }


//public void findById(int id) {
//    // Entity가 아닌것은 JPA가 파싱 해주지 않는다. -> 조인했으니까
//    Query query = em.createNativeQuery("select bt.id, bt.content, bt.title, bt.created_at, bt.user_id, ut.username  from board_tb bt inner join user_tb ut  on bt.user_id = ut.id  where bt.id=?;");
//    query.setParameter(1, id);
//    JpaResultMapper rm = new JpaResultMapper();
//    BoardResponse.DetaillDTO responseDTO = rm.uniqueResult(query, BoardResponse.DetaillDTO.class);
//    return responseDTO;
//    // 스프링입자에서 DB랑 통신 리스펀스 DTO
//
//    //query.getSingleResult();
//
//}



