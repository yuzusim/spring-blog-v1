package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import shop.mtcoding.blog._core.PagingUtil;
import shop.mtcoding.blog.user.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final HttpSession session;
    private final BoardRepository boardRepository;

    // http://localhost:8080?page=0
    @GetMapping({ "/", "/board"})
    public String index(HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {
        //System.out.println("페이지: " +page);
        List<Board> boardList = boardRepository.findAll(page);
        request.setAttribute("boardList", boardList); // 가방에 담음

        int currentPage = page;
        int nextPage = currentPage+1;
        int prevPage = currentPage-1;
        request.setAttribute("nextPage", nextPage);
        request.setAttribute("prevPage", prevPage);


        boolean first = PagingUtil.isFirst(currentPage);
        boolean last = PagingUtil.isLast(currentPage, boardRepository.count());

        request.setAttribute("first", first);
        request.setAttribute("last", last);

        return "index";




        // 이것만 담으면 디세이브를 못한다.

        // 현재 페이지가 퍼스트인지 라스트인지 만든다.
//        boolean first = currentPage == 0 ? true : false ;


//        // 토탈 페이지 구하기
//        int totalCount = 7; // db 조회해서 카운트 가져오기
//        int paging = 3;
//        //int totalPage = 2;
//        int totalPage = totalCount / paging + (totalCount % paging == 0 ? 0 : 1);
//        System.out.println("전체 페이지 수: " + totalPage);
//
//        boolean last = currentPage == (totalPage - 1);
//
//        request.setAttribute("first", first);
//        request.setAttribute("last", last);
//
//        return "index";





//        // 토탈 페이지 구하기
//        int totalCount = 7; // db 조회해서 카운트 가져오기
//        int pagingCount = 3;
//
//        // 1. 나머지 여부확인
//        int remainCount = totalCount % pagingCount;
//        //System.out.println(remainCount);
//
//        int totalPageCount = totalCount / pagingCount;
//        // 2. 나머지 있다면?
//        if (remainCount > 0) {
//
//        }
//
//        // 3. 나머지 없다면?
//        if (remainCount == 0) {
//
//        }






    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @GetMapping("/board/1")
    public String detail() {
        return "board/detail";
    }
}