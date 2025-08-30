package com.chat_service.global.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class CustomDateFormat {
    public static LocalDateTime nowDateTime() {
        return  LocalDateTime.now(ZoneId.of("Asia/Seoul")).truncatedTo(ChronoUnit.SECONDS);
    }

}
