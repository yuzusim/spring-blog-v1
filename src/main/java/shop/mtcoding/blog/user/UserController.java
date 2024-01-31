package shop.mtcoding.blog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.Reader;

/**
 * 컨트롤러의 책임
 * 1. 요청받기 (URL - URI포함)
 * 2. http body는 어떻게? (DTO)
 * 3. 기본 mime 전략 : x-www-form-urlencoded (username=ssar&password=1234)
 * 4. 유효성 검사 (body 데이터가 있다면)
 * 5. 클라이언트 view만 원하는지? 혹은 DB 처리 후 view도 원하는지?
 * 6. view만 원하면 view를 응답하면 끝
 * 7. DB 처리를 원하면 model(DAO)에게 위임후 view를 응답하면 끝
 *
 */

@RequiredArgsConstructor
@Controller
public class UserController {


    private final UserRepository userRepository;
    private final HttpSession session;

//    public UserController(UserRepository userRepository) {
//        System.out.println("풀 생성자 UserController");
//        this.userRepository = userRepository;
//    }

    @PostMapping("/login") // @PostMapping login만 예외임
    public String login(UserRequest.loginDTO requestDTO){
        System.out.println(requestDTO);

        // 1. 유효성 검사
        if (requestDTO.getUsername().length() < 3) {
            return "error/400";
        }

        // 2. 모델 필요 (위임)  select * from user_tb where username=? and password=?
        User user = userRepository.findByUsernameAndPassword(requestDTO);

        if (user == null) {
            return "error/401";
        }else {
            // 3. 응답
            session.setAttribute("sessionUser", user);
            return "redirect:/";
        }

        // 유저가 null 이면 error 페이지로
        // 유저가 null 아니면 Session


        //System.out.println(user);

        // 3. 응답
//        return "redirect:/";



    }

    @PostMapping("/join")
    public String join(UserRequest.joinDTO requestDTO) {
        System.out.println(requestDTO);

        // 1. 유효성 검사
        if (requestDTO.getUsername().length() < 3) {
            return "error/400";
        }

        // 2.model에게 위임하기
        //userRepository.save(requestDTO);
        userRepository.save(requestDTO);


        // DB insert
        return "redirect:/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}
