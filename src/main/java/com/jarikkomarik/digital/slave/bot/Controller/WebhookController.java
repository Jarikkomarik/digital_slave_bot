package com.jarikkomarik.digital.slave.bot.Controller;

import com.jarikkomarik.digital.slave.bot.Model.DigitalSlaveBot;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;


@RestController
public class WebhookController {
    private final DigitalSlaveBot telegramBot;

    public WebhookController(DigitalSlaveBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return telegramBot.onWebhookUpdateReceived(update);
    }

    @GetMapping
    public ResponseEntity get() {
        return ResponseEntity.ok().build();
    }
}
