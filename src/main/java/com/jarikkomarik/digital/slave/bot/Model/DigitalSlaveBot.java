package com.jarikkomarik.digital.slave.bot.Model;

import com.jarikkomarik.digital.slave.bot.Model.UserAction.Language;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.starter.SpringWebhookBot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DigitalSlaveBot extends SpringWebhookBot {

    String botPath;
    String botUsername;
    String botToken;

    int count = 0;

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

    @SneakyThrows
    public void sendOptionsKeyboard(String chatID, DigitalSlaveBot digitalSlaveBot) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("UA");
        keyboardRow.add("RU");
        keyboardRow.add("EN");
        keyboardRow.add("JP");
        List<KeyboardRow> keyboardRows = new LinkedList<>();
        keyboardRows.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);


        execute(SendMessage.builder().chatId(chatID).text("Привет\uD83E\uDD1A\n" +
                "Я умею генерировать аудио из твоих сообщений \uD83E\uDD16 \n" +
                "Пиши текст, а я пошлю тебе аудио \uD83C\uDFA4\n" +
                "\n" + "Для начала выбери язык \uD83D\uDD24").replyMarkup(replyKeyboardMarkup).build());
    }

    public void sendMessage(String chatId, String messageText) throws TelegramApiException{
        execute(SendMessage.builder()
                .chatId(chatId)
                .text(messageText)
                .build());
    }

    public void sendAskForLangMessage(String chatId) throws TelegramApiException {
        sendMessage(chatId, "Язык не был задан, \nпожалуйста выберите язык из клавиатуры");
    }

    public void sendUpdateMessage(String chatId, Language language) throws TelegramApiException {
        String languagePostfix = null;
        switch (language) {
            case EN:
                languagePostfix = "Английский \uD83C\uDDEC\uD83C\uDDE7";
                break;
            case UA:
                languagePostfix = "Украинский \uD83C\uDDFA\uD83C\uDDE6";
                break;
            case RU:
                languagePostfix = "Русский \uD83C\uDDF7\uD83C\uDDFA";
                break;
            case JP:
                languagePostfix = "Японский \uD83C\uDDEF\uD83C\uDDF5";
                break;
        }
        sendMessage(chatId, "Выбран " + languagePostfix + " язык.");
    }

    @SneakyThrows
    public void sendGeneratedVoice(String text, String chatId, Language language) {

        setLanguage(language);

        InputStream inputStream = speechGenerator.getSpeech(text);
        File file = new File("TempVoice");
        file.createNewFile();
        FileUtils.copyInputStreamToFile(inputStream, file);
        SendVoice sendVoice = new SendVoice();
        sendVoice.setChatId(chatId);
        sendVoice.setVoice(new InputFile(file));
        execute(sendVoice);
    }

    public void setLanguage(Language language) {
        speechGenerator.setLanguage(language);
    }

}
