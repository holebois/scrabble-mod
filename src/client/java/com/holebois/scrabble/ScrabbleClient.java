package com.holebois.scrabble;

import org.slf4j.Logger;

import com.holebois.scrabble.core.ScrabbleCommands;

import net.fabricmc.api.ClientModInitializer;

public class ScrabbleClient implements ClientModInitializer {
	Logger LOGGER = org.slf4j.LoggerFactory.getLogger("Scrabble");
	@Override
	public void onInitializeClient() {
		ScrabbleCommands.register();
        LOGGER.info("Scrabble initialised!");
	}
}