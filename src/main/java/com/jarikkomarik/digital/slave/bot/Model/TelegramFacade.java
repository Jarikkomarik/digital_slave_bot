package com.jarikkomarik.digital.slave.bot.Model;

import com.jarikkomarik.digital.slave.bot.Model.UserAction.Language;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramFacade {

    Logger logger = LoggerFactory.getLogger(TelegramFacade.class);
    Map<String, Language> usersMap = new HashMap<>();

    @SneakyThrows
    public BotApiMethod<?> handleUpdate(Update update, DigitalSlaveBot digitalSlaveBot) {
        if (update.getMessage() == null) return null;
        logger.info("New message received chatID: {}", update.getMessage().getChatId().toString());

        Message message = update.getMessage();
        String chatID = message.getChatId().toString();

        if (message.getText().equals("/start")) usersMap.remove(chatID);

        logger.info("Checking if usersMap contains chatID: {}", chatID);
        if (!usersMap.containsKey(chatID)) {
            logger.info("New chatID: {} is added to usersMap", chatID);
            usersMap.put(chatID, null);
            digitalSlaveBot.sendOptionsKeyboard(chatID, digitalSlaveBot);

        } else {

            logger.info("User:{} is already registered", chatID);

            Language tempLang = getLanguageFromMessage(message);

            if (tempLang != null) {
                logger.info("Setting laguage: {}, to chatID: {}.", tempLang, chatID);
                usersMap.put(chatID, tempLang);
                digitalSlaveBot.sendUpdateMessage(chatID, tempLang);
            } else if (usersMap.get(chatID) != null) {
                logger.info("Sending generated voice message in: {}, to chatID: {}",usersMap.get(chatID), chatID);
                digitalSlaveBot.sendGeneratedVoice(message.getText(), chatID, usersMap.get(chatID));
            } else {
                logger.info("Asking chatID: {} to select language", chatID);
                digitalSlaveBot.sendAskForLangMessage(chatID);
            }

        }
        return null;
    }

    private Language getLanguageFromMessage(Message message) {
        Language language;
        switch (message.getText()) {
            case "EN":
                language = Language.EN;
                break;
            case "UA":
                language = Language.UA;
                break;
            case "RU":
                language = Language.RU;
                break;
            case "JP":
                language = Language.JP;
                break;
            default:
                language = null;
                break;
        }
        return language;
    }
}
