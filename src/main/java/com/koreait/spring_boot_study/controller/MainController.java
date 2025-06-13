package com.koreait.spring_boot_study.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
class UserDto {
    private int userId;
    private String username;
    private int age;
}

// controller => SSR
// 즉 서버쪽에서 웹페이지를 렌더링해서 반환하는 SSR
@Controller
public class MainController {

    private List<UserDto> users = signupSubmit(); // 이거 복사 해와야 함
    // 이러한 방식은 정적 웹페이지를 보여주는 것
    // 데이터 즉 동적인 요소가 없는 정적 웹페이지
    @GetMapping("/main")
    public String getMain() {
        return "main.html";
    }

    // SSR 에 동적을 추가하면 Thymeleaf 를 적용하면 된다.
    // html 파일은 템플릿 패키지 폴더에 있어야 한다.
    // Thymeleaf
    // 서버에서 HTML 을 렌더링 할 때, 자바 데이터를 끼워 넣을 수 있게 해주는 템플릿 엔진

    @GetMapping("/profile")
    public String getProfile(Model model) {
        model.addAttribute("username", "<b>송다빈</b>");
        model.addAttribute("isAdult", true);
        model.addAttribute("age",19);
        Map<String, String> userList = new HashMap<>();
        userList.put("이동윤", "27");
        userList.put("삼동윤", "18");
        userList.put("사동윤", "44");
        userList.put("오동윤", "36");
        model.addAttribute("userList", userList);
        return "profile.html";
    }

    @GetMapping("/search")
    public String getSearch(@RequestParam String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        return "search.html";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup.html";
    }

    @PostMapping("/signup") // 요청 주소가 같아도 됨. 메소드가 다르니까 별개로 처리 됨.
    public String signupSubmit(@RequestParam String name, @RequestParam int age, Model model) {
        UserDto userDto = new UserDto(users.size()+1, name, age);
        users.add(UserDto);
        model.addAttribute("message", name + "님, 가입을 환영합니다.");
        return "signup-result";
    }

}
// class 아래에 있는 거 복사해야 함.