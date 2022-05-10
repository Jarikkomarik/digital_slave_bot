package com.jarikkomarik.digital.slave.bot.Model;


import java.io.IOException;
import java.io.InputStream;

import com.darkprograms.speech.synthesiser.SynthesiserV2;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import org.springframework.stereotype.Component;


public class SpeechGenerator {


    public static final String LANG_AU_ENGLISH = "en-AU";
    public static final String LANG_US_ENGLISH = "en-US";
    public static final String LANG_UK_ENGLISH = "en-GB";
    public static final String LANG_ES_SPANISH = "es";
    public static final String LANG_FR_FRENCH = "fr";
    public static final String LANG_DE_GERMAN = "de";
    public static final String LANG_PT_PORTUGUESE = "pt-pt";
    public static final String LANG_PT_BRAZILIAN = "pt-br";
    public static final String LANG_RU_RUSSIAN = "ru-RU";
    public static final String LANG_UK_UKRAINIAN = "uk-UK";


    private SynthesiserV2 synthesizer;

    public SpeechGenerator(String API_KEY) {
        synthesizer = new SynthesiserV2(API_KEY);
        synthesizer.setLanguage(LANG_RU_RUSSIAN);
        synthesizer.setSpeed(0.8);
    }

    public InputStream getSpeech (String str) throws IOException {
            return synthesizer.getMP3Data(str);
    }

    public void speak(InputStream inputStream) {
        try {
            AdvancedPlayer player = new AdvancedPlayer(inputStream);
            player.play();
        } catch ( JavaLayerException e) {
            e.printStackTrace();
        }
    }


}
