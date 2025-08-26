package com.chat_service.formLogin.service;

import com.chat_service.chatroom.dto.ChatroomResponse;
import com.chat_service.chatroom.entity.Chatroom;
import com.chat_service.chatroom.repository.ChatroomRepository;
import com.chat_service.formLogin.userDetail.CustomUserDetails;
import com.chat_service.member.dto.MemberRequest;
import com.chat_service.member.dto.MemberResponse;
import com.chat_service.member.entity.Member;
import com.chat_service.member.entity.Role;
import com.chat_service.member.repository.MemberRepository;
import java.awt.print.Pageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConsultantService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final ChatroomRepository chatroomRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByNickName(username).get();

        if (member.getRole() != Role.ROLE_CONSULTANT) {
            throw new AccessDeniedException("상담사가 아닙니다.");
        }

        return new CustomUserDetails(member);
    }

    public MemberResponse save(MemberRequest memberRequest) {
        memberRequest.passwordEncoding(passwordEncoder);
        return memberRepository.save(memberRequest.toMemberEntity()).toResponse();
    }

    //모든 채팅룸 가져오기
    public Page<ChatroomResponse> getChatroomPage(Pageable pageable) {
        return chatroomRepository.findAll(pageable)
                .map(Chatroom::toResponse);
    }
}
