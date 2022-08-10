package me.x1xx.bees.modules;

import me.x1xx.bees.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public interface Module {
    void preInitialization(JDABuilder builder, Main main);
    void initialization(JDA jda);
}
