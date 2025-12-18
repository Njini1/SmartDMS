package com.company.eduboard.domain.user.controller;

import com.company.eduboard.domain.user.dto.request.UserRequest;
import com.company.eduboard.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 로그인 페이지 요청
//    @GetMapping("/login")
//    public String loginPage() {
//        return "user/login";
//    }
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "expired", required = false) String expired,
                            Model model, HttpSession session) {
        if (expired != null) {
            System.out.println("세션 만료로 인한 재로그인 요청");
            model.addAttribute("message", "세션이 만료되었습니다. 다시 로그인해주세요.");
        }

        // 로그인 실패 메시지 세션에서 가져오기
        Object error = session.getAttribute("error");
        if (error != null) {
            model.addAttribute("message", error.toString());
            session.removeAttribute("error"); // 한 번 보여준 뒤 삭제
        }

        return "user/login";
    }

    // 회원가입 페이지 요청
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("userRequest", new UserRequest());
        return "user/register";
    }

    // 회원가입 처리 요청
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute UserRequest userRequest,
                               BindingResult bindingResult,
                               Model model) {
        // 기본 Bean Validation 실패
        if (bindingResult.hasErrors()) {
            return "user/register";
        }

        // 비밀번호 확인 일치 검증
        if (!userRequest.getPassword().equals(userRequest.getPasswordConfirm())) {
            bindingResult.rejectValue(
                    "passwordConfirm",
                    "PASSWORD_MISMATCH",
                    "비밀번호가 일치하지 않습니다."
            );
            return "user/register";
        }

        userService.registerUser(userRequest);
        return "redirect:/users/login";
    }

}
