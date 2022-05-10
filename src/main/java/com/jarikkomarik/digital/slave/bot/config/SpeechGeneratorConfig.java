package com.jarikkomarik.digital.slave.bot.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpeechGeneratorConfig {

    @Value("${Speech.API.KEY}")
    String APIkey;
}
