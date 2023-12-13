package com.holebois.scrabble.commands;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

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
                        MutableText message = Text.literal("\"" + 
                                            (ScrabbleDictionary.isWord(ScrabbleDictionary.getCleanWord(word)) ? "§a" : "§c") +
                                            ScrabbleDictionary.getCleanWord(word)
                                            + "§r\" is worth §a");
                         
                        HoverEvent he = new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.of(ScrabbleDictionary.getPointBreakdown(word)));
                        Text points = Text.literal(ScrabbleDictionary.getPoints(word) + " points.").setStyle(Style.EMPTY.withFormatting(Formatting.UNDERLINE).withHoverEvent(he));
                        
                        context.getSource().sendFeedback(message.append(points));
                        return 1;
                    })
                )
            )
            .then(literal("help")
                .then(literal("valid")
                    .executes(context -> {
                        context.getSource().sendFeedback(Text.of("§a/scrabble valid <word>§r - Checks if a word is valid."));
                        return 1;
                    })
                )
                .then(literal("points")
                    .executes(context -> {
                        String pointsMsg = """
                            §7Some Examples:
                            "scrabble" - simple
                            "scr3abble" - triple letter R
                            "3scrabble" - triple word
                            "2scrabble" - double word
                            "scr3abb2le" - double letter B, triple letter R
                            "33scr3abble" - 2x Triple Word, Triple letter R§r
                            
                            To count the points of a word, first enter the word multipliers (3, 33, 2, 22) followed by the word. To indicate a letter multiplier, enter the value of the multiplier after the letter.  (U3, K2, c2, etc.) Examples Above.
                            """;



                        context.getSource().sendFeedback(Text.of(pointsMsg));
                        return 1;
                    })
                )
                .executes(context -> {
                    context.getSource().sendFeedback(Text.of("§a/scrabble help <command>§r - Command Specific help.\n§a/scrabble valid <word>§r - Checks if a word is valid.\n§a/scrabble points <word>§r - Gets the points for a word."));
                    return 1;
                })
            )
        ));
    }
}
