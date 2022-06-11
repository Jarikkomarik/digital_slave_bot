package com.jarikkomarik.digital.slave.bot.Model;


import java.io.IOException;
import java.io.InputStream;

import com.darkprograms.speech.synthesiser.SynthesiserV2;

import com.jarikkomarik.digital.slave.bot.Model.UserAction.Language;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import org.springframework.stereotype.Component;


public class SpeechGenerator {


    private SynthesiserV2 synthesizer;

    public SpeechGenerator(String API_KEY) {
        synthesizer = new SynthesiserV2(API_KEY);
    }

    public InputStream getSpeech (String str) throws IOException {
            return synthesizer.getMP3Data(str);
    }

    public void setLanguage(Language language) {
        synthesizer.setLanguage(language.getLangCode());
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
