package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.dto.SigninReqDto;
import com.koreait.spring_boot_study.dto.SignupReqDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    /*
        @RequestParam
        클라이언트가 URL 쿼리스트링으로 넘긴 값을 메소드 파라미터로 전달
     */

    @GetMapping("/get")
    public String getUser(@RequestParam String userId) {
        System.out.println("RequestParam 으로 들어온 값 : " + userId);
        return "RequestParam 으로 들어온 값 : " + userId;
    }

    @GetMapping("/get/name")
    public String getUsername(@RequestParam(value = "name", defaultValue = "홍길동") String username, @RequestParam(required = false) Integer age) { // value 를 적어서 보내면 name 으로 키를 받음
        System.out.println(username + age);
        return username + age;
    }

    @GetMapping("/get/names")
    public String getUsernames(@RequestParam List<String> names) {
        return names.toString();
    }

    // RequestParam 주의사항
    // 파라미터가 없으면 500에러
    // 타입이 안 맞을 때
    // 이름이 불일치 할 때
    // 실무 Tip. 절대로 민감한 정보를 쓰지 않아야 함!!!
    // Get 일 때만 RequestParam 으로 받는다.

    // 요청 주소는 /search => name, email
    // name 는 필수x, email은 기본값으로 no-email
    // 요청 => /auth/search?name=lee
    // 반환 => "검색 조건 - 이름: ***, 이메일: ***"

    @GetMapping("/search")
    public String searchUser(@RequestParam(required = false) String name, @RequestParam(defaultValue = "no-email") String email) {
        return "검색 조건 - 이름: " + name + ", 이메일: " + email;
    }

    // RequestBody
    // HTTP 요청의 바디에 들어있는 JSON 데이터를 자바 객체(DTO)로 변환해서 주입해주는 어노테이션
    // 클라이언트가 JSON 형식으로 데이터 보냄
    // 백엔드 서버는 그 JSON 을 @RequestBody 가 붙은 DTO 로 자동 매핑
    // 일반적으로 POST, PUT, PATCH 에서 사용

    // DTO (Data Transfer Object)
    // 데이터를 전달하기 위한 객체
    // 클라이언트간에 데이터를 주고 받을 때 사용하는 중간 객체

    @PostMapping("/signup")
    public String signup(@RequestBody SignupReqDto signupReqDto) {
        System.out.println(signupReqDto);

        return signupReqDto.getUsername() + "님 회원가입이 완료되었습니다.";
    }

    // Post 요청 signin 로그인 로직
    // SigninRequestDto => email, password
    // 반환 "로그인 완료 : " + signinRequestDto.getEmail() + "님 반갑습니다.

    @PostMapping("/signin")
    public String signin(@RequestBody SigninReqDto signinReqDto) {
        System.out.println(signinReqDto);
        return "로그인 완료 : " + signinReqDto.getEmail() + "님 반갑습니다.";
    }
}

// 안에서 사용하는 변수명과 쿼리스트링의 키 값이 다를 경우 괄호 안에 표기해주면 됨
// 그리고 기본 값도 설정이 가능함 (defaultValue)
// 그리고 다른 타입도 가능하며 여러 개의 RequestParam 도 받을 수 있다
// int 는 null 을 허용 안 하기 때문에 값이 없음의 상태가 될 수 있다
// 그래서 required = false 를 했지만 에러가 뜸 => Integer 로 해야 null 로 받을 수 있다
// 만약 필수값이 false 이고 기본 값이 설정되어 있다면 필수 값 설정이 무의미하다
// int = null 을 허용하지 않음, Integer 은 값이 없으면 null 로 변환됨
//  int 는 서버가 멈춰버리는 상황이 발생할 수도 있다. 숫자같은 건 Integer 로 받거나 제일 좋은 건 String 으로 받아서 바꾸는 게 낫다!!