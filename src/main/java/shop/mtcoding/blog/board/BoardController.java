package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.PagingUtil;
import shop.mtcoding.blog.user.User;

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



    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @GetMapping("/board/{id}") // 깃발
    public String detail(@PathVariable int id, HttpServletRequest request) {

        BoardResponse.DetaillDTO responseDTO = boardRepository.findById(id);
        request.setAttribute("board", responseDTO);

        // 1. 해당 페이지 주인여부
        boolean owner = false;

        // 2. 작성자 userId 확인하기
        int boardUserId = responseDTO.getUserId();

        // 3. 로그인 여부 체크
        User sessionUser = (User) session.getAttribute("swssionUser");
        if (sessionUser != null) { // 로그인 했고
            if (boardUserId == sessionUser.getId()) {
                owner =true;
            }
        }

        request.setAttribute("owner", owner);
        return "board/detail";


    }
}



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


