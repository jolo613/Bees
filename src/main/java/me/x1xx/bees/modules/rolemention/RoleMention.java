package me.x1xx.bees.modules.rolemention;

import me.x1xx.bees.Main;
import me.x1xx.bees.modules.Module;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

public class RoleMention extends ListenerAdapter implements Module {
    // todo: start this
    private static MentionTask mentionTask;
    private Main main;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getInteraction().getName().equalsIgnoreCase("lfg")) return;

        if(!event.getInteraction().isFromGuild()) {
            event.getInteraction().reply("Please use this command within a guild!").setEphemeral(true).queue();
            return;
        }

        // todo: do this



    }

    @Override
    public void preInitialization(JDABuilder builder, Main main) {
        this.main = main;
        builder.addEventListeners(this);

    }

    @Override
    public void initialization(JDA jda) {
        jda.retrieveCommands().queue(commands -> {
            boolean found = false;
            for (Command command : commands) {
                if (command.getName().equals("lfg")) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                jda.upsertCommand(Commands.slash("lfg", "Mention a role when looking for a game!")
                        .addOption(OptionType.ROLE, "r", "The role to tag when looking for a game!", true)
                ).queue();
            }
        });
    }
}
