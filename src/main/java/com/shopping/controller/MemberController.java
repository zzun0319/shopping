package com.shopping.controller;

import com.shopping.api.dtos.MemberDto;
import com.shopping.controller.form.MemberJoinForm;
import com.shopping.controller.form.MemberLoginForm;
import com.shopping.domain.Member;
import com.shopping.repository.MemberRepository;
import com.shopping.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
        model.addAttribute("joinForm", new MemberJoinForm());
        return "member/join-form";
    }

    @PostMapping("/add")
    public String join(@Validated @ModelAttribute("joinForm") MemberJoinForm joinForm, BindingResult bindingResult) {

        if(memberService.checkDuplicateId(joinForm.getLoginId())){
            bindingResult.rejectValue("loginId", "duplicate.loginId");
        }

        if (bindingResult.hasErrors()) {
            return "member/join-form";
        }

        memberService.join(joinForm);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") MemberLoginForm loginForm){
        return "member/login-form";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginForm") MemberLoginForm loginForm, BindingResult bindingResult,
                        HttpServletRequest request, @RequestParam(defaultValue = "/") String redirectURL) {

        Member member = memberService.login(loginForm);

        if(member == null){
            bindingResult.reject("fail.login");
        }

        if(bindingResult.hasErrors()){
            return "member/login-form";
        }

        MemberDto memberDto = new MemberDto(member);

        HttpSession session = request.getSession();
        session.setAttribute("loginMember", memberDto);
        session.setMaxInactiveInterval(1800); // 30분간 아무 요청 없으면 세션만료

        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false); // 있으면 가져오고, 없다고 새로 만들지 말고.

        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }


}
