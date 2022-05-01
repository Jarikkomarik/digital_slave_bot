package com.jarikkomarik.digital.slave.bot.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramBotConfig {

    @Value("${telegram.bot.webHookPath}")
    String WebhookPath;

    @Value("${telegram.bot.username}")
    String username;

    @Value("${telegram.bot.token}")
    String token;
}
