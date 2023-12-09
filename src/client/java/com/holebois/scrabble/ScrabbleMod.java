package com.holebois.scrabble;

import org.slf4j.Logger;

import com.holebois.scrabble.commands.ScrabbleCommands;

import net.fabricmc.api.ModInitializer;

public class ScrabbleMod implements ModInitializer {
    Logger LOGGER = org.slf4j.LoggerFactory.getLogger("ScrabbleMod");

    @Override
    public void onInitialize() {
        

        ScrabbleCommands.register();
        LOGGER.info("ScrabbleMod initialized!");

    }
}
