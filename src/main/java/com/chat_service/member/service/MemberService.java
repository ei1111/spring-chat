package com.chat_service.member.service;

import com.chat_service.member.dto.MemberRequest;
import com.chat_service.member.dto.MemberResponse;
import com.chat_service.member.entity.Member;
import com.chat_service.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse save(MemberRequest memberRequest) {
        return memberRepository.save(memberRequest.toMemberEntity()).toResponse();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다"));
    }
}
