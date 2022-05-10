package com.jarikkomarik.digital.slave.bot.Model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.starter.SpringWebhookBot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DigitalSlaveBot extends SpringWebhookBot {

    String botPath;
    String botUsername;
    String botToken;

    private TelegramFacade telegramFacade;
    @Autowired
    private SpeechGenerator speechGenerator;

    public DigitalSlaveBot(SetWebhook setWebhook, TelegramFacade telegramFacade) {
        super(setWebhook);
        this.telegramFacade = telegramFacade;
    }


    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
       return telegramFacade.handleUpdate(update, this);
    }

    public void sendPhoto(String chatId, String imagePath) throws TelegramApiException, FileNotFoundException {
        File image = ResourceUtils.getFile(imagePath);
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(image));
        sendPhoto.setChatId(chatId);
        execute(sendPhoto);
    }

    public void sendMessage(String chatId, String messageText) throws TelegramApiException, FileNotFoundException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messageText);
        execute(sendMessage);
    }

    @SneakyThrows
    public void sendGeneratedVoice(String text,String chatId) {
        InputStream inputStream = speechGenerator.getSpeech(text);
        File file = new File("TempVoice");
        file.createNewFile();
        FileUtils.copyInputStreamToFile(inputStream, file);
        SendVoice sendVoice = new SendVoice();
        sendVoice.setChatId(chatId);
        sendVoice.setVoice(new InputFile(file));
        execute(sendVoice);
    }

}
