package com.jarikkomarik.digital.slave.bot.config;

import com.jarikkomarik.digital.slave.bot.Model.DigitalSlaveBot;
import com.jarikkomarik.digital.slave.bot.Model.SpeechGenerator;
import com.jarikkomarik.digital.slave.bot.Model.TelegramFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;


@Configuration
public class AppConfig {
    private final TelegramBotConfig botConfig;

    public AppConfig(TelegramBotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(botConfig.getWebhookPath()).build();
    }

    @Bean
    public SpeechGenerator speechGenerator (SpeechGeneratorConfig speechGeneratorConfig) {
        return new SpeechGenerator(speechGeneratorConfig.getAPIkey());
    }

    @Bean
    public DigitalSlaveBot springWebhookBot (SetWebhook setWebhook, TelegramFacade telegramFacade) {
        DigitalSlaveBot bot = new DigitalSlaveBot(setWebhook, telegramFacade);
        bot.setBotToken(botConfig.getToken());
        bot.setBotUsername(botConfig.getUsername());
        bot.setBotPath(botConfig.getWebhookPath());
        return bot;
    }
}
