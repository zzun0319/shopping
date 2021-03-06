package com.shopping.api;

import com.shopping.api.dtos.MemberDto;
import com.shopping.domain.Member;
import com.shopping.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberRepository memberRepository;

    /**
     * 멤버들의 리스트 조회하기
     * @param pageable
     * @return
     */
    @GetMapping("/members")
    public Page<MemberDto> memberList(@PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        Page<MemberDto> dtoPage = page.map(MemberDto::new);
        return dtoPage;
    }
}
