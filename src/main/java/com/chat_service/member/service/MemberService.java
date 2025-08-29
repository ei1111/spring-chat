package com.chat_service.member.service;

import com.chat_service.member.dto.MemberRequest;
import com.chat_service.member.dto.MemberResponse;
import com.chat_service.member.entity.Member;
import com.chat_service.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResponse save(MemberRequest memberRequest) {
        memberRequest.passwordEncoding(passwordEncoder);
        return memberRepository.save(memberRequest.toMemberEntity()).toResponse();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다"));
    }

    public Member findByUserId(String userId) {
        return memberRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException(userId));
    }
}
