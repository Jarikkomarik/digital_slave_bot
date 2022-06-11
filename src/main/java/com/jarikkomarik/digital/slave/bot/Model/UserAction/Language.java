package com.jarikkomarik.digital.slave.bot.Model.UserAction;
//google lang codes https://cloud.google.com/speech-to-text/docs/languages
public enum Language {
    EN("en-US"),
    UA("uk-UK"),
    RU("ru-RU"),
    JP("ja-JP");

    private String langCode;

    Language(String langCode) {
        this.langCode = langCode;
    }

    public String getLangCode() {
        return langCode;
    }
}
