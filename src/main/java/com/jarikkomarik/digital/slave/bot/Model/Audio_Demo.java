package com.jarikkomarik.digital.slave.bot.Model;

import java.io.IOException;
import java.io.InputStream;

import com.darkprograms.speech.synthesiser.SynthesiserV2;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 * This is where all begins .
 *
 * @author GOXR3PLUS
 *
 */
public class Audio_Demo {

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


    //Create a Synthesizer instance
    SynthesiserV2 synthesizer = new SynthesiserV2("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");

    public void speak(InputStream inputStream) {


        //Create a new Thread because JLayer is running on the current Thread and will make the application to lag

        try {

            //Create a JLayer instance
            AdvancedPlayer player = new AdvancedPlayer(inputStream);
            player.play();

            System.out.println("Successfully got back synthesizer data");

        } catch ( JavaLayerException e) {

            e.printStackTrace(); //Print the exception ( we want to know , not hide below our finger , like many developers do...)

        }

    }


}
