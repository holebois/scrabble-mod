package com.holebois.scrabble.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;


public class ScrabbleDictionary {
    public static Logger LOGGER = org.slf4j.LoggerFactory.getLogger("ScrabbleMod");

    public static boolean isWord(String word) {
        LOGGER.info(word);
        try { //? maybe make async if desired, the longest search time is ~4.5ms
            long startTime = System.nanoTime();
            boolean found = findWordChar(word);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            LOGGER.info((found ? "Found \'" : "Couldn't Find \'") + word + "\' . Duration: " + duration + "ns");
            
            return found;
        } catch (Exception e) {
            LOGGER.error("Error checking if word is a word: " + e.getMessage());
            return false;
        }

    }

    // private static boolean findWordCharGz(String word) {
    //     char firstLetter = word.charAt(0);
    //     String fileName = "/assets/scrabble/words/comp/" + Character.toLowerCase(firstLetter) + ".gz";
    //     InputStream inputStream = ScrabbleDictionary.class.getResourceAsStream(fileName);
    //     return findGz(inputStream, word);
    // }

    private static boolean findWordChar(String word) {
        char firstLetter = word.charAt(0);
        String fileName = "/assets/scrabble/words/" + Character.toUpperCase(firstLetter) + ".txt";
        InputStream inputStream = ScrabbleDictionary.class.getResourceAsStream(fileName);
        return find(inputStream, word);
    }

    private static boolean find(InputStream stream, String word) {
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(word.toUpperCase())) {
                    found = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return found;
    }

    // private static boolean findGz(InputStream stream, String word) {
    //     boolean found = false;
    //     try (GZIPInputStream gzipInputStream = new GZIPInputStream(stream);
    //          BufferedReader reader = new BufferedReader(new InputStreamReader(gzipInputStream))) {
    //         String line;
    //         while ((line = reader.readLine()) != null) {
    //             if (line.equals(word.toUpperCase())) {
    //                 found = true;
    //                 break;
    //             }
    //         }
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return found;
    // }

}
