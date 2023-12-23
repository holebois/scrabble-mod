package com.holebois.scrabble.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;


public class ScrabbleDictionary {
    public static Logger LOGGER = org.slf4j.LoggerFactory.getLogger("ScrabbleMod");
    public static String[] words = new String[267751];

    public static void init() {
        LOGGER.info("Loading Scrabble Dictionary");
        try (InputStream stream = ScrabbleDictionary.class.getResourceAsStream("/assets/scrabble/words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                words[i] = line;
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("Loaded Scrabble Dictionary");
    }

    public static boolean findWordBinary(String word) {
        int min = 0;
        int max = words.length - 1;
        int mid = (min + max) / 2;
        while (min <= max) {
            if (words[mid].equals(word.toUpperCase())) {
                return true;
            } else if (words[mid].compareTo(word.toUpperCase()) < 0) {
                min = mid + 1;
            } else {
                max = mid - 1;
            }
            mid = (min + max) / 2;
        }
        return false;
    }

    public static boolean isWord(String word) {
        LOGGER.info(word);
        try {
            long startTime = System.nanoTime();
            boolean found = findWordBinary(word);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            LOGGER.info((found ? "Found \'" : "Couldn't Find \'") + word + "\' . Duration: " + duration + "ns");
            
            return found;
        } catch (Exception e) {
            LOGGER.error("Error checking if word is a word: " + e.getMessage());
            return false;
        }

    }

    public static String getCleanWord(String word) {
        return word.replaceAll("[0-9]", "").toLowerCase();
    }

    public static int getPoints(String word) {
        int points = 0;
        int modifier1 = 1;
        int modifier2 = 1;
        char[] chars = word.toCharArray();
        int lastPoints = 0;


        for (int i = 0; i < chars.length; i++) {
            Character c = chars[i];
            if (!Character.isDigit(c)) {
                int newPoints = getPoints(c);
                points += newPoints;
                lastPoints = newPoints;
                continue;   
            }
            if (i == 0) {
                modifier1 = Character.getNumericValue(c);
                continue;
            } else if (i == 1 && modifier1 != 1) {
                modifier2 = Character.getNumericValue(c);
                continue;
            } else {
                points = (points - lastPoints) + (lastPoints * Character.getNumericValue(c));
            }
            
        }
        return points * modifier1 * modifier2;
    }

    public static String getPointBreakdown(String word) {
        String breakdown = "(";
        int modifier1 = 1;
        int modifier2 = 1;
        char[] chars = word.toCharArray();
        boolean first = true;

        for (int i=0; i < chars.length; i++) {
            Character c = chars[i];
            if (!Character.isDigit(c)) {
                if (!first) {
                    breakdown += ", ";
                } else {
                    first = false;
                }
                int newPoints = getPoints(c);
                breakdown += Character.toUpperCase(c) + getSubscriptUnicode(newPoints);
            } else {
                if (i == 0) {
                    modifier1 = Character.getNumericValue(c);
                    continue;
                } else if (i == 1 && modifier1 != 1) {
                    modifier2 = Character.getNumericValue(c);
                    continue;
                } else {
                    breakdown += "x" + Character.getNumericValue(c);
                }
            }
        }

        int combinedModifier = modifier1 * modifier2;
        return breakdown + ")" + (combinedModifier != 1 ? " x" + combinedModifier : "");
    }

    private static String getSubscriptUnicode(int i) {
        switch (i) {
            case 0:
                return "\u2080";
            case 1:
                return "\u2081";
            case 2:
                return "\u2082";
            case 3:
                return "\u2083";
            case 4:
                return "\u2084";
            case 5:
                return "\u2085";
            case 8:
                return "\u2088";
            case 10:
                return "\u2081\u2080";
            default:
                return "";
        }
    }


    public static int getPoints(char c) {
        switch (Character.toUpperCase(c)) {
            case 'A':
            case 'E':
            case 'I':
            case 'L':
            case 'N':
            case 'O':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
                return 1;
            case 'D':
            case 'G':
                return 2;
            case 'B':
            case 'C':
            case 'M':
            case 'P':
                return 3;
            case 'F':
            case 'H':
            case 'V':
            case 'W':
            case 'Y':
                return 4;
            case 'K':
                return 5;
            case 'J':
            case 'X':
                return 8;
            case 'Q':
            case 'Z':
                return 10;
            default:
                return 0;
        }
    }

}
