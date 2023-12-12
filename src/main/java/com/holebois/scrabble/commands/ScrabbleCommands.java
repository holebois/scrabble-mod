package com.holebois.scrabble.commands;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

import com.holebois.scrabble.core.ScrabbleDictionary;
import com.mojang.brigadier.arguments.StringArgumentType;

public class ScrabbleCommands {

    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(literal("scrabble")
            .then(literal("valid")
                .then(argument("word", StringArgumentType.string())
                    .executes(context -> {
                        String word = StringArgumentType.getString(context, "word");
                        Text message = Text.of("\"" + word + "\" " + (ScrabbleDictionary.isWord(word) ? "§ais§r " : "§cis not§r ") + "a valid word.");
                        context.getSource().sendFeedback(message);
                        return 1;
                    })
                )
            )
            .then(literal("points")
                .then(argument("word", StringArgumentType.string())
                    .executes(context -> {
                        String word = StringArgumentType.getString(context, "word");
                        Text message = Text.of("\"" + word + "\" is worth §a" + ScrabbleDictionary.getPoints(word) + "§r points.");
                        context.getSource().sendFeedback(message);
                        return 1;
                    })
                )
            )
        ));
    }
}
