package com.jarikkomarik.digital.slave.bot.Model;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Voice;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramBot;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramFacade {
    @Autowired
    SpeechGenerator speechGenerator;

    Set<String> usersSet = new HashSet<>();

    @SneakyThrows
    public BotApiMethod<?> handleUpdate(Update update, DigitalSlaveBot digitalSlaveBot) {

        Message message = update.getMessage();
        String chatID = message.getChatId().toString();

        if(sayHiToNewUser(chatID, digitalSlaveBot)&& message.hasText()) {
            //digitalSlaveBot.sendPhoto(chatID, "src/main/resources/sonya.jpg");
            digitalSlaveBot.sendGeneratedVoice(message.getText(), chatID);
            digitalSlaveBot.sendUsageMessageCount(usersSet.size());
            return SendMessage.builder().chatId(String.valueOf(message.getChatId())).text("лови)").build();
        } else {
            return null;
        }
    }

    @SneakyThrows
    private boolean sayHiToNewUser(String chatID, DigitalSlaveBot digitalSlaveBot) {
        if(!usersSet.contains(chatID)) {
            digitalSlaveBot.sendMessage(chatID, "Привет\uD83E\uDD1A\n" +
                    "Я умею генерировать аудио из твоих сообщений \uD83E\uDD16 \n" +
                    "Пиши текст, а я пошлю тебе аудио \uD83C\uDFA4 ");
            usersSet.add(chatID);
            return false;
        } else {
            return true;
        }
    }
}
