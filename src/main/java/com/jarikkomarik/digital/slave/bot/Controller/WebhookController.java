package com.jarikkomarik.digital.slave.bot.Controller;

import com.jarikkomarik.digital.slave.bot.Model.DigitalSlaveBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDice;
import org.telegram.telegrambots.meta.api.methods.send.SendInvoice;
import org.telegram.telegrambots.meta.api.objects.Update;


@RestController
public class WebhookController {
    @Autowired
    private DigitalSlaveBot telegramBot;


    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {

        return telegramBot.onWebhookUpdateReceived(update);
    }

    @GetMapping
    public ResponseEntity get() {
        return ResponseEntity.ok().build();
    }
}
