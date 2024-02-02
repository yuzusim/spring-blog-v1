package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@RequiredArgsConstructor
@Controller
public class BoardController {
    private  final HttpSession session;

    @GetMapping({ "/", "/board" })
    public String index() {
        
//        User sessionUser = (User) session.getAttribute("sessionUser");
//
//        if (session == null) {
//            System.out.println("로그인 안된 상태입니다.");
//        }else {
//            System.out.println("로그인 된 상태입니다.");
//        }
        
        return "index";
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
