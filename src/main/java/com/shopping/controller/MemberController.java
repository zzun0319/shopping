package com.shopping.controller;

import com.shopping.controller.form.MemberJoinForm;
import com.shopping.repository.MemberRepository;
import com.shopping.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    /**
     * 회원가입 페이지로 이동
     */
    @GetMapping("/add")
    public String joinForm(Model model){
        log.info("joinForm으로 이동");
        model.addAttribute("form", new MemberJoinForm());
        log.info("joinForm으로 이동2");
        return "member/join-form";
    }


}
