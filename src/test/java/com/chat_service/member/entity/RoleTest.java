package com.chat_service.member.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RoleTest {

    @DisplayName("")
    @Test
    void test() throws Exception{
        //given
        Role roleConsultant = Role.ROLE_CONSULTANT;
        System.out.println("roleConsultant.toString() = " + roleConsultant.toString());
        System.out.println("roleConsultant.toString().getclass = " + roleConsultant.toString().getClass());
        System.out.println("roleConsultant.valueOf() = " + Role.valueOf(roleConsultant.toString()));
        System.out.println("roleConsultant.valueOf().getClass = " + Role.valueOf(roleConsultant.toString()).getClass());


        //when

        //then
    }


}