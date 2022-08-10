package me.x1xx.bees.modules.overlay;

import me.x1xx.bees.Main;
import me.x1xx.bees.modules.Module;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Overlay extends ListenerAdapter implements Module {

    @Override
    public void preInitialization(JDABuilder builder, Main main) {
            builder.enableCache(CacheFlag.VOICE_STATE);
            builder.enableIntents(GatewayIntent.GUILD_VOICE_STATES);
            builder.addEventListeners(this);
    }

    @Override
    public void initialization(JDA jda) {
        jda.retrieveCommands().queue(commands -> {
            boolean found = false;
            for (Command command : commands) {
                if (command.getName().equals("overlay")) {
                    found = true;
                    break;
                }
            }
            if (!found) jda.upsertCommand("overlay", "Get an overlay for your current vc!").queue();
        });
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getInteraction().getName().equals("overlay")) return;

        Member member = event.getMember();

        if(member == null) {
            event.getInteraction().reply("You must use this command inside a discord server!").setEphemeral(true).queue();
            return;
        }



        if(member.getVoiceState() == null) {
            event.getInteraction().reply("You must be in a voice channel to use this command!").setEphemeral(true).queue();
            return;
        }

        if(member.getVoiceState().getChannel() == null) {
            event.getInteraction().reply("You must be in a voice channel to use this command!").setEphemeral(true).queue();
            return;
        }

        event.replyEmbeds(
                new EmbedBuilder()
                        .setAuthor("Overlay Link | " + member.getVoiceState().getChannel().getName(), null, member.getGuild().getIconUrl())
                        .setDescription("[Link]" + "(" + getOverlayLink(member) + ")")
                        .setColor(Color.CYAN)
                        .build()).setEphemeral(true).queue();


    }



    public String getOverlayLink(Member member) {
        return "https://streamkit.discord.com/overlay/voice/" +
                member.getGuild().getId() + "/" + member.getVoiceState().getChannel().getId() +
                "?icon=true&online=true&logo=white&text_color=%23ffffff&text_size=20&text_outline_color=%23000000&text_outline_size=0&text_shadow_color=%23000000&text_shadow_size=0&bg_color=%231e2124&bg_opacity=0.95&bg_shadow_color=%23000000&bg_shadow_size=0&invite_code=&limit_speaking=true&small_avatars=false&hide_names=false&fade_chat=0";
    }
}
