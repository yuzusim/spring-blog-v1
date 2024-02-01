package shop.mtcoding.blog.util;

import org.junit.jupiter.api.Test;

public class PagingTest {

    // 토탈 페이지를 구하시오.
    // 한 페이지에는 3개의 아이템이 담길 수 있습니다.
    // 아이템은 총 8개가 있습니다.
    // 토탈페이지는 몇개가 필요할까요?
    @Test
    public void count(){

        // 토탈 페이지 구하기
        int totalCount = 6; // db 조회해서 count 가져오기
        int pagingCount = 3;

        // 1. 나머지 여부 확인
        int remainCount = totalCount % pagingCount;
        //System.out.println(remainCount);

        int totalPageCount = totalCount / pagingCount;
        //System.out.println(totalPageCount);
        // 2. 나머지가 있다면?
        if(remainCount > 0){
            totalPageCount = totalPageCount + 1;
        }

        System.out.println("토탈페이지 : "+totalPageCount);
    }
}