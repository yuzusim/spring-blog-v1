package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



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
    // Ioc 컨테이너에 세션에 접근할수 있는 변수가들어가 있음, DI하면 됨
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

        // 2. 동일 Username 체크 -> 나중에 하나의 트랜잭션으로 묶기
        // 트라이캐치 -> 어떤 에러를 돌려 줘야 할지 판단하기 어렵다.
        // 내가 코드 구현
        User  user = userRepository.findByUsername(requestDTO.getUsername());
        if (user == null) {
            // 3. model에게 위임하기
            userRepository.save(requestDTO);
        }else {
            return "error/401";
        }
        
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
        session.invalidate(); // 1번 서랍안에 있는 내용을 다 삭제
        // 어트리뷰트 키 값만 날라가므로 invalidate()를 써서 서랍안을 다 비운다.
        // 1.
        // 2. 서버가 서랍을 비운다. -> 내가 키를 들고 있어서 접근해도 아무 것도 없다.
        // 세션 저장소 유효기간 30분 -> 길지 않게 메모리를 많이 잡아 먹음
        // 세션 저장소 : 웹.엑스엠엘 설정가능하지만
        // 애플리케이션에서 설정 해줄 수 있다.
        //

        return "redirect:/";
    }
}
