package com.chat_service.consultant.service;

import com.chat_service.chatroom.dto.ChatroomResponse;
import com.chat_service.chatroom.entity.Chatroom;
import com.chat_service.chatroom.repository.ChatroomRepository;
import com.chat_service.consultant.userDetail.CustomUserDetails;
import com.chat_service.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultantService implements UserDetailsService {

    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return new CustomUserDetails(memberService.findByUserId(userId));
    }

}
