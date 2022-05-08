package com.jarikkomarik.digital.slave.bot.Model;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramFacade {

    public BotApiMethod<?> handleUpdate(Update update) {

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return null;
        } else {

            Message message = update.getMessage();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            if (message.hasText()) {
                sendMessage.setText(LocalDateTime.now().toString());

                Audio_Demo trying_different_languages = new Audio_Demo();
                trying_different_languages.synthesizer.setLanguage("ru-RU");
                InputStream audioData = null;
                try {
                    audioData = trying_different_languages.synthesizer.getMP3Data(message.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                trying_different_languages.speak(audioData);

                return sendMessage;
            }
        }
        return null;
    }
}
